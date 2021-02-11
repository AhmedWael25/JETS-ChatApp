package commons.remotes.server;

import commons.sharedmodels.Invitation;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface InvitationServiceInt extends Remote {

    List<Invitation> getAllUserInvitations(String userId)  throws RemoteException;
    boolean  deleteInvitation(Invitation invitation)  throws RemoteException;
    boolean sendInvitation(Invitation invitation,String receiverId)  throws RemoteException;

}
