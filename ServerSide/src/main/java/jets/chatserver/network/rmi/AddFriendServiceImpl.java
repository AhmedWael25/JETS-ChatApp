package jets.chatserver.network.rmi;


import commons.remotes.client.ClientInterface;
import commons.remotes.server.AddFriendServiceInt;
import jets.chatserver.database.dao.FriendsDao;
import jets.chatserver.database.dao.UserDao;
import jets.chatserver.database.daoImpl.FriendsDaoImpl;
import jets.chatserver.database.daoImpl.UserDaoImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.Map;

public class AddFriendServiceImpl extends UnicastRemoteObject implements AddFriendServiceInt {

    Map<String, ClientInterface> currentConnectedUsers = null;

    public AddFriendServiceImpl() throws RemoteException {
        super();
    }

    public AddFriendServiceImpl(Map<String, ClientInterface> currentConnectedUsers) throws RemoteException {
        super();
        this.currentConnectedUsers = currentConnectedUsers;
    }

    @Override
    public boolean addFriend(String  userId ,String friendId) throws  RemoteException{

        try{
            UserDao userDao = UserDaoImpl.getUserDaoInstance();
            boolean isUserExists   = userDao.isUserExist(friendId);

            if(!isUserExists){
                return  false;
            }
            FriendsDao friendsDao = FriendsDaoImpl.getFriendsDaoInstance();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }


}
