package jets.chatserver.sharedModels;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AddFriendInt extends Remote {

    boolean addFriend(String userId,String friendId) throws RemoteException;

}
