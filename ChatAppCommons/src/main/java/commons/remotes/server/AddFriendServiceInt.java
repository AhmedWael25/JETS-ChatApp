package commons.remotes.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AddFriendServiceInt extends Remote {

    boolean addFriend(String userId,String friendId) throws RemoteException;

}
