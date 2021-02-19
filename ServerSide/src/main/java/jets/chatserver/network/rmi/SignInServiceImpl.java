package jets.chatserver.network.rmi;

import commons.remotes.server.SignInServiceInt;
import commons.sharedmodels.CurrentUserDto;
import commons.utils.HashEncoder;
import jets.chatserver.DBModels.DBUser;
import jets.chatserver.DBModels.DBUserCredintials;
import jets.chatserver.database.dao.UserDao;
import jets.chatserver.database.daoImpl.UserDaoImpl;
import jets.chatserver.network.adapters.EntityDTOAdapter;
import jets.chatserver.network.adapters.EntityObjAdapter;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.Optional;

public class SignInServiceImpl extends UnicastRemoteObject implements SignInServiceInt {

    enum registrationStatus {
        Registered(1), NotRegistered(2), NotFullyRegistered(3);

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


    public SignInServiceImpl() throws RemoteException {
        super();
        System.out.println("SignIn service Intialized");
    }


    //checks whether User name exists within the DB or not
    @Override
    public Integer checkUserCredentials(String userPhone) throws RemoteException {
        try {
            UserDao userDao = UserDaoImpl.getUserDaoInstance();
            if (userDao.isUserExist(userPhone)) {
                System.out.println(userDao.isUserExist(userPhone));
                DBUserCredintials userCredintials = userDao.getUserCredentials(userPhone);
                if (userCredintials.getUserPassword().equals("\"\""))
                    return registrationStatus.NotFullyRegistered.getValue();
                else
                    return registrationStatus.Registered.getValue();

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return registrationStatus.NotRegistered.getValue();
    }

    @Override
    public boolean checkUserCredentials(String userPhone, String userPassword) throws RemoteException {
        DBUserCredintials userCredintials = new DBUserCredintials();
        try {
            UserDao userDao = UserDaoImpl.getUserDaoInstance();
             userCredintials = userDao.getUserCredentials(userPhone);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //handling password
        //TODO fetch salt from DB , and add it to userCredintials class
        String salt="SKgTpccoOReOUvXS/ORKuY1+mC0=";
        String password="";
        Optional<String> optionalpassword = HashEncoder.hashPassword(userPassword,salt);
        if(optionalpassword.isPresent());
        password=optionalpassword.get();

        if(HashEncoder.verifyPassword(userCredintials.getUserPassword(),password))
            return true;
        else return false;
    }

    @Override
    public CurrentUserDto signUserIn(String userPhone) throws RemoteException {
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
}
