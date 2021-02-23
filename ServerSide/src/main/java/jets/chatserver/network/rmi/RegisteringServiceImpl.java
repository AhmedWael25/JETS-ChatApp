package jets.chatserver.network.rmi;

import commons.remotes.client.ClientInterface;
import commons.remotes.server.RegisteringClientInt;
import jets.chatserver.DBModels.DBUserCredintials;
import jets.chatserver.database.dao.UserDao;
import jets.chatserver.database.daoImpl.UserDaoImpl;
import jets.chatserver.gui.helpers.ModelsFactory;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.Map;

public class RegisteringServiceImpl extends UnicastRemoteObject implements RegisteringClientInt {

    Map<String, ClientInterface> currentConnectedUsers = null;

    public RegisteringServiceImpl() throws RemoteException {
        super();
        currentConnectedUsers = ModelsFactory.getInstance().getCurrentConnectedUsers();
    }


    public RegisteringServiceImpl(Map<String, ClientInterface> currentConnectedUsers) throws RemoteException {
        super();
        this.currentConnectedUsers = currentConnectedUsers;
    }

    @Override
    public void registerClient(ClientInterface clientInterface, String userId) throws RemoteException {
        if(!currentConnectedUsers.containsKey(userId)){
            currentConnectedUsers.put(userId,clientInterface);
            System.out.println("Client of id ("+userId+") Registered");
            //Update --- User status and Avail

            UserDao userDao = null;
            try {
                userDao = UserDaoImpl.getUserDaoInstance();
                userDao.updateDBUserAvailability(1,userId);
                userDao.updateDBUserStatus(1,userId);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public void disconnectClient(ClientInterface clientInterface, String userId) throws RemoteException {
        if(currentConnectedUsers.containsKey(userId)){
            currentConnectedUsers.remove(userId);
            System.out.println("Client of id ("+userId+") Disconnected");
            //Update ---User Status And Validation
        }
    }



    @Override
    public boolean isClientRegistered(String userId) throws RemoteException {
        if(currentConnectedUsers.get(userId) == null){
            return  false;
        }
        return  true;
    }

    @Override
    public void disconnectClient(String userId) throws RemoteException {
        if(currentConnectedUsers.containsKey(userId)){
            currentConnectedUsers.remove(userId);
            System.out.println("Client of id ("+userId+") Disconnected");

            try {
                UserDao userDao = UserDaoImpl.getUserDaoInstance();
                userDao.updateDBUserAvailability(0,userId);
                userDao.updateDBUserStatus(0,userId);
                System.out.println("user status updated");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public String getEncryptedPassword(String userPhone) throws RemoteException {
        DBUserCredintials userCredintials = new DBUserCredintials();
        try {
            UserDao userDao = UserDaoImpl.getUserDaoInstance();
            userCredintials = userDao.getUserCredentials(userPhone);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return userCredintials.getUserPassword();
    }
}



