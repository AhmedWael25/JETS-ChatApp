package jets.chatclient.gui.helpers.adapters;

import commons.sharedmodels.CurrentUserDto;
import commons.sharedmodels.InvitationDto;
import javafx.scene.image.Image;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.models.CurrentUserModel;
import jets.chatclient.gui.models.Invitation;
import jets.chatclient.gui.models.User;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
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
        currentUser.setGender(currentUserDto.getUserGender());
        currentUser.setEmailAddress(currentUserDto.getUserEmail());
        currentUser.setCountry(currentUserDto.getUserCountry());
        //TODO handle the problem with Local date
        //the problem is birth date isn't stored
//        currentUser.setBirthdayDate(LocalDate.parse(currentUserDto.getUserBirthDate()) );
        currentUser.setBio(currentUserDto.getUserBio());



        //handling Image conversion
        byte[] imgBytes = Base64.getDecoder().decode(currentUserDto.getUserImage());
        currentUser.setImage(new Image(new ByteArrayInputStream(imgBytes)));


        return currentUser;
    }
    public static CurrentUserDto convertObjToDto(CurrentUserModel currentUser){
        CurrentUserDto userDto = new CurrentUserDto();
        //TODO complete conversion methods
        userDto.setUserPhone(currentUser.getPhoneNumber());
        userDto.setUserName(currentUser.getUserName());
        userDto.setUserGender(currentUser.getGender());
        userDto.setUserEmail(currentUser.getEmailAddress());
        userDto.setUserCountry(currentUser.getCountry());
        userDto.setDob(currentUser.getBirthdayDate().toString());
        userDto.setUserBio(currentUser.getBio());

        //TODO handle user Image & BD
        return userDto;
    }

    public static CurrentUserDto convertToUserDto(User user){
        CurrentUserDto userDto = new CurrentUserDto();

        userDto.setUserPhone(user.getUserPhone());
        userDto.setUserName(user.getUserName());
        userDto.setUserCountry(user.getUserCountry());
        userDto.setUserGender(user.getUserGender());
      //  userDto.setUserEmail(user.getUserEmail());
      //  userDto.setUserBio(user.getUserBio());
        userDto.setPassword(user.getUserPassword());
        userDto.setUserImage(user.getUserImage());
        userDto.setDob(user.getUserDateOfBirth());
         userDto.setStatus(user.getUserStatus());
         userDto.setAvailability(user.geUserAvailability());
        //TODO handle user Image & BD
        return userDto;
    }

}
