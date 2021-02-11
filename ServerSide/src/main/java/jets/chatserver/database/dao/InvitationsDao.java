package jets.chatserver.database.dao;

import jets.chatserver.DBModels.DBInvitations;

import java.sql.SQLException;
import java.util.List;

public interface InvitationsDao {

    boolean addNewInvitation(DBInvitations invitation) throws SQLException;
    List<DBInvitations> getAllUserSentInvitations(String userId) throws SQLException;
    List<DBInvitations> getAllUserReceivedInvitations(String userId) throws SQLException;
    boolean isInviteExists(String senderId,String receiverId) throws SQLException;
    boolean deleteInvitation(String senderId,String receiverId ) throws  SQLException;
    boolean deleteInvitation(DBInvitations invitation ) throws  SQLException;

    }
