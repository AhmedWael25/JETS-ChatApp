package jets.chatserver.network.adapters;

import commons.sharedmodels.CurrentUserDto;
import commons.sharedmodels.InvitationDto;
import jets.chatserver.DBModels.DBInvitations;
import jets.chatserver.DBModels.DBUser;
import jets.chatserver.database.daoImpl.UserDaoImpl;

import java.sql.SQLException;

public class EntityObjAdapter {

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
    public  static  InvitationDto convertEntityToDto(DBInvitations dbinv){
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

    //convert DBUSERModel From/to currentUserDTO

    public static DBUser convertDtoToEntity(CurrentUserDto currentUserDto) {

        DBUser dbUser = new DBUser();

        dbUser.setPhone(currentUserDto.getUserPhone());
        dbUser.setDisplayedName(currentUserDto.getUserName());
        dbUser.setGender(currentUserDto.getUserGender());
        dbUser.setEmail(currentUserDto.getUserEmail());
        dbUser.setCountry(currentUserDto.getUserCountry());
        dbUser.setDob(currentUserDto.getUserBirthDate());
        dbUser.setBio(currentUserDto.getUserBio());

        //TODO handle user Image seralization
        //needs refactoring
        dbUser.setImgEncoded(currentUserDto.getUserImage());
        return dbUser;
    }
    public  static  CurrentUserDto convertEntityToDto(DBUser dbUser){
        CurrentUserDto userDto = new CurrentUserDto();
        //one to one mapping
        userDto.setUserPhone(dbUser.getPhone());
        userDto.setUserName(dbUser.getDisplayedName());
        userDto.setUserGender(dbUser.getGender());
        userDto.setUserEmail(dbUser.getEmail());
        userDto.setUserCountry(dbUser.getCountry());
        userDto.setUserBirthDate(dbUser.getDob());
        userDto.setUserBio(dbUser.getBio());

        try {
            userDto.setUserImage(UserDaoImpl.getUserDaoInstance().getUserEncodedImg(dbUser.getPhone()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  userDto;
    }

}
