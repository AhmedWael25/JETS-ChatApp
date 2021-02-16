package commons.remotes.server;

import commons.sharedmodels.CurrentUserDto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SignUpServiceInt extends Remote {


    int checkUserExist(String userId,String userName) throws RemoteException;
    boolean signUpUser(CurrentUserDto currentUserDto) throws RemoteException;

}
