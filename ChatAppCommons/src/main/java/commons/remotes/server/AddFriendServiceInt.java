package commons.remotes.server;

import commons.sharedmodels.FriendGpDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AddFriendServiceInt extends Remote {

    boolean addFriend(String userId,String friendId) throws RemoteException;
    List<FriendGpDto> fetchAllFriendsByUserId(String userId)throws RemoteException;
    boolean areFriends(String userId, String  friendId)throws  RemoteException;
}
