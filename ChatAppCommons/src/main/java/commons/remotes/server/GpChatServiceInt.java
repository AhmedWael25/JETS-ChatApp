package commons.remotes.server;

import commons.sharedmodels.GpChatDto;
import commons.sharedmodels.GpChatUserDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface GpChatServiceInt extends Remote {

    List<GpChatDto> fetchAllUserGpChats(String userId) throws RemoteException;
    boolean createGroupChat(GpChatUserDto chatDto) throws  RemoteException;
}
