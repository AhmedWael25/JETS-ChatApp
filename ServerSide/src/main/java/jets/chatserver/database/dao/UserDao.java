package jets.chatserver.database.dao;

import jets.chatserver.DBModels.DBUser;
import jets.chatserver.DBModels.DBUserCredintials;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface UserDao {

    //Get User By Id
    DBUser getUserById(String userId) throws SQLException;

    //Check is User exists
    boolean isUserExist(String userId) throws SQLException;

    //Get All Users from a given list of IDs
    List<DBUser> getUsersFromIds(List<String> userIds) throws SQLException;

    //Get the encoded image string from userId
    String getUserEncodedImg(String userId) throws SQLException;

    //Get User Name By Id
    String getUserNameById(String userId) throws SQLException;

    //Get User Credentials (UserId,Password)
    DBUserCredintials getUserCredentials(String userId) throws SQLException;

    boolean isUserNameExist(String userName) throws SQLException;

    boolean addUser(DBUser dbUser) throws SQLException;

    //TODO refactor naming
    //to add user on condition(Admin registered User)
    boolean updateUser(DBUser dbUser) throws SQLException;


    //Update user profile
    boolean updateUserTable(DBUser updatedUser, String userId) throws SQLException;

    boolean updateDBUserPhoto(String EncodedImage, String userId) throws SQLException;

    boolean updateDBUserStatus(int userStatus, String userId) throws SQLException;

    boolean updateDBUserPassword(String newPassword, String userId) throws SQLException;

    Map<String ,Integer> getUsersStatus(List<String> userIds) throws  SQLException;

    Integer getUserStatus(String  userId) throws  SQLException;
    boolean updateDBUserAvailability(int userAvailability, String userId) throws SQLException;

}