package commons.remotes.server;

import commons.sharedmodels.FriendDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AddFriendServiceInt extends Remote {

    boolean addFriend(String userId,String friendId) throws RemoteException;
    List<FriendDto> fetchAllFriendsByUserId(String userId)throws RemoteException;

}
