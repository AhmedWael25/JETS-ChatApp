package jets.chatserver.network.rmi;

import commons.remotes.server.SignInServiceInt;
import commons.remotes.server.SignUpServiceInt;
import commons.sharedmodels.CurrentUserDto;
import jets.chatserver.DBModels.DBUser;
import jets.chatserver.DBModels.DBUserCredintials;
import jets.chatserver.database.dao.UserDao;
import jets.chatserver.database.daoImpl.UserDaoImpl;
import jets.chatserver.network.adapters.EntityDTOAdapter;
import jets.chatserver.network.adapters.EntityObjAdapter;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

public class SignUpServiceImpl extends UnicastRemoteObject implements SignUpServiceInt {


    enum registrationStatus {
        Registered(1), NotRegistered(2), NotFullyRegistered(3),RegisterError(4) ;

        private final int value;

        registrationStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "" + value;
        }
    }


    public SignUpServiceImpl() throws RemoteException {

    }

    @Override
    public int checkUserExist(String userPhone, String userName) throws RemoteException {
        try {
            UserDao userDao = UserDaoImpl.getUserDaoInstance();
            if (userDao.isUserExist(userPhone)) {
                DBUserCredintials userCredintials = userDao.getUserCredentials(userPhone);
                if (userCredintials.getUserPassword().equals("\"\""))
                    return SignUpServiceImpl.registrationStatus.NotFullyRegistered.getValue();
                else
                    return SignUpServiceImpl.registrationStatus.Registered.getValue();

            }

            else if(userDao.isUserNameExist(userName)){
                return SignUpServiceImpl.registrationStatus.RegisterError.getValue();

             } } catch (SQLException throwables) {
                throwables.printStackTrace();
              }
        return SignUpServiceImpl.registrationStatus.NotRegistered.getValue();

    }

    @Override
    public boolean signUpUser(CurrentUserDto currentUserDto) throws RemoteException {

        DBUser dbUser = EntityObjAdapter.convertDtoToEntity(currentUserDto);
        try {
            UserDao userDao = UserDaoImpl.getUserDaoInstance();
            if(!userDao.isUserExist(dbUser.getPhone()) && !userDao.isUserNameExist(dbUser.getDisplayedName())) {
                userDao.addUser(dbUser);
                System.out.println("user registered successfully");
            }
            else {
                System.out.println("user should be updated");
                userDao.updateUser(dbUser);
            }
        } catch (SQLException throwables) {
            System.out.println("failed to add user");
            return  false;
        }
       return true;
} }
