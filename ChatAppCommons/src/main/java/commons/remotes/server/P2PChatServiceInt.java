package commons.remotes.server;

import commons.sharedmodels.P2PMessageDto;
import commons.sharedmodels.P2PChatDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface P2PChatServiceInt extends Remote {

    List<P2PChatDto> fetchAllUserP2PChats(String userId) throws RemoteException;

    boolean sendMessage(P2PMessageDto msgDto) throws RemoteException;
}
