package jets.chatserver.network.adapters;

import commons.sharedmodels.*;
import jets.chatserver.DBModels.DBGpChat;
import jets.chatserver.DBModels.DBInvitations;
import jets.chatserver.DBModels.DBP2PChat;
import jets.chatserver.DBModels.DBUser;
import jets.chatserver.database.dao.FriendsDao;
import jets.chatserver.database.daoImpl.UserDaoImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public static GpChatDto convertEntityToDto(DBGpChat dbGpChat){
        GpChatDto gpChatDto = new GpChatDto();

        gpChatDto.setGpChatId(dbGpChat.getGpChatId());
        gpChatDto.setGpChatName(dbGpChat.getGpChatName());
        gpChatDto.setGpChatImage(dbGpChat.getGpChatImg());
        gpChatDto.setGpChatStartDate(dbGpChat.getGpChatStartDate());
        gpChatDto.setGrpChatAdminId(dbGpChat.getGpChatAdminId());
        gpChatDto.setGrpChatDesc(dbGpChat.getGpChatDesc());

        try{
            List<ParticipantDto> participantsDto = new ArrayList<>();
            for(String participantId:  dbGpChat.getParticipantsId()){
                ParticipantDto participant = new ParticipantDto();
                participant.setParticipantId(participantId);
                participant.setParticipantName(UserDaoImpl.getUserDaoInstance().getUserNameById(participantId));
                participant.setParticipantImage(UserDaoImpl.getUserDaoInstance().getUserEncodedImg(participantId));
                participantsDto.add(participant);
            }
            gpChatDto.setGpParticipants(participantsDto);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  gpChatDto;
    }
    public static FriendDto convertEntityToDto(DBUser user){
        FriendDto friendDto = new FriendDto();

        friendDto.setFriendId(user.getPhone());
        friendDto.setFriendImage(user.getImgEncoded());
        friendDto.setFriendName(user.getDisplayedName());

        return friendDto;
    }

}
