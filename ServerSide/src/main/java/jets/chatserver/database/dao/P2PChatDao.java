package jets.chatserver.database.dao;

import jets.chatserver.DBModels.DBP2PChat;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface P2PChatDao {

    boolean isChatExist(String participant1,String participant2) throws SQLException;
    boolean isChatExist(int chatId) throws  SQLException;
    boolean isUserHasChat(String userId) throws SQLException;
    List<DBP2PChat> fetchAllChatsByUserId(String userId) throws SQLException;
    DBP2PChat fetchChatBetweenUsers(String participant1,String participant2) throws  SQLException;
    boolean addChat(String participant1 , String participant2) throws  SQLException;


}
