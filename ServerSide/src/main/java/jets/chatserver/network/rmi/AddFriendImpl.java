package jets.chatserver.network.rmi;

import jets.chatserver.sharedModels.AddFriendInt;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AddFriendImpl extends UnicastRemoteObject implements AddFriendInt {


    public AddFriendImpl() throws RemoteException {
        super();
    }

    @Override
    public boolean addFriend(String friendId) {
        System.out.println("Friend Added Succefully");
        return true;
    }

    @Override
    public boolean isFriendExist(String friendId) {
        System.out.println("Yes he does");
        return true;
    }
}
