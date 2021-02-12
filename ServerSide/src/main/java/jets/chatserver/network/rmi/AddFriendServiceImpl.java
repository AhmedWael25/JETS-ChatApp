package jets.chatserver.network.rmi;


import commons.remotes.client.ClientInterface;
import commons.remotes.server.AddFriendServiceInt;
import jets.chatserver.database.dao.FriendsDao;
import jets.chatserver.database.dao.InvitationsDao;
import jets.chatserver.database.dao.UserDao;
import jets.chatserver.database.daoImpl.FriendsDaoImpl;
import jets.chatserver.database.daoImpl.UserDaoImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.Map;

public class AddFriendServiceImpl extends UnicastRemoteObject implements AddFriendServiceInt {

    Map<String, ClientInterface> currentConnectedUsers = null;
    FriendsDao friendsDao = null;

    public AddFriendServiceImpl() throws RemoteException {
        super();
    }

    public AddFriendServiceImpl(Map<String, ClientInterface> currentConnectedUsers) throws RemoteException {
        super();
        this.currentConnectedUsers = currentConnectedUsers;
        try {
            friendsDao = FriendsDaoImpl.getFriendsDaoInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean addFriend(String  userId ,String friendId) throws  RemoteException{
        boolean areFriends = false;
        try {
            areFriends =  friendsDao.areFriends(userId,friendId);
            if (areFriends) return  false;

            friendsDao.addFriend(userId,friendId);

            //TODO Call Back Client And Add Chat To List Of Current Running Chats.

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  true;
    }

}
