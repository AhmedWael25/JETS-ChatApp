package commons.remotes.server;

import commons.sharedmodels.CurrentUserDto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SignInServiceInt extends Remote {

    //this will first check user credentials, then fill the current user model
    //with data coming from DB through DTO

    Integer checkUserCredentials(String userPhone) throws RemoteException;
    boolean checkUserCredentials(String userPhone,String userPassword) throws RemoteException;
    //takes user Id (phone number),
    CurrentUserDto signUserIn(String userPhone) throws RemoteException;

}
