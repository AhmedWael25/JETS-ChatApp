package jets.chatclient.network.rmi;

import commons.remotes.client.ClientInterface;
import commons.sharedmodels.*;
import commons.utils.ImageEncoderDecoder;
import commons.utils.NotificationUtils;
import javafx.application.Platform;
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
    public void pushGpChatNotification(int gchatId, String senderName, String msg,Image chatImg) throws RemoteException {
        String notificationMsg = senderName+": "+msg;
        EventHandler<ActionEvent> action = (event)->{
            DashBoardCoordinator coordinator = DashBoardCoordinator.getInstance();
            coordinator.switchToGroupScreen();
            //TODO, Switch to groupChat based on the id
        };
        NotificationUtils.showNotification("New Group Msg",notificationMsg,action, StageCoordinator.getPrimaryStage(),chatImg);
    }

    @Override
    public void pushp2pChatNotification(int chatId, String senderName, String msg,Image senderImg) throws RemoteException {
        String notificationMsg = senderName+": "+msg;
        EventHandler<ActionEvent> action = (event)->{
            DashBoardCoordinator coordinator = DashBoardCoordinator.getInstance();
            coordinator.switchToChatScreen();
            //TODO, Switch to p2pChat based on the id
        };
        NotificationUtils.showNotification("New Message",notificationMsg,action, StageCoordinator.getPrimaryStage(),senderImg);
    }


    @Override
    public void notifyUserIsOnline(String friendId) throws RemoteException {

        ContactsManager contactsManager = ModelsFactory.getInstance().getContactsManager();
        String friendName = contactsManager.getFriendName(friendId);
        Image img = contactsManager.getFriendImage(friendId);
        pushStatusNotification(friendName,img);
    }

    @Override
    public void pushStatusNotification(String friendName, Image img ) throws RemoteException {
        EventHandler<ActionEvent> action = (event)->{
            DashBoardCoordinator coordinator = DashBoardCoordinator.getInstance();
            coordinator.switchToChatScreen();
        };
        NotificationUtils.showNotification("Welcome Your Friend!" ,friendName+" Has Gone ONLINE!",action, StageCoordinator.getPrimaryStage(),img);
    }

    @Override
    public void sendNewP2PMessageToUser(P2PMessageDto p2pMsgDto) throws RemoteException {

        P2PChatManager p2PChatManager = ModelsFactory.getInstance().getP2PChatManager();
        p2PChatManager.addMsg(p2pMsgDto);
        //Push Notification
        //If msg incoming from active chat dont send NOTI
        //Otherwise Send Noti
        Integer activeChat = p2PChatManager.getActiveP2PChat();
        Integer chatIdFromMsg = p2pMsgDto.getChatId();
        DashBoardCoordinator dashboard = DashBoardCoordinator.getInstance();
        if(!activeChat.equals(chatIdFromMsg ) ||  !dashboard.isInChatScreen()){
            String senderName = p2PChatManager.getParticipantName(chatIdFromMsg);
            Image img = p2PChatManager.getParticipantImage(chatIdFromMsg);
            if(p2pMsgDto.getMsgType() == MsgType.TEXT){
                pushp2pChatNotification(chatIdFromMsg,senderName,p2pMsgDto.getMsgBody(),img);
            }else {
                String fileName = p2pMsgDto.getMsgBody().split(";")[0];
                pushp2pChatNotification(chatIdFromMsg,senderName," FILE : "+fileName,img);
            }
        }
    }
    public void pushServerAnnouncements(String serverMsg, String serverImg) throws RemoteException {
        String notificationMsg = serverMsg;
        NotificationUtils.showServerNotification(notificationMsg, StageCoordinator.getPrimaryStage(),serverImg);

    }


    @Override
    public void sendNewGpMsgToUsers(GpMessageDto gpMessageDto) throws RemoteException {

        GpChatsManager gpChatsManager = ModelsFactory.getInstance().getGpChatsManager();
        gpChatsManager.addMsg(gpMessageDto);

        Integer activeChat = gpChatsManager.getActiveChat();
        Integer chatIdFromMsg = gpMessageDto.getChatId();
        DashBoardCoordinator dashboard = DashBoardCoordinator.getInstance();
        if(!activeChat.equals(chatIdFromMsg) || !dashboard.isInGpChatScreen()){
            String senderName = gpMessageDto.getSenderName();
            Image img = gpChatsManager.getChatImg(chatIdFromMsg);
            if(gpMessageDto.getMsgType() == MsgType.TEXT){
                pushGpChatNotification(chatIdFromMsg,senderName,gpMessageDto.getMsgContent(),img);
            }else {
                String fileName = gpMessageDto.getMsgContent().split(";")[0];
                pushp2pChatNotification(chatIdFromMsg,senderName," FILE : "+fileName,img);
            }
        }
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
    public String LivenessTest() throws  RemoteException{
        return  "ACK";
    }

    @Override
    public void ReInitAllPages() throws RemoteException {

    }


}
