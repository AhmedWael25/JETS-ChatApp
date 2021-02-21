package jets.chatclient.network.rmi;

import commons.remotes.client.ClientInterface;
import commons.sharedmodels.*;
import commons.utils.NotificationUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import jets.chatclient.gui.controllers.ContactsController;
import jets.chatclient.gui.controllers.P2PChatController;
import jets.chatclient.gui.controllers.GroupChatController;
import jets.chatclient.gui.helpers.*;
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

        P2PChatManager manager = ModelsFactory.getInstance().getP2PChatManager();
//        manager.addP2PChat(chatDto);

    }

    @Override
    public void sendNewGpChatToUsers(GpChatDto gpChatDto) throws RemoteException {
        //Refactor
        ControllersGetter controllersGetter = ControllersGetter.getInstance();
        GroupChatController groupChatController = controllersGetter.getGpChatController();
        groupChatController.addGpChatToList(gpChatDto);
    }

    @Override
    public void addNewFriend(ContactDto contactDto) throws RemoteException {
        ContactsManager manager = ModelsFactory.getInstance().getContactsManager();
        manager.addNewContact(contactDto);
    }


    @Override
    public void pushGpChatNotification(int gchatId, String senderName, String msg,String chatImg) throws RemoteException {
        String notificationMsg = senderName+": "+msg;
        EventHandler<ActionEvent> action = (event)->{
            DashBoardCoordinator coordinator = DashBoardCoordinator.getInstance();
            coordinator.switchToGroupScreen();
            //TODO, Switch to groupChat based on the id
        };
        NotificationUtils.showNotification("New Group Msg",notificationMsg,action, StageCoordinator.getPrimaryStage(),"asdasds");
        P2PChatManager p2pChatManager = ModelsFactory.getInstance().getP2PChatManager();
//        p2pChatManager.addMsg(p2pMsgDto);
        System.out.println("From CALL BACK ");
    }

    @Override
    public void pushp2pChatNotification(int chatId, String senderName, String msg,String senderImg) throws RemoteException {
        String notificationMsg = senderName+": "+msg;
        EventHandler<ActionEvent> action = (event)->{
            DashBoardCoordinator coordinator = DashBoardCoordinator.getInstance();
            coordinator.switchToChatScreen();
            //TODO, Switch to p2pChat based on the id
        };
        NotificationUtils.showNotification("New Message",notificationMsg,action, StageCoordinator.getPrimaryStage(),senderImg);
    }
    @Override
    public void pushStatusNotification(int chatId, String senderName, String status, String senderImg) throws RemoteException {
        String notificationMsg = senderName+"is "+status+" now!";
        EventHandler<ActionEvent> action = (event)->{
//            DashBoardCoordinator coordinator = DashBoardCoordinator.getInstance();
//            coordinator.switchToChatScreen();
            //TODO, Switch to p2pChat based on the id
            System.out.println("El7amdulaaaaaah");
        };
        NotificationUtils.showNotification("Status Changed",notificationMsg,action, StageCoordinator.getPrimaryStage(),senderImg);
    }

    @Override
    public void sendNewGpMsgToUsers(GpMessageDto gpMessageDto) throws RemoteException {

        GpChatsManager gpChatsManager = ModelsFactory.getInstance().getGpChatsManager();
        gpChatsManager.addMsg(gpMessageDto);
//        pushGpChatNotification(gpMessageDto.getChatId(), gpMessageDto.getSenderName(), gpMessageDto.getMsgContent(),"asasas");
    }



    @Override
    public void sendNewP2PMessageToUser(P2PMessageDto p2pMsgDto) throws RemoteException {

        P2PChatManager p2PChatManager = ModelsFactory.getInstance().getP2PChatManager();
        p2PChatManager.addMsg(p2pMsgDto);
        //Push Notification
    }



    @Override
    public void sendNewGpFileTpUsers(byte[] fileArr, GpMessageDto gpMessageDto) {
        GpChatsManager gpChatsManager = ModelsFactory.getInstance().getGpChatsManager();
        gpChatsManager.addFileMsg(fileArr,gpMessageDto);
    }



    @Override
    public void updateUserStatus(String userId, Integer status) throws RemoteException {
        GpChatsManager gpChatsManager = ModelsFactory.getInstance().getGpChatsManager();
        gpChatsManager.updateParticipantStatus(userId,status);

        P2PChatManager p2PChatManager = ModelsFactory.getInstance().getP2PChatManager();
        p2PChatManager.updateFriendStatus(userId,status);

        ContactsManager contactsManager = ModelsFactory.getInstance().getContactsManager();
        contactsManager.updateContactStatus(userId,status);
    }

    @Override
    public void updateUserImg(String userId, String imgEncoded) throws RemoteException {

        //Update in GP manager
        GpChatsManager gpChatsManager = ModelsFactory.getInstance().getGpChatsManager();
        gpChatsManager.updateParticipantImg(userId,imgEncoded);
        //Update in P2P Manager
        P2PChatManager p2PChatManager = ModelsFactory.getInstance().getP2PChatManager();
        p2PChatManager.updateFriendImg(userId,imgEncoded);
        //Update in contact list

        ContactsManager contactsManager = ModelsFactory.getInstance().getContactsManager();
        contactsManager.updateContactImg(userId,imgEncoded);

    }

    @Override
    public void sendNewMessageToUser(P2PMessageDto msgDto) throws RemoteException {

    }

    @Override
    public void forTesting(String userId) throws  RemoteException{
        System.out.println("CallBack to USer of Id : "+ userId);
    }


}
