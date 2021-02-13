package jets.chatserver.network.rmi;


import commons.remotes.client.ClientInterface;
import commons.remotes.server.AddFriendServiceInt;
import commons.sharedmodels.P2PChatDto;
import jets.chatserver.database.dao.FriendsDao;
import jets.chatserver.database.dao.InvitationsDao;
import jets.chatserver.database.dao.P2PChatDao;
import jets.chatserver.database.dao.UserDao;
import jets.chatserver.database.daoImpl.FriendsDaoImpl;
import jets.chatserver.database.daoImpl.P2PChatDaoImpl;
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
    public boolean addFriend(String  participant1 ,String participant2) throws  RemoteException{
        boolean areFriends = false;
        try {
            areFriends =  friendsDao.areFriends(participant1,participant2);
            if (areFriends) return  false;

            //Friend Added In DB
            friendsDao.addFriend(participant1,participant2);
            //Now Create Chat Between the 2 Users
            P2PChatDao p2pChatDao = P2PChatDaoImpl.getP2PChatDaoInstance();
            p2pChatDao.addChat(participant1,participant2);

            //TODO Call Back Client And Add Chat To List Of Current Running Chats.
            //TODO The Chat Will be Added to Both Users OFC
            ClientInterface client1 = currentConnectedUsers.get(participant1);
            ClientInterface client2 = currentConnectedUsers.get(participant2);

            client1.forTesting(participant1);
            client2.forTesting(participant2);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  true;
    }

}
