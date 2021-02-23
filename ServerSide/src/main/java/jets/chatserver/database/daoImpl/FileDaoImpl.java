package jets.chatserver.database.daoImpl;

import com.mysql.cj.jdbc.Blob;
import jets.chatserver.database.DataSourceFactory;
import jets.chatserver.database.dao.FileDao;

import javax.sql.DataSource;
import javax.sql.rowset.serial.SerialBlob;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FileDaoImpl implements FileDao {

    private static volatile FileDaoImpl  fileDao = null;
    DataSource dataSource = DataSourceFactory.getDataSourceInstance();
    Connection conn;

    private FileDaoImpl()throws SQLException {
        conn = dataSource.getConnection();
    }


    public static FileDao getFileDaoInstance() throws SQLException {
        if(fileDao == null){
            synchronized (FileDaoImpl.class){
                if(fileDao == null){
                    fileDao = new FileDaoImpl();
                }
            }
        }
        return fileDao;
    }


    @Override
    public Integer saveFile(byte[] byteArr, Integer chatId, String senderId, String fileName) throws SQLException {

        String query = "SELECT id FROM files ORDER BY id DESC LIMIT 1";
        PreparedStatement pd = conn.prepareStatement(query);
        ResultSet rs = pd.executeQuery();

        int lastId = 0;
        if (rs.next()) {
            lastId = rs.getInt("id");
            System.out.println("FIRST TIME  YASTA");
        }
        int newId = lastId+1;

         query = "INSERT INTO files(id,filename,senderId,chatId,content) VALUES(?,?,?,?,?)";
        pd = conn.prepareStatement(query);
        pd.setInt(1,newId);
        pd.setString(2,fileName);
        pd.setString(3,senderId);
        pd.setInt(4,chatId);
//        pd.setBytes(5,byteArr);


        InputStream inputStream = new ByteArrayInputStream(byteArr);
        pd.setBinaryStream(5,inputStream);

        pd.executeUpdate();

        return  newId;
    }

    @Override
    public byte[] getFile(Integer fileId) throws SQLException {

        String query = "SELECT content from files WHERE id = ?";
        PreparedStatement pd = conn.prepareStatement(query);
        pd.setInt(1,fileId);
        ResultSet rs = pd.executeQuery();

        System.out.println("fileId"+fileId);

        rs.next();
        byte[] arr = rs.getBytes("content");
        System.out.println("FROM SERVER:"+arr.length);
        return  arr;
    }
}
