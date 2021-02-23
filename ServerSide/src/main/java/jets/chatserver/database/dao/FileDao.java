package jets.chatserver.database.dao;

import java.sql.SQLException;

public interface FileDao {

    Integer saveFile(byte[] byteArr,Integer chatId,String senderId,String fileName) throws SQLException;
    byte[] getFile(Integer fileId) throws SQLException;

}
