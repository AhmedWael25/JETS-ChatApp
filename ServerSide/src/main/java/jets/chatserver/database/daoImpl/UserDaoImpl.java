package jets.chatserver.database.daoImpl;

import jets.chatserver.DBModels.DBUserCredintials;
import jets.chatserver.DBModels.UserData;
import jets.chatserver.database.DataSourceFactory;
import jets.chatserver.database.dao.UserDao;
import jets.chatserver.DBModels.DBUser;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

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
            user.setPhone(rs.getString("phone"));
            user.setDisplayedName(rs.getString("name"));
            user.setGender(rs.getString("gender"));
            user.setEmail(rs.getString("email"));
            user.setCountry(rs.getString("country"));
            user.setDob(rs.getDate("dob").toString());
            user.setBio(rs.getString("bio"));
            user.setImgEncoded(rs.getString("image"));
            user.setUserStatus(rs.getInt("status"));
            user.setUserAvail(rs.getInt("availability"));
        }
        pd.close();
        return  user;
    }

    public boolean isUserExist(String userId) throws  SQLException{

        String query = "Select * from user WHERE phone = ?";

        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        pd.setString(1,userId);

        ResultSet rs = pd.executeQuery();

        while(rs.next()){
            pd.close();
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

    public List<UserData> getUsersforDashBoard() throws SQLException {
        List<UserData> users = new ArrayList<>();
        String query = "SELECT * FROM user";

        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = pd.executeQuery();

        while (rs.next()){
            UserData user = new UserData();
            user.setPhone(rs.getString("phone"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setGender(rs.getString("gender"));
            user.setCountry(rs.getString("country"));
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
        pd.close();
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
        pd.close();
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
        user.setUserAvail(rs.getInt("availability"));
        user.setUserStatus(rs.getInt("status"));
        return  user;
    }

    public boolean isUserNameExist(String userName) throws  SQLException{

        String query = "Select * from user WHERE name = ?";

        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        pd.setString(1,userName);

        ResultSet rs = pd.executeQuery();

        while(rs.next()){
            return true;
        }
        pd.close();
        return  false;
    }

    @Override
    public boolean addUser(DBUser dbUser) throws SQLException {


  String query = "INSERT INTO user(phone,name,gender,password,country,dob, image,status,availability) VALUES(?,?,?,?,?,?,?,?,?)";

   PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        pd.setString(1,dbUser.getPhone());
        pd.setString(2,dbUser.getDisplayedName());
        pd.setString(3,dbUser.getGender());
        pd.setString(4,dbUser.getPassword());
        pd.setString(5,dbUser.getCountry());
        pd.setString(6,dbUser.getDob());
        pd.setString(7,dbUser.getImgEncoded());
        //TODO CHnaged to be def of 0
        pd.setInt(8,0);
        pd.setInt(9,0);

        int rowCount = pd.executeUpdate();
        if (rowCount == 1){
            pd.close();
            return  true;
        }
        pd.close();
        return false;


    }

    @Override
    public boolean updateUser(DBUser dbUser) throws SQLException {
        String query = "UPDATE user set  name=?,gender=?,password=?,country=?,dob=?, image=?,status=?,availability=? WHERE phone=?";

        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pd.setString(1,dbUser.getDisplayedName());
        pd.setString(2,dbUser.getGender());
        pd.setString(3,dbUser.getPassword());
        pd.setString(4,dbUser.getCountry());
        pd.setString(5,dbUser.getDob());
        pd.setString(6,dbUser.getImgEncoded());
        pd.setInt(7,dbUser.getUserStatus());
        pd.setInt(8,dbUser.getUserAvail());
        pd.setString(9,dbUser.getPhone());

        int rowCount = pd.executeUpdate();
        if (rowCount == 1){
            pd.close();
            return  true;
        }
        pd.close();
        return false;



    }


    @Override
    public boolean updateUserTable(DBUser dbupdatedUser, String userId) throws SQLException{
        //Check if the updated phone number is valid or not
        boolean isUserExist = isUserExist(userId);
        if (!isUserExist){
            System.out.println("User does not exist.");
            return false;
        }
        //The Query Statement
        String query = "UPDATE user SET name=?, email=?, dob=?, country=?, bio=? WHERE phone=? ";

        //Set the prepared statement
        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        pd.setString(1, dbupdatedUser.getDisplayedName());
        pd.setString(2, dbupdatedUser.getEmail());
        pd.setString(3, dbupdatedUser.getDob());
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
    public boolean updateDBUserPassword( String newPassword, String userId) throws SQLException{

        //The Query Statement
        String query = "UPDATE user SET password=? WHERE phone=?";

        //Set the prepared statement
        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        pd.setString(1, newPassword);
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
    public Map<String, Integer> getUsersStatus(List<String> userIds) throws SQLException {

        Map<String, Integer> usersStatus = new HashMap<>();

        StringBuffer buff = new StringBuffer();
        for (String id : userIds){
            buff.append(id+",") ;
        }
        buff.append("69966996"); //TODO RMV, BUT NOT NOW
        String query = "SELECT phone, status FROM user WHERE phone IN("+buff.toString()+")";

        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
       ResultSet rs =  pd.executeQuery();

       while (rs.next()){
           usersStatus.put(rs.getString("phone"),rs.getInt("status"));
       }
       return  usersStatus;
    }

    @Override
    public Integer getUserStatus(String userId) throws SQLException {

        Integer status = 0;

        if(!isUserExist(userId)) return status;

        String query = "SELECT status FROM user WHERE phone = ?";
        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        pd.setString(1,userId);
        ResultSet rs = pd.executeQuery();

        while (rs.next()){
            status = rs.getInt("status");
        }

        return status;
    }

    @Override
    public Map<String, Integer> getUsersperCounry() throws SQLException {
        Map <String, Integer> numUsers = new LinkedHashMap<>();
        String query="SELECT country, COUNT(*)  FROM user GROUP BY country ORDER BY COUNT(*) DESC ";
        PreparedStatement pd = conn.prepareStatement(query);
        ResultSet rs = pd.executeQuery();

        while (rs.next()){
            numUsers.put(rs.getString(1),rs.getInt(2));
        }
        return numUsers;
    }

    @Override
    public Integer getGenderCount(String gender) throws SQLException {
        Integer count = -1;
        String query="SELECT COUNT(*)  FROM user WHERE gender=? ";
        PreparedStatement pd = conn.prepareStatement(query);
        pd.setString(1,gender);
        ResultSet rs = pd.executeQuery();
        if (rs.next())
           count= rs.getInt(1);
        return count;
    }

    @Override
    public Integer getAvailabilityCount(Integer availability) throws SQLException {
        Integer count = -1;
        String query="SELECT COUNT(*)  FROM user WHERE availability=? ";
        PreparedStatement pd = conn.prepareStatement(query);
        pd.setInt(1,availability);
        ResultSet rs = pd.executeQuery();
        if (rs.next())
            count= rs.getInt(1);
        return count;
    }

    @Override
    public void initAllUsersStatus() throws SQLException {
        String  query = "UPDATE user set status = 0, availability = 0";
        PreparedStatement pd = conn.prepareStatement(query);
        pd.executeUpdate();
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
            System.out.println("status is " + userStatus);
            return  true;
        }
        pd.close();
        return false;
    }
    @Override
    public boolean updateDBUserAvailability(int userAvailability, String userId) throws SQLException {
        //Check if the updated phone number is valid or not
        boolean isUserExist = isUserExist(userId);
        if (!isUserExist){
            System.out.println("User does not exist.");
            return false;
        }
        //The Query Statement
        String query = "UPDATE user SET availability=? WHERE phone=? ";

        //Set the prepared statement
        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pd.setInt(1, userAvailability);

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
