package commons.remotes.server;

import commons.sharedmodels.CurrentUserDto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserProfileServiceInt extends Remote {

    // takes user phone or user instance ??
    CurrentUserDto getUserData(String userPhone) throws RemoteException;
    boolean updateUserData(CurrentUserDto currentUserDto, String userId) throws  RemoteException;
}
