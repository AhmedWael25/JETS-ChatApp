package jets.chatserver.database.dao;

import jets.chatserver.DBModels.DBUser;

import java.sql.SQLException;
import java.util.List;

public interface FriendsDao {


    boolean addFriend(String userId,String friendId) throws SQLException;
    List<DBUser> getAllFriends(String userId) throws SQLException;
    List<String> getAllFriendsIds(String userId) throws SQLException;
    boolean areFriends(String userId,String friendId) throws SQLException;

}
