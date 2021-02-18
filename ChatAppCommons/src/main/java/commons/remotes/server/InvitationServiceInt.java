package commons.remotes.server;

import commons.sharedmodels.InvitationDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface InvitationServiceInt extends Remote {

    List<InvitationDto> getAllUserInvitations(String userId)  throws RemoteException;
    boolean  deleteInvitation(InvitationDto invitationDto)  throws RemoteException;
    boolean sendInvitation(InvitationDto invitationDto)  throws RemoteException;
    boolean isInviteExists(InvitationDto invitationDto) throws  RemoteException;
    boolean isUserExist(String  userId) throws  RemoteException;

}
