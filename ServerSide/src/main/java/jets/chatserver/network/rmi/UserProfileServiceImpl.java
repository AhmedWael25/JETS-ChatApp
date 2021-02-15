package jets.chatserver.network.rmi;


import commons.remotes.server.UserProfileServiceInt;
import commons.sharedmodels.CurrentUserDto;
import jets.chatserver.DBModels.DBUser;
import jets.chatserver.database.dao.UserDao;
import jets.chatserver.database.daoImpl.UserDaoImpl;
import jets.chatserver.network.adapters.EntityObjAdapter;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

//import static jets.chatserver.network.adapters.EntityObjAdapter.convertDtoToEntity;

public class UserProfileServiceImpl extends UnicastRemoteObject implements UserProfileServiceInt {
    public UserProfileServiceImpl() throws RemoteException {

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
    public boolean updateUserData(CurrentUserDto currentUserDto) throws RemoteException {
        DBUser receivedUpdatedUser = EntityObjAdapter.convertDtoToEntity(currentUserDto);
        //TODO: use this DBUser to set the data in DB.
        return false;
    }
}
