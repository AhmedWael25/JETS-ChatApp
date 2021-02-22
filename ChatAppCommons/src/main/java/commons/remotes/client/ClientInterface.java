package commons.remotes.client;

import commons.sharedmodels.*;
import javafx.scene.image.Image;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {

    void sendNewInvToUser(InvitationDto invitationDto) throws RemoteException;
    void deleteInv(InvitationDto invitationDto) throws  RemoteException;

    void sendNewChatToUser(P2PChatDto chatDto) throws  RemoteException;

    void sendNewGpChatToUsers(GpChatDto gpChatDto) throws RemoteException;

    void addNewFriend(ContactDto contactDto) throws  RemoteException;

    void sendNewP2PMessageToUser(P2PMessageDto msgDto) throws RemoteException;

    void sendNewGpMsgToUsers(GpMessageDto gpMessageDto) throws  RemoteException;

    void sendNewGpFileTpUsers(byte[] fileArr,GpMessageDto gpMessageDto ) throws  RemoteException;

    void updateUserStatus(String userId , Integer status) throws RemoteException;

    void updateUserImg(String userId , String imgEncoded) throws RemoteException;

    void sendNewMessageToUser(P2PMessageDto msgDto) throws RemoteException;

    void pushGpChatNotification(int gchatId, String senderName, String msg,Image chatImg) throws RemoteException;
    void pushp2pChatNotification(int chatId, String senderName, String msg, Image senderImg) throws RemoteException;
    void pushStatusNotification(String friendName, Image img ) throws RemoteException;

    void notifyUserIsOnline(String  userId) throws  RemoteException;

    String LivenessTest() throws  RemoteException;
    //For When the Server disconnects and connects back again
    void ReInitAllPages() throws  RemoteException;
}
