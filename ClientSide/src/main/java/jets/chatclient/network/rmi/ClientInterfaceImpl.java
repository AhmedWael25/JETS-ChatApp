package jets.chatclient.network.rmi;

import commons.remotes.client.ClientInterface;
import commons.sharedmodels.GpChatDto;
import commons.sharedmodels.InvitationDto;
import commons.sharedmodels.MessageDto;
import commons.sharedmodels.P2PChatDto;
import jets.chatclient.gui.controllers.ContactsController;
import jets.chatclient.gui.controllers.P2PChatController;
import jets.chatclient.gui.controllers.groupChatController;
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
    }

    @Override
    public void deleteInv(InvitationDto invitationDto) throws RemoteException {}

    @Override
    public void sendNewChatToUser(P2PChatDto chatDto) throws RemoteException {

        ControllersGetter controllersGetter = ControllersGetter.getInstance();
        P2PChatController p2pChatController = controllersGetter.getP2PChatController();
        p2pChatController.addNewChatToList(chatDto);
    }

    @Override
    public void sendNewGpChatToUsers(GpChatDto gpChatDto) throws RemoteException {
        ControllersGetter controllersGetter = ControllersGetter.getInstance();
        groupChatController groupChatController = controllersGetter.getGpChatController();
        groupChatController.addGpChatToList(gpChatDto);
    }

    @Override
    public void sendNewMessageToUser(MessageDto msgDto) throws RemoteException {

        ControllersGetter controllersGetter = ControllersGetter.getInstance();
        P2PChatController p2pChatController = controllersGetter.getP2PChatController();
        p2pChatController.SendMessageToChat(msgDto);
    }

    @Override
    public void forTesting(String userId) throws  RemoteException{
        System.out.println("CallBack to USer of Id : "+ userId);
    }


}
