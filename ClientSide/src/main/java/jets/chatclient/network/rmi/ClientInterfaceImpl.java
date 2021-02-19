package jets.chatclient.network.rmi;

import commons.remotes.client.ClientInterface;
import commons.sharedmodels.GpChatDto;
import commons.sharedmodels.InvitationDto;
import commons.sharedmodels.P2PMessageDto;
import commons.sharedmodels.GpMessageDto;
import commons.sharedmodels.P2PChatDto;
import jets.chatclient.gui.controllers.ContactsController;
import jets.chatclient.gui.controllers.P2PChatController;
import jets.chatclient.gui.controllers.GroupChatController;
import jets.chatclient.gui.helpers.ControllersGetter;
import jets.chatclient.gui.helpers.GpChatsManager;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.P2PChatManager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientInterfaceImpl extends UnicastRemoteObject implements ClientInterface {

    public ClientInterfaceImpl() throws RemoteException {
        super();
    }

    @Override
    public void sendNewInvToUser(InvitationDto invitationDto)throws  RemoteException {
        //Refactor
        ControllersGetter controllersGetter = ControllersGetter.getInstance();
        ContactsController contactsController = controllersGetter.getContactsController();
        contactsController.addInvitationToList(invitationDto);
    }

    @Override
    public void deleteInv(InvitationDto invitationDto) throws RemoteException {}

    @Override
    public void sendNewChatToUser(P2PChatDto chatDto) throws RemoteException {
        //Refactor
        ControllersGetter controllersGetter = ControllersGetter.getInstance();
        P2PChatController p2pChatController = controllersGetter.getP2PChatController();
        p2pChatController.addNewChatToList(chatDto);
    }

    @Override
    public void sendNewGpChatToUsers(GpChatDto gpChatDto) throws RemoteException {
        //Refactor
        ControllersGetter controllersGetter = ControllersGetter.getInstance();
        GroupChatController groupChatController = controllersGetter.getGpChatController();
        groupChatController.addGpChatToList(gpChatDto);
    }

    @Override
    public void sendNewP2PMessageToUser(P2PMessageDto p2pMsgDto) throws RemoteException {

        P2PChatManager p2pChatManager = ModelsFactory.getInstance().getP2PChatManager();
        p2pChatManager.addMsg(p2pMsgDto);
        System.out.println("From CALL BACK ");
    }

    @Override
    public void sendNewGpMsgToUsers(GpMessageDto gpMessageDto) throws RemoteException {
        GpChatsManager gpChatsManager = ModelsFactory.getInstance().getGpChatsManager();
        gpChatsManager.addMsg(gpMessageDto);
    }

    @Override
    public void forTesting(String userId) throws  RemoteException{
        System.out.println("CallBack to USer of Id : "+ userId);
    }


}
