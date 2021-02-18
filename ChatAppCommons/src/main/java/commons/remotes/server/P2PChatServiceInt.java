package commons.remotes.server;

import commons.sharedmodels.MessageDto;
import commons.sharedmodels.P2PChatDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface P2PChatServiceInt extends Remote {

    List<P2PChatDto> fetchAllUserP2PChats(String userId) throws RemoteException;

    void sendMessage(MessageDto msgDto)throws RemoteException;
}
