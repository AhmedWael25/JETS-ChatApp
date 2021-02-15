package jets.chatserver.database.daoImpl;

import jets.chatserver.DBModels.DBGpChat;
import jets.chatserver.database.DataSourceFactory;
import jets.chatserver.database.dao.GpChatDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class GpChatDaoImpl implements GpChatDao {


    private static volatile GpChatDaoImpl  gpChatDao = null;
    DataSource dataSource = DataSourceFactory.getDataSourceInstance();
    Connection conn;


    private GpChatDaoImpl()throws SQLException {
        conn = dataSource.getConnection();
    }

    public static GpChatDaoImpl getGpChatDaoInstance() throws SQLException {
        if(gpChatDao == null){
            synchronized (GpChatDaoImpl.class){
                if(gpChatDao == null){
                    gpChatDao = new GpChatDaoImpl();
                }
            }
        }
        return gpChatDao;
    }


    @Override
    public List<DBGpChat> getAllGpChatsOfUser(String userId) throws SQLException {

        List<DBGpChat> dbGpChats = new ArrayList<>();

        boolean hasGpChats = doesUserHasGpChats(userId);
        if(!hasGpChats) return dbGpChats;

        String query = "SELECT * FROM gpchats INNER JOIN gpchats_part ON id=gpchats_part_id WHERE part_id = ? ";
        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pd.setString(1,userId);

        ResultSet rs = pd.executeQuery();

        while (rs.next()){
            DBGpChat gpchat = getChatFromRs(rs);
            dbGpChats.add(gpchat);
        }
        return dbGpChats;
    }

    @Override
    public List<Integer> getAllGpChatsIdsOfUser(String userId) throws SQLException {
        List<Integer> gpChatsIds = new ArrayList<>();
        String query = "SELECT id FROM gpchats INNER JOIN gpchats_part ON id=gpchats_part_id WHERE part_id = ? ";
        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pd.setString(1,userId);
        ResultSet rs = pd.executeQuery();
        while (rs.next()){
            Integer id = rs.getInt("id");
            gpChatsIds.add(id);
        }
        pd.close();
        return  gpChatsIds;
    }

    @Override
    public List<String> getAllParticipantsIdsByChatId(int gpChatId) throws SQLException {

        List<String> participants = new ArrayList<>();

        String query = "SELECT part_id FROM gpchats_part WHERE gpchats_part_id = ?";
        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pd.setInt(1,gpChatId);
        ResultSet rs = pd.executeQuery();

        String str = "";
        while(rs.next()){
            str = rs.getString("part_id");
            participants.add(str);
        }
        pd.close();
        return  participants;
    }

    @Override
    public boolean doesUserHasGpChats(String userId) throws SQLException {

        boolean hasGpChats = false;

        String query = "SELECT 1 FROM gpchats_part WHERE gpchats_part_id = ?";
        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pd.setString(1,userId);

        ResultSet rs = pd.executeQuery();
        while (rs.next()){
            hasGpChats =  true;
        }
        return  hasGpChats;
    }


    @Override
    public DBGpChat getGpChatById(int gpChatId) {
        return null;
    }

    @Override
    public List<DBGpChat> getAllGpChatsByCreator(String creatorId) {
        return null;
    }

    @Override
    public String getGpChatImageById(int gpChatId) {
        return  null;
    }




    private DBGpChat getChatFromRs(ResultSet rs) throws SQLException {
        DBGpChat dbGpChat = new DBGpChat();

        dbGpChat.setGpChatId(rs.getInt("id"));
        dbGpChat.setGpChatImg(rs.getString("gpphoto"));
        dbGpChat.setGpChatName(rs.getString("gpname"));
        dbGpChat.setGpChatStartDate(rs.getString("startdate"));
        dbGpChat.setParticipantsId(getAllParticipantsIdsByChatId(rs.getInt("id")));
        dbGpChat.setGpChatAdminId(rs.getString("gpadmin"));
        dbGpChat.setGpChatDesc(rs.getString("gpdesc"));

        return dbGpChat;
    }
}
