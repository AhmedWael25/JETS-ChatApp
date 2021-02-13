package jets.chatclient.network.rmi;

import commons.remotes.client.ClientInterface;
import commons.sharedmodels.InvitationDto;
import commons.sharedmodels.P2PChatDto;
import jets.chatclient.gui.controllers.ContactsController;
import jets.chatclient.gui.helpers.ControllersGetter;

import javax.naming.ldap.Control;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientInterfaceImpl extends UnicastRemoteObject implements ClientInterface {

    public ClientInterfaceImpl() throws RemoteException {
        super();
    }

    @Override
    public void sendNewInvToUser(InvitationDto invitationDto)throws  RemoteException {

        ControllersGetter controllersGetter = ControllersGetter.getInstance();
        ContactsController contactsController = controllersGetter.getContactsController();
        contactsController.addInvitationToList(invitationDto);
        System.out.println("Awesome CallBack");
    }

    @Override
    public void deleteInv(InvitationDto invitationDto) throws RemoteException {}

    @Override
    public void sendNewChatToUser(P2PChatDto chatDto) throws RemoteException {

    }

    @Override
    public void forTesting(String userId) throws  RemoteException{
        System.out.println("CallBack to USer of Id : "+ userId);
    }


}
