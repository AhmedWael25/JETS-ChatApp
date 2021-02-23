package jets.chatserver.network.rmi;

import commons.remotes.client.ClientInterface;
import commons.remotes.server.UpdateStatusServiceInt;
import jets.chatserver.database.daoImpl.FriendsDaoImpl;
import jets.chatserver.database.daoImpl.UserDaoImpl;
import jets.chatserver.gui.helpers.ModelsFactory;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class UpdateStatusService   extends UnicastRemoteObject implements UpdateStatusServiceInt {

    Map<String, ClientInterface> currentConnectedUsers = null;

    public UpdateStatusService() throws RemoteException {
        super();
        currentConnectedUsers = ModelsFactory.getInstance().getCurrentConnectedUsers();
    }

    public UpdateStatusService(Map<String, ClientInterface> currentConnectedUsers) throws RemoteException {
        super();
        this.currentConnectedUsers = currentConnectedUsers;
    }

    @Override
    public boolean updateStatus(String userId, Integer status) throws RemoteException {

        currentConnectedUsers.forEach((id, ci) -> {
            if(!id.equals(userId)){
                try {
                    ci.updateUserStatus(userId,status);
                    if(status.equals(1)){
                        //Notify His Friends Telling Them He Went ONLINE
                        //First get His Friends List
                    }
                } catch (RemoteException  e) {
                    e.printStackTrace();
                }
            }
        });


        if(status == 1){
            try {
                List<String> userFriends = FriendsDaoImpl.getFriendsDaoInstance().getAllFriendsIds(userId);
                for(String friendId : userFriends){
                    ClientInterface ci = currentConnectedUsers.get(friendId);
                    if(ci != null ){
                        ci.notifyUserIsOnline(userId);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return  true;
    }
}
