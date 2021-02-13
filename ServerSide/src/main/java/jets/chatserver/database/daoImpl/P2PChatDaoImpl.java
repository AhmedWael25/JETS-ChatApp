package jets.chatserver.database.daoImpl;

import jets.chatserver.DBModels.DBP2PChat;
import jets.chatserver.DBModels.DBUser;
import jets.chatserver.database.DataSourceFactory;
import jets.chatserver.database.dao.P2PChatDao;

import javax.sql.DataSource;
import javax.xml.transform.Result;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class P2PChatDaoImpl implements P2PChatDao {

    private static volatile P2PChatDaoImpl  p2pChatDao = null;
    DataSource dataSource = DataSourceFactory.getDataSourceInstance();
    Connection conn;

    private P2PChatDaoImpl() throws SQLException {
        conn = dataSource.getConnection();
        conn.isClosed();
    }

    public static P2PChatDaoImpl getP2PChatDaoInstance() throws SQLException {
        if(p2pChatDao == null){
            synchronized (FriendsDaoImpl.class){
                if(p2pChatDao == null){
                    p2pChatDao = new P2PChatDaoImpl();
                }
            }
        }
        return p2pChatDao;
    }


    //Check if users provided already has a chat record in the DB
    @Override
    public boolean isChatExist(String participant1, String participant2) throws SQLException {
        boolean isChatExist = false;
        String query = "SELECT * FROM p2pchats WHERE part1_id = ? AND part2_id = ?";
        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pd.setString(1,participant1);
        pd.setString(2,participant2);

        ResultSet rs = pd.executeQuery();
        if (rs.next()){
            isChatExist = true;
        }
        pd.close();
        return  isChatExist;
    }

    @Override
    public boolean isChatExist(int chatId) {
        return false;
    }

    @Override
    public boolean isUserHasChat(String userId)  throws  SQLException {
        boolean isUserHasChat = false;
        String query = "SELECT 1 FROM p2pchats WHERE part1_id = ?";
        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pd.setString(1,userId);

        ResultSet rs = pd.executeQuery();
        if (rs.next()){
            isUserHasChat = true;
        }
        pd.close();
        return  isUserHasChat;
    }


    @Override
    public List<DBP2PChat> fetchAllChatsByUserId(String userId) throws SQLException {

        List<DBP2PChat> dbp2PChats = new ArrayList<>();

        String query = "SELECT * FROM p2pchats WHERE part1_id = ?";
        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pd.setString(1,userId);

        ResultSet rs = pd.executeQuery();

        while(rs.next()){
            DBP2PChat chat = getP2PChatFromRs(rs);
            dbp2PChats.add(chat);
        }
        pd.close();
        return dbp2PChats;
    }

    @Override
    public DBP2PChat fetchChatBetweenUsers(String participant1, String participant2) throws SQLException {
        DBP2PChat dbp2PChat = null;
        String query = "SELECT * FROM p2pchats WHERE part1_id = ? AND part2_id = ?";
        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pd.setString(1,participant1);
        pd.setString(2,participant2);

        ResultSet rs = pd.executeQuery();
        while (rs.next()){
            dbp2PChat = getP2PChatFromRs(rs);
        }
        return dbp2PChat;
    }

    @Override
    public boolean addChat(String participant1, String participant2) throws SQLException {

        boolean isChatExist = isChatExist(participant1,participant2);
        if(isChatExist) return  false;

        String query = "INSERT INTO p2pchats(part1_id,part2_id) VALUES(?,?)";
        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pd.setString(1,participant1);
        pd.setString(2,participant2);
        pd.executeUpdate();

        pd.setString(1,participant2);
        pd.setString(2,participant1);
        pd.executeUpdate();

        pd.close();
        return true;
    }


    private DBP2PChat getP2PChatFromRs(ResultSet rs) throws SQLException {
        DBP2PChat dbp2PChat = new DBP2PChat();
        dbp2PChat.setChatId(rs.getInt("chat_id"));
        dbp2PChat.setFirstParticipant(rs.getString("part1_id"));
        dbp2PChat.setSecondParticipant(rs.getString("part2_id"));
        dbp2PChat.setChatStartDate(rs.getString("startdate"));
        return dbp2PChat;
    }


}
