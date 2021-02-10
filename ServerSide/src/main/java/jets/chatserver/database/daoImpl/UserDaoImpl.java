package jets.chatserver.database.daoImpl;

import jets.chatserver.database.DataSourceFactory;
import jets.chatserver.database.dao.UserDao;
import jets.chatserver.DBModels.DBUser;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private static volatile UserDaoImpl  userDao = null;
    DataSource dataSource = DataSourceFactory.getDataSourceInstance();
    Connection conn;

    private UserDaoImpl() throws SQLException {
        conn = dataSource.getConnection();
    }

    public static UserDaoImpl getUserDaoInstance() throws SQLException {

        if(userDao == null){

            synchronized (UserDaoImpl.class){
                if(userDao == null){
                    userDao = new UserDaoImpl();
                }
            }
        }
        return userDao;
    }

//============================ DAO Implementations ================================

    @Override
    public DBUser getUserById(String userId) throws SQLException {
        DBUser user = new DBUser();
        String query = "Select * from user WHERE phone = ?";

        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pd.setString(1,userId);

        ResultSet rs = pd.executeQuery();

        //TODO Not Fully Implemented only has what i need right now, IMPL it Later
        while(rs.next()){
            user.setId(rs.getInt("id"));
            user.setDisplayedName(rs.getString("name"));
            user.setPhone(rs.getString("phone"));
        }
        pd.close();
        return  user;
    }

    public boolean isUserExist(String userId) throws  SQLException{

        DBUser user = new DBUser();
        String query = "Select * from user WHERE id = ?";

        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        pd.setString(1,userId);

        ResultSet rs = pd.executeQuery();

        while(rs.next()){
           return true;
        }
        pd.close();
        return  false;
    }

    @Override
    public List<DBUser> getUsersFromIds(List<String> userIds) throws SQLException {

        List<DBUser> users = new ArrayList<>();
        StringBuffer buff = new StringBuffer();
        for (String id : userIds){
            buff.append(id+",") ;
        }
        buff.append("69966996"); //TODO RMV, BUT NOT NOW
        String query = "SELECT * FROM user WHERE phone IN("+buff.toString()+")";

        PreparedStatement pd = conn.prepareStatement(query);
        ResultSet rs = pd.executeQuery();

        while (rs.next()){
            DBUser user = getUserFromRs(rs);
            users.add(user);
        }
        pd.close();
        return users;
    }


    //Get Users From RS
    private DBUser getUserFromRs(ResultSet rs) throws SQLException {
        //TODO To get FULL INFO USER, For now get only necessary
        DBUser user = new DBUser();
        user.setId(rs.getInt("id"));
        user.setPhone(rs.getString("phone"));
        user.setDisplayedName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setGender(rs.getString("gender"));
        user.setImgEncoded(rs.getString("image"));
        user.setCountry(rs.getString("country"));

        return  user;
    }

}
