package jets.chatserver.database.daoImpl;

import javafx.scene.image.Image;
import jets.chatserver.DBModels.DBInvitations;
import jets.chatserver.DBModels.DBUserCredintials;
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
        String query = "Select * from user WHERE phone = ?";

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

        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = pd.executeQuery();

        while (rs.next()){
            DBUser user = getUserFromRs(rs);
            users.add(user);
        }
        pd.close();
        return users;
    }

    @Override
    public String getUserEncodedImg(String userId) throws SQLException {

        String query = "SELECT image FROM user WHERE phone = ?";

        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pd.setString(1,userId);

        ResultSet rs =pd.executeQuery();

        String str = null;
        while (rs.next()){
            str = rs.getString("image");
        }
        return  str;
    }

    @Override
    public String getUserNameById(String userId) throws SQLException {
        String query = "SELECT name FROM user WHERE phone = ?";

        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pd.setString(1,userId);

        ResultSet rs =pd.executeQuery();
        String str = null;
        while (rs.next()){
            str = rs.getString("name");
        }
        return  str;
    }
    @Override
    public DBUserCredintials getUserCredentials(String userId) throws SQLException {
        DBUserCredintials userCredintials = new DBUserCredintials();
        String query = "SELECT * FROM user WHERE phone = ?";

        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pd.setString(1,userId);

        ResultSet rs =pd.executeQuery();
        while (rs.next()){
            userCredintials.setUserPassword(rs.getString("password"));
            userCredintials.setUserName(rs.getString("name"));
        }
        userCredintials.setUserId(userId);
        return userCredintials;
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

    @Override
    public boolean updateUserTable(DBUser dbupdatedUser, String userId) throws SQLException{
        //Check if the updated phone number is valid or not
        boolean isUserExist = isUserExist(dbupdatedUser.getPhone());
        if (!isUserExist){
            System.out.println("User does not exist.");
            return false;
        }
        //The Query Statement
        String query = "UPDATE user SET phone=?, name=?, email=?, country=?, bio=? WHERE phone=? ";

        //Set the prepared statement
        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pd.setString(1, dbupdatedUser.getPhone());
        //TODO: make mrthods getDisplayedName() and getUserName() consistence
        pd.setString(2, dbupdatedUser.getDisplayedName());
        pd.setString(3, dbupdatedUser.getEmail());
        pd.setString(4, dbupdatedUser.getCountry());
        pd.setString(5, dbupdatedUser.getBio());

        //WHERE
        pd.setString(6, userId);

        //Execute, check the result and return
        int rowCount = pd.executeUpdate();
        if (rowCount == 1){
            pd.close();
            System.out.println("Database updated successfully.");
            return  true;
        }
        pd.close();
        return false;


    }


    @Override
    public boolean updateDBUserPhoto(String EncodedImage, String userId) throws SQLException{
        //Check if the updated phone number is valid or not
        boolean isUserExist = isUserExist(userId);
        if (!isUserExist){
            System.out.println("User does not exist.");
            return false;
        }
        //The Query Statement
        String query = "UPDATE user SET image=? WHERE phone=? ";

        //Set the prepared statement
        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pd.setString(1, EncodedImage);

        pd.setString(2, userId);

        //Execute, check the result and return
        int rowCount = pd.executeUpdate();
        if (rowCount == 1){
            pd.close();
            System.out.println("Database updated successfully.");
            return  true;
        }
        pd.close();
        return false;
    }

    @Override
    public boolean updateDBUserStatus(int userStatus, String userId) throws SQLException {
        //Check if the updated phone number is valid or not
        boolean isUserExist = isUserExist(userId);
        if (!isUserExist){
            System.out.println("User does not exist.");
            return false;
        }
        //The Query Statement
        String query = "UPDATE user SET status=? WHERE phone=? ";

        //Set the prepared statement
        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pd.setInt(1, userStatus);

        pd.setString(2, userId);

        //Execute, check the result and return
        int rowCount = pd.executeUpdate();
        if (rowCount == 1){
            pd.close();
            System.out.println("Database updated successfully.");
            return  true;
        }
        pd.close();
        return false;
    }
}
