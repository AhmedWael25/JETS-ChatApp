package commons.remotes.client;

import commons.sharedmodels.GpChatDto;
import commons.sharedmodels.InvitationDto;
import commons.sharedmodels.P2PMessageDto;
import commons.sharedmodels.GpMessageDto;
import commons.sharedmodels.P2PChatDto;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {

     void sendNewInvToUser(InvitationDto invitationDto) throws RemoteException;
     void deleteInv(InvitationDto invitationDto) throws  RemoteException;

     void sendNewChatToUser(P2PChatDto chatDto) throws  RemoteException;

    void sendNewGpChatToUsers(GpChatDto gpChatDto) throws RemoteException;


    void sendNewGpMsgToUsers(GpMessageDto gpMessageDto) throws  RemoteException;
    void sendNewGpFileTpUsers(byte[] fileArr,GpMessageDto gpMessageDto ) throws  RemoteException;
    void updateUserStatus(String userId , Integer status) throws RemoteException;
    void updateUserImg(String userId , String imgEncoded) throws RemoteException;

    void sendNewMessageToUser(P2PMessageDto msgDto) throws RemoteException;


    void pushGpChatNotification(int gchatId, String senderName, String msg,String chatImg) throws RemoteException;
    void pushp2pChatNotification(int chatId, String senderName, String msg, String senderImg) throws RemoteException;
    void pushStatusNotification(int chatId, String senderName, String status, String senderImg) throws RemoteException;

    void pushServerAnnouncements (String serverMsg,String serverImg) throws RemoteException;
    void forTesting(String userId) throws  RemoteException;

}
