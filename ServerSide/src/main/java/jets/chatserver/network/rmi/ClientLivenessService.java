package jets.chatserver.network.rmi;

import commons.remotes.client.ClientInterface;
import commons.remotes.server.ClientLivenessServiceInt;
import javafx.concurrent.ScheduledService;
import jets.chatserver.database.daoImpl.FriendsDaoImpl;
import jets.chatserver.database.daoImpl.UserDaoImpl;
import jets.chatserver.gui.helpers.ModelsFactory;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ClientLivenessService extends UnicastRemoteObject implements ClientLivenessServiceInt {

    Map<String, ClientInterface> currentConnectedUsers;

    public ClientLivenessService() throws RemoteException {
        ModelsFactory modelsFactory = ModelsFactory.getInstance();
        currentConnectedUsers = modelsFactory.getCurrentConnectedUsers();

        initLivenessCheck();
    }


    public  void initLivenessCheck(){

        ScheduledExecutorService scheduledService = new ScheduledThreadPoolExecutor(1);
        scheduledService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                Iterator<Map.Entry<String, ClientInterface>> iter = currentConnectedUsers.entrySet().iterator();
                while (iter.hasNext()){
                    Map.Entry<String,ClientInterface> entry = iter.next();
                    try {
                        String ack = entry.getValue().LivenessTest();
                    } catch (RemoteException e) {
                        System.out.println("Client Disconnected of Id"+entry.getKey());
                        iter.remove();
                        //Thread That will handle Status Update
                        //Will Also Handle DB Stuff
                        String disconnectedUserId = entry.getKey();
                        new Thread(() ->{
                            List<String> userFriends = null;
                            try {
                                UserDaoImpl.getUserDaoInstance().updateDBUserAvailability(0,disconnectedUserId);
                                userFriends = FriendsDaoImpl.getFriendsDaoInstance().getAllFriendsIds(disconnectedUserId);
                                for(String friendId : userFriends){
                                    ClientInterface ci = currentConnectedUsers.get(friendId);
                                    if(ci != null ){
                                        ci.updateUserStatus(disconnectedUserId,0);
                                    }
                                }
                            } catch (SQLException | RemoteException throwables) {
                                throwables.printStackTrace();
                            }
                        }).start();
                    }
                }
            }
        },0,500L, TimeUnit.MILLISECONDS);
    }


    @Override
    public String checkServerLiveness() throws RemoteException {
        return  "SERVER_ACK";
    }
}
