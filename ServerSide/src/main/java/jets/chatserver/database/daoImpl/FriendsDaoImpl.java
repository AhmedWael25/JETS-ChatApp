package jets.chatserver.database.daoImpl;

import jets.chatserver.database.DataSourceFactory;
import jets.chatserver.database.dao.FriendsDao;
import jets.chatserver.database.dao.UserDao;
import jets.chatserver.DBModels.DBUser;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendsDaoImpl  implements FriendsDao {

    private static volatile FriendsDaoImpl  friendsDao = null;
    DataSource dataSource = DataSourceFactory.getDataSourceInstance();
    Connection conn;


    private FriendsDaoImpl()throws SQLException {
        conn = dataSource.getConnection();
        conn.isClosed();
    }

    public static FriendsDaoImpl getFriendsDaoInstance() throws SQLException {

        if(friendsDao == null){
            synchronized (FriendsDaoImpl.class){
                if(friendsDao == null){
                    friendsDao = new FriendsDaoImpl();
                }
            }
        }
        return friendsDao;
    }

    @Override
    public boolean addFriend(String userId,String friendId) {



        return  true;
    }

    @Override
    public List<DBUser> getAllFriends(String userId) throws SQLException {
        List<DBUser> friends = new ArrayList<>();
        List<String> friendIds = getAllFriendsIds(userId);

        UserDao userDao = UserDaoImpl.getUserDaoInstance();
        friends = userDao.getUsersFromIds(friendIds);

        return friends;
    }

    //TESTED ALL GOOD
    @Override
    public List<String> getAllFriendsIds(String userId) throws SQLException {
        List<String> friendIds = new ArrayList<>();
        String query = "SELECT * FROM friends WHERE userid = ?";

        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        pd.setString(1,userId);
        ResultSet rs =  pd.executeQuery();

        String str;
        while (rs.next()){
            str = rs.getString("friendid");
            friendIds.add(str);
        }
        pd.close();
        return friendIds;
    }
}
