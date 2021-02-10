package jets.chatserver.network.rmi;


import commons.remotes.server.AddFriendInt;
import jets.chatserver.database.dao.FriendsDao;
import jets.chatserver.database.dao.UserDao;
import jets.chatserver.database.daoImpl.FriendsDaoImpl;
import jets.chatserver.database.daoImpl.UserDaoImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

public class AddFriendImpl extends UnicastRemoteObject implements AddFriendInt {


    public AddFriendImpl() throws RemoteException {
        super();
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