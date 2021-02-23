package jets.chatserver.network.rmi;


import commons.remotes.client.ClientInterface;
import commons.remotes.server.UserProfileServiceInt;
import commons.sharedmodels.CurrentUserDto;
import commons.utils.HashEncoder;
import jets.chatserver.DBModels.DBUser;
import jets.chatserver.DBModels.DBUserCredintials;
import jets.chatserver.database.dao.UserDao;
import jets.chatserver.database.daoImpl.UserDaoImpl;
import jets.chatserver.network.adapters.EntityObjAdapter;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;


public class UserProfileServiceImpl extends UnicastRemoteObject implements UserProfileServiceInt {

    Map<String, ClientInterface> currentConnectedUsers = null;

    public UserProfileServiceImpl() throws RemoteException {

    }

    public UserProfileServiceImpl(Map<String, ClientInterface> currentConnectedUsers) throws RemoteException {
        super();
        this.currentConnectedUsers = currentConnectedUsers;
    }

    @Override
    public CurrentUserDto getUserData(String userPhone) throws RemoteException {
        CurrentUserDto userDto = new CurrentUserDto();
        try {
            UserDao userDao = UserDaoImpl.getUserDaoInstance();
            DBUser dbUser = userDao.getUserById(userPhone);
            userDto = EntityObjAdapter.convertEntityToDto(dbUser);
        } catch (SQLException throwables) {
            System.out.println("no Data Fetched from DB");
            throwables.printStackTrace();

        }
        return userDto;
    }

    @Override
    public boolean updateUserData(CurrentUserDto currentUserDto, String userId) throws RemoteException {
        DBUser receivedUpdatedUser = EntityObjAdapter.convertDtoToEntity(currentUserDto);
        //TODO: use this DBUser to set the data in DB.
        System.out.println("New send DBUser to DAO to update the database");
        try {
            return UserDaoImpl.getUserDaoInstance().updateUserTable(receivedUpdatedUser, userId);
        } catch (SQLException e) {
            System.out.println("Unable to update user in database.");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateProfilePic(String newImageString, String userId) throws RemoteException {

        try {
            boolean isUpdated =  UserDaoImpl.getUserDaoInstance().updateDBUserPhoto(newImageString, userId);


            currentConnectedUsers.forEach((id, clientInterface) -> {
                if(!id.equals(userId)){
                    try {
                        clientInterface.updateUserImg(userId,newImageString);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
            return isUpdated;
        } catch (SQLException e) {
            System.out.println("Unable to User photo.");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateUserStatus(int userStatus, String userId) throws RemoteException{

        {
            try {
                boolean isUpdated = UserDaoImpl.getUserDaoInstance().updateDBUserStatus(userStatus, userId);
                //Call Back All Connected Clients to Update Their Status
                //Loop on All Online Users And Change
                currentConnectedUsers.forEach((id, clientInterface) -> {
                    if(!id.equals(userId)){
                        try {
                            clientInterface.updateUserStatus(userId,userStatus);
//                            clientInterface.pushStatusNotification(50,"lololo","Avail","isas");
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                });
                return  isUpdated;
            } catch (SQLException e) {
                System.out.println("Unable to update User status.");
                e.printStackTrace();
            }
            return false;
        }

    }

    @Override
    public boolean updateUserPassword(String oldPassword, String newPassword, String userId) throws RemoteException {

        DBUserCredintials userCredintials;
        try {
            UserDao userDao = UserDaoImpl.getUserDaoInstance();
            userCredintials = userDao.getUserCredentials(userId);
            if( HashEncoder.verifyPassword(oldPassword,userCredintials.getUserPassword()))
            {
               return userDao.updateDBUserPassword(newPassword,userId);
            }

        } catch (SQLException e) {
            System.out.println("Unable to Update Password.");
            e.printStackTrace();
        }
        return false;
    }

}
