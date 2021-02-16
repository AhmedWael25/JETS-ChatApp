package jets.chatclient.gui.helpers.adapters;

import commons.sharedmodels.FriendGpDto;
import commons.sharedmodels.InvitationDto;
import commons.sharedmodels.P2PChatDto;
import commons.utils.ImageEncoderDecoder;
import javafx.scene.image.Image;
import jets.chatclient.gui.models.FriendModel;
import jets.chatclient.gui.models.Invitation;
import jets.chatclient.gui.models.P2PChatModel;

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
        System.out.println("INV DTO:"+ invDto);
        return  invDto;
    }

    public static P2PChatModel convertDtoToObj(P2PChatDto chatDto){

        P2PChatModel p2pchat = new P2PChatModel();

        p2pchat.setChatId(chatDto.getChatId());
        p2pchat.setChatStartDate(chatDto.getChatStartDate());
        p2pchat.setFriendStatus(chatDto.getStatus());
        p2pchat.setFriendAvailability(chatDto.getAvailability());
        p2pchat.setFriendName(chatDto.getFriendName());
        p2pchat.setFriendId(chatDto.getFriendId());

        //TODO Refactor INTO img utils
        System.out.println("asdasdasd" + chatDto.getFriendImg());
        byte[] dst = Base64.getDecoder().decode(chatDto.getFriendImg());
        Image img = new Image(new ByteArrayInputStream(dst));
        p2pchat.setFriendImg(img);
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

}
