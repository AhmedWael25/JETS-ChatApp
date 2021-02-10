package jets.chatserver.database.dao;

import jets.chatserver.sharedModels.DBUser;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    //Get User By Id
    DBUser getUserById(String userId) throws SQLException;
    //Check is User exists
    boolean isUserExist(String userId) throws  SQLException;
    //Get All Users from a given list of IDs
    List<DBUser>getUsersFromIds(List<String> userIds) throws SQLException;

}