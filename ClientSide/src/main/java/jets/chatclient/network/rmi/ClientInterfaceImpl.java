package jets.chatclient.network.rmi;

import commons.remotes.client.ClientInterface;
import commons.sharedmodels.GpChatDto;
import commons.sharedmodels.InvitationDto;
import commons.sharedmodels.P2PMessageDto;
import commons.sharedmodels.GpMessageDto;
import commons.sharedmodels.P2PChatDto;
import commons.utils.NotificationUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import jets.chatclient.gui.controllers.ContactsController;
import jets.chatclient.gui.controllers.P2PChatController;
import jets.chatclient.gui.controllers.GroupChatController;
import jets.chatclient.gui.helpers.*;

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
    public void sendNewMessageToUser(P2PMessageDto msgDto) throws RemoteException {

        ControllersGetter controllersGetter = ControllersGetter.getInstance();
        P2PChatController p2pChatController = controllersGetter.getP2PChatController();
        p2pChatController.SendMessageToChat(msgDto);


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
        pushGpChatNotification(gpMessageDto.getChatId(), gpMessageDto.getSenderName(), gpMessageDto.getMsgContent(),"asasas");
    }

    @Override
    public void sendNewGpFileTpUsers(byte[] fileArr, GpMessageDto gpMessageDto) {
        GpChatsManager gpChatsManager = ModelsFactory.getInstance().getGpChatsManager();
        gpChatsManager.addFileMsg(fileArr,gpMessageDto);



        System.out.println("FROM MGR" + fileArr.length);
    }

    @Override
    public void updateUserStatus(String userId, Integer status) throws RemoteException {
        GpChatsManager gpChatsManager = ModelsFactory.getInstance().getGpChatsManager();
        gpChatsManager.updateParticipantStatus(userId,status);
    }

    @Override
    public void updateUserImg(String userId, String imgEncoded) throws RemoteException {

    }

    @Override
    public void forTesting(String userId) throws  RemoteException{
        System.out.println("CallBack to USer of Id : "+ userId);
    }


}
