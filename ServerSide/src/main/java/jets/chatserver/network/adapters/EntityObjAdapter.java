package jets.chatserver.network.adapters;

import commons.sharedmodels.CurrentUserDto;
import commons.sharedmodels.InvitationDto;
import jets.chatserver.DBModels.DBInvitations;
import jets.chatserver.DBModels.DBUser;
import jets.chatserver.database.daoImpl.UserDaoImpl;

import java.sql.SQLException;

public class EntityObjAdapter {


    //convert DBUSERModel From/to currentUserDTO
    public static DBUser convertDtoToEntity(CurrentUserDto currentUserDto) {

        DBUser dbUser = new DBUser();

        dbUser.setPhone(currentUserDto.getUserPhone());
        dbUser.setDisplayedName(currentUserDto.getUserName());
        dbUser.setCountry(currentUserDto.getUserCountry());
        dbUser.setEmail(currentUserDto.getUserEmail());
        dbUser.setBio(currentUserDto.getUserBio());
        dbUser.setGender(currentUserDto.getUserGender());
        dbUser.setUserStatus(currentUserDto.getStatus());
        dbUser.setUserAvail(currentUserDto.getAvailability());
        dbUser.setImgEncoded(currentUserDto.getUserImage());
        dbUser.setDob(currentUserDto.getDob());
        dbUser.setPassword(currentUserDto.getPassword());


        //TODO handle user Image seralization
        return dbUser;
    }
    public  static  CurrentUserDto convertEntityToDto(DBUser dbUser){
        CurrentUserDto userDto = new CurrentUserDto();
        //one to one mapping
        userDto.setUserPhone(dbUser.getPhone());
        userDto.setUserName(dbUser.getDisplayedName());
        userDto.setUserCountry(dbUser.getCountry());
        userDto.setUserEmail(dbUser.getEmail());
        userDto.setUserBio(dbUser.getBio());
        userDto.setUserGender(dbUser.getGender());
        userDto.setDob(dbUser.getDob());
        userDto.setStatus(dbUser.getUserStatus());
        userDto.setAvailability(dbUser.getUserAvail());


        try {
            userDto.setUserImage(UserDaoImpl.getUserDaoInstance().getUserEncodedImg(dbUser.getPhone()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  userDto;
    }

}
