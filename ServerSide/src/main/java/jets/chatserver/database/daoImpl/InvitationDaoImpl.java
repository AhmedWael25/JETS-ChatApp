package jets.chatserver.database.daoImpl;

import jets.chatserver.DBModels.DBInvitations;
import jets.chatserver.database.DataSourceFactory;
import jets.chatserver.database.dao.InvitationsDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InvitationDaoImpl implements InvitationsDao {


    private static volatile InvitationDaoImpl  invitationDao = null;
    DataSource dataSource = DataSourceFactory.getDataSourceInstance();
    Connection conn;

    private InvitationDaoImpl()throws SQLException {
        conn = dataSource.getConnection();
        conn.isClosed();
    }

    public static InvitationDaoImpl getInvitationDaoInstance() throws SQLException {

        if(invitationDao == null){
            synchronized (InvitationDaoImpl.class){
                if(invitationDao == null){
                    invitationDao = new InvitationDaoImpl();
                }
            }
        }
        return invitationDao;
    }


    @Override
    public boolean addNewInvitation(DBInvitations invitation) throws SQLException {

        //TODO Check for errors if user sent
        boolean isInviteExists = isInviteExists(invitation.getSenderId(),invitation.getReceiverId());

        if (isInviteExists) return false; //returning false denoting that the invitation already exists
        String query = "INSERT INTO invitations(senderid,receiverid,content) VALUES(?,?,?)";

        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        System.out.println(invitation.getSenderId()+invitation.getReceiverId());
        pd.setString(1,invitation.getSenderId());
        pd.setString(2,invitation.getReceiverId());
        //TODO To Be Changed To the dynamic invitation content
        pd.setString(3,"You got a new Friend Request ");
//        pd.setString(3,invitation.getContent());

        int rowCount = pd.executeUpdate();
        if (rowCount == 1){
            pd.close();
            return  true;
        }
        pd.close();
        return false;
    }

    @Override
    public List<DBInvitations> getAllUserSentInvitations(String userId) throws SQLException {
        List<DBInvitations> senderInvitations = new ArrayList<>();
        String query = "SELECT * FROM invitations WHERE senderID = ?";

        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        pd.setString(1,userId);

        ResultSet rs = pd.executeQuery();

        while (rs.next()){
            DBInvitations invitation = getInvitationFromResultSet(rs);
            senderInvitations.add(invitation);
        }

        pd.close();
        return senderInvitations;
    }

    @Override
    public List<DBInvitations> getAllUserReceivedInvitations(String userId) throws SQLException {

        List<DBInvitations> senderInvitations = new ArrayList<>();
        String query = "SELECT * FROM invitations WHERE receiverid = ?";

        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        pd.setString(1,userId);

        ResultSet rs = pd.executeQuery();

        while(rs.next()){
            DBInvitations invitation = getInvitationFromResultSet(rs);
            senderInvitations.add(invitation);
        }

        pd.close();
        return senderInvitations;
    }


    @Override
    public boolean isInviteExists(String senderId,String receiverId) throws SQLException {

        String query = "SELECT * FROM invitations WHERE senderid = ? AND receiverid = ?";

        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pd.setString(1,senderId);
        pd.setString(2,receiverId);

        ResultSet rs = pd.executeQuery();
        if (rs.next()){
            return true;
        }
        pd.close();
        return false;
    }

    @Override
    public boolean deleteInvitation(String senderId, String receiverId) throws SQLException {

        String query = "DELETE FROM invitations WHERE senderid = ? AND receiverid = ?";

        PreparedStatement pd = conn.prepareStatement(query,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pd.setString(1,senderId);
        pd.setString(2,receiverId);

        int rowCount = pd.executeUpdate();
        pd.close();
        if (rowCount == 1){
            return  true;
        }
        return  false;
    }

    @Override
    public boolean deleteInvitation(DBInvitations invitation) throws SQLException {
       return deleteInvitation(invitation.getSenderId(),invitation.getReceiverId());
    }

    private DBInvitations getInvitationFromResultSet(ResultSet rs) throws SQLException {

        DBInvitations invitation = new DBInvitations();

        invitation.setInvitationId(rs.getInt("invitationid"));
        invitation.setSenderId(rs.getString("senderid"));
        invitation.setReceiverId(rs.getString("receiverid"));
        invitation.setContent(rs.getString("content"));
        invitation.setInvitationDate(rs.getString("invitationtime"));

        return invitation;
    }

}
