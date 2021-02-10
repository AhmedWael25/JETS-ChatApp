package jets.chatserver.database.dao;

import jets.chatserver.sharedModels.DBFriend;
import jets.chatserver.sharedModels.DBUser;

import java.sql.SQLException;
import java.util.List;

public interface FriendsDao {


    boolean addFriend(String userId,String friendId);
    List<DBUser> getAllFriends(String userId) throws SQLException;
    List<String> getAllFriendsIds(String userId) throws SQLException;



}
