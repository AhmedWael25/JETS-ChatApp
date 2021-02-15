package jets.chatserver.database.dao;

import jets.chatserver.DBModels.DBGpChat;

import java.sql.SQLException;
import java.util.List;

public interface GpChatDao {


    List<DBGpChat> getAllGpChatsOfUser(String userId) throws SQLException;
    List<Integer> getAllGpChatsIdsOfUser(String userId) throws SQLException;
    DBGpChat getGpChatById(int gpChatId);
    List<DBGpChat> getAllGpChatsByCreator(String creatorId);
    String getGpChatImageById(int gpChatId);
    List<String> getAllParticipantsIdsByChatId(int gpChatId) throws SQLException;
    boolean doesUserHasGpChats(String userId) throws SQLException;

    //List of nice to have stuff given enough time.
    //-Add User in GP Chat
    //-Del From User From Gp Chat
    //-Switch Admin in Gp Chat
}
