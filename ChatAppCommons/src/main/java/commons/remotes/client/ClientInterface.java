package commons.remotes.client;

import commons.sharedmodels.InvitationDto;
import commons.sharedmodels.P2PChatDto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {

     void sendNewInvToUser(InvitationDto invitationDto) throws RemoteException;
     void deleteInv(InvitationDto invitationDto) throws  RemoteException;

     void sendNewChatToUser(P2PChatDto chatDto) throws  RemoteException;



    void forTesting(String userId) throws  RemoteException;

}
