package jets.chatclient.gui.helpers.adapters;

import commons.sharedmodels.CurrentUserDto;
import commons.sharedmodels.InvitationDto;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.models.CurrentUserModel;
import jets.chatclient.gui.models.Invitation;

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
    public static List<Invitation> convertDtoList(List<InvitationDto> invitationDtoList){

        List<Invitation> invitations = invitationDtoList.parallelStream().map(invitationDto -> convertDtoToObj(invitationDto))
                .collect(Collectors.toList());
        return  invitations;
    }

    public static CurrentUserModel convertDtoToCurrentUser(CurrentUserDto currentUserDto){
        ModelsFactory modelsFactory = ModelsFactory.getInstance();
        CurrentUserModel currentUser = modelsFactory.getCurrentUserModel();

        currentUser.setPhoneNumber(currentUserDto.getUserPhone());
        currentUser.setUserName(currentUserDto.getUserName());
        currentUser.setCountry(currentUserDto.getUserCountry());
        currentUser.setGender(currentUserDto.getUserGender());
        currentUser.setEmailAddress(currentUserDto.getUserEmail());
        currentUser.setBio(currentUserDto.getUserBio());
        //TODO handle user Image, user  BD
        return currentUser;
    }
    public static CurrentUserDto convertObjToDto(CurrentUserModel currentUser){
        CurrentUserDto userDto = new CurrentUserDto();

        userDto.setUserPhone(currentUser.getPhoneNumber());
        userDto.setUserName(currentUser.getUserName());
        userDto.setUserCountry(currentUser.getCountry());
        userDto.setUserGender(currentUser.getGender());
        userDto.setUserEmail(currentUser.getEmailAddress());
        userDto.setUserBio(currentUser.getBio());
        //TODO handle user Image & BD
        return userDto;
    }

}
