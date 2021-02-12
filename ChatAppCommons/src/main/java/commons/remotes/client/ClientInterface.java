package commons.remotes.client;

import commons.sharedmodels.InvitationDto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {

    public void sendNewInvToUser(InvitationDto invitationDto) throws RemoteException;
    public void deleteInv(InvitationDto invitationDto) throws  RemoteException;


}
