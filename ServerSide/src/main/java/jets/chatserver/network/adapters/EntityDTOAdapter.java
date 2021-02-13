package jets.chatserver.network.adapters;

import commons.sharedmodels.InvitationDto;
import commons.sharedmodels.P2PChatDto;
import jets.chatserver.DBModels.DBInvitations;
import jets.chatserver.DBModels.DBP2PChat;
import jets.chatserver.DBModels.DBUser;
import jets.chatserver.database.daoImpl.UserDaoImpl;

import java.sql.SQLException;

public class EntityDTOAdapter {

    public static DBInvitations convertDtoToEntity(InvitationDto invitationDto) {

        String senderId = invitationDto.getSenderId();
        String receiverId =invitationDto.getReceiverId();

        DBInvitations dbInv = new DBInvitations();

        dbInv.setSenderId(senderId);
        dbInv.setReceiverId(receiverId);
        dbInv.setSenderName(invitationDto.getSenderName());
        dbInv.setContent(invitationDto.getInvitationContent());
        try {
            dbInv.setReceiverName(UserDaoImpl.getUserDaoInstance().getUserNameById(invitationDto.getReceiverId()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return dbInv;
    }
    public static InvitationDto convertEntityToDto(DBInvitations dbinv){
        InvitationDto invitationDto = new InvitationDto();

        //Mapping Done Here
        invitationDto.setInvitationId(dbinv.getInvitationId());
        invitationDto.setInvitationContent(dbinv.getContent());
        invitationDto.setReceiverId(dbinv.getReceiverId());
        invitationDto.setSenderId(dbinv.getSenderId());
        invitationDto.setSenderName(dbinv.getSenderName());
        invitationDto.setReceiverName(dbinv.getReceiverName());

        try {
            invitationDto.setSenderImg(UserDaoImpl.getUserDaoInstance().getUserEncodedImg(dbinv.getSenderId()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  invitationDto;
    }
    public static P2PChatDto convertEntityToDto(DBP2PChat dbChat){
        P2PChatDto chatDto = new P2PChatDto();

        String friendId = dbChat.getSecondParticipant();
        chatDto.setChatId(dbChat.getChatId());
        chatDto.setChatStartDate(dbChat.getChatStartDate());
        chatDto.setFriendId(friendId);
        chatDto.setUserId(dbChat.getFirstParticipant());

        try {
            DBUser user = UserDaoImpl.getUserDaoInstance().getUserById(friendId);
            chatDto.setFriendName(user.getDisplayedName());
            chatDto.setFriendImg(user.getImgEncoded());
            chatDto.setAvailability(user.getUserAvail());
            chatDto.setStatus(user.getUserStatus());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chatDto;
    }

}
