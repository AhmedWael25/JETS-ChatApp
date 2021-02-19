package jets.chatclient.gui.helpers.adapters;

import commons.sharedmodels.*;
import commons.utils.ImageEncoderDecoder;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import jets.chatclient.gui.models.*;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class DTOObjAdapter {


    public static Invitation convertDtoToObj(InvitationDto invitationDto){
        Invitation inv = new Invitation();
        inv.setSenderId(invitationDto.getSenderId());
        inv.setReceiverId(invitationDto.getReceiverId());
        inv.setInvitationId(invitationDto.getInvitationId());
        inv.setSenderName(invitationDto.getSenderName());
        inv.setReceiverName(invitationDto.getReceiverName());
        inv.setSenderImg(invitationDto.getSenderImg());
        inv.setInvitationContent(invitationDto.getInvitationContent());
        return inv;
    }
    public static InvitationDto convertObjToDto(Invitation inv){
        InvitationDto invDto = new InvitationDto();
        invDto.setSenderId(inv.getSenderId());
        invDto.setReceiverId(inv.getReceiverId());
        invDto.setInvitationId(inv.getInvitationId());
        invDto.setSenderName(inv.getSenderName());
        invDto.setReceiverName(inv.getReceiverName());
        invDto.setSenderImg(inv.getSenderImg());
        invDto.setInvitationContent(inv.getInvitationContent());
        return  invDto;
    }

    public static P2PMessageModel convertDtoToObj(P2PMessageDto msgDto){

        P2PMessageModel msg = new P2PMessageModel();

        msg.setChatId(msgDto.getChatId());
        msg.setSenderId(msgDto.getSenderId());
        msg.setReceiverId(msgDto.getReceiverId());
        msg.setMsgBody(msgDto.getMsgBody());
        msg.setMsgTime(msgDto.getMsgTime());
        msg.setMsgType(msgDto.getMsgType());

        return  msg;
    }

    public static P2PMessageDto convertObjToDto(P2PMessageModel msg){

        P2PMessageDto msgDto = new P2PMessageDto();

        msgDto.setChatId(msg.getChatId());
        msgDto.setSenderId(msg.getSenderId());
        msgDto.setReceiverId(msg.getReceiverId());
        msgDto.setMsgBody(msg.getMsgBody());
        msgDto.setMsgTime(msg.getMsgTime());
        msgDto.setMsgType(msg.getMsgType());

        return msgDto;
    }

    public static P2PChatModel convertDtoToObj(P2PChatDto chatDto){

        P2PChatModel p2pchat = new P2PChatModel();

        p2pchat.setChatId(chatDto.getChatId());
        p2pchat.setChatStartDate(chatDto.getChatStartDate());
        p2pchat.setFriendStatus(chatDto.getStatus());
        p2pchat.setFriendAvailability(chatDto.getAvailability());
        p2pchat.setFriendName(chatDto.getFriendName());
        p2pchat.setFriendId(chatDto.getFriendId());

        return  p2pchat;
    }

    public  static FriendModel convertDtoToObj(FriendGpDto friendGpDto){

        FriendModel friend = new FriendModel();

        friend.setFriendId(friendGpDto.getFriendId());
        friend.setFriendName(friendGpDto.getFriendName());

        Image img = ImageEncoderDecoder.getDecodedImage(friendGpDto.getFriendImage());
        friend.setFriendImg(img);
        return  friend;
    }

    public static  GpChatModel convertDtoToObj(GpChatDto dto){
        GpChatModel gpChatModel = new GpChatModel();

        gpChatModel.setGpChatId(dto.getGpChatId());
        gpChatModel.setGpChatAdminId(dto.getGrpChatAdminId());
        gpChatModel.setGpChatName(dto.getGpChatName());
        gpChatModel.setGpChatStartDate(dto.getGpChatStartDate());
        gpChatModel.setGpChatDesc(dto.getGrpChatDesc());

        Image img = ImageEncoderDecoder.getDecodedImage(dto.getGpChatImage());
        gpChatModel.setgChatImage(img);

        List<ParticipantModel> participantsList = dto.getGpParticipants().stream().map(participantDto -> {
            ParticipantModel pModel = new ParticipantModel();

            pModel.setParticipantId(participantDto.getParticipantId());
            pModel.setParticipantName(participantDto.getParticipantName());
            Circle circle = new Circle();
            Image partImg = ImageEncoderDecoder.getDecodedImage(participantDto.getParticipantImage());
            circle.setFill(new ImagePattern(partImg));
            pModel.setParticipantImg(circle);
            pModel.setParticipantImage(partImg);

            return pModel;
        }).collect(Collectors.toList());
        gpChatModel.setGpParticipants(participantsList);

        return gpChatModel;
    }



    public  static GpMessageModel convertDtoToObj(GpMessageDto dto){
        GpMessageModel msgModel = new GpMessageModel();

        msgModel.setMsgContent(dto.getMsgContent());
        msgModel.setMsgType(dto.getMsgType());
        msgModel.setTimeStamp(dto.getTimeStamp());
        msgModel.setSenderId(dto.getSenderId());
        msgModel.setSenderName(dto.getSenderName());
        msgModel.setChatId(dto.getChatId());

        return  msgModel;
    }

    public  static GpMessageDto convertoObjToDto(GpMessageModel model){
        GpMessageDto dto = new GpMessageDto();

        dto.setMsgContent(model.getMsgContent());
        dto.setMsgType(model.getMsgType());
        dto.setTimeStamp(model.getTimeStamp());
        dto.setSenderId(model.getSenderId());
        dto.setSenderName(model.getSenderName());
        dto.setChatId(model.getChatId());

        return  dto;
    }

    public static List<Invitation> convertDtoInvitationList(List<InvitationDto> invitationDtoList){

        List<Invitation> invitations = invitationDtoList.parallelStream().map(invitationDto -> convertDtoToObj(invitationDto))
                .collect(Collectors.toList());
        return  invitations;
    }
    public static List<P2PChatModel> convertDtop2pChatList(List<P2PChatDto> p2PChatDtoList){
        List<P2PChatModel> invitations = p2PChatDtoList.parallelStream().map(invitationDto -> convertDtoToObj(invitationDto))
                .collect(Collectors.toList());
        return  invitations;
    }

    public static  List<FriendModel> convertDtoGpFriendList(List<FriendGpDto> friendGpDtoList){


        List<FriendModel> friendModelList = null;
        friendModelList = friendGpDtoList.parallelStream().map(DTOObjAdapter::convertDtoToObj)
                .collect(Collectors.toList());

        return friendModelList;
    }

    public  static  List<GpChatModel> convertDtoGpChat(List<GpChatDto> gpChatDtos){
        List<GpChatModel> chatModels = null;
        chatModels = gpChatDtos.parallelStream().map(DTOObjAdapter::convertDtoToObj)
                .collect(Collectors.toList());

        return chatModels;
    }

}
