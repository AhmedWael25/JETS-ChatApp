package commons.remotes.client;

import commons.sharedmodels.GpChatDto;
import commons.sharedmodels.InvitationDto;
import commons.sharedmodels.MessageDto;
import commons.sharedmodels.P2PChatDto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {

     void sendNewInvToUser(InvitationDto invitationDto) throws RemoteException;
     void deleteInv(InvitationDto invitationDto) throws  RemoteException;

     void sendNewChatToUser(P2PChatDto chatDto) throws  RemoteException;

    void sendNewGpChatToUsers(GpChatDto gpChatDto) throws RemoteException;

    void sendNewMessageToUser(MessageDto msgDto) throws RemoteException;


    void forTesting(String userId) throws  RemoteException;

}
