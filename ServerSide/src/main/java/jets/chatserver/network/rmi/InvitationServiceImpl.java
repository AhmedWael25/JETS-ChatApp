package jets.chatserver.network.rmi;

import commons.remotes.client.ClientInterface;
import commons.remotes.server.InvitationServiceInt;
import commons.sharedmodels.Invitation;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

public class InvitationServiceImpl extends UnicastRemoteObject implements InvitationServiceInt {

    Map<String, ClientInterface> currentConnectedUsers = null;

    protected InvitationServiceImpl() throws RemoteException {
        super();
    }
    public InvitationServiceImpl(Map<String, ClientInterface> currentConnectedUsers) throws RemoteException {
        super();
        this.currentConnectedUsers = currentConnectedUsers;
    }

    @Override
    public List<Invitation> getAllUserInvitations(String userId) throws RemoteException {
        return null;
    }

    @Override
    public boolean deleteInvitation(Invitation invitation) throws RemoteException {
        return false;
    }

    @Override
    public boolean sendInvitation(Invitation invitation, String receiverId) throws RemoteException {
        return false;
    }
}
