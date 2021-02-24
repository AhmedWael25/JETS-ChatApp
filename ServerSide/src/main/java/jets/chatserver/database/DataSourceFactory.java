package jets.chatserver.database;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DataSourceFactory {

    private static MysqlDataSource dbDataSource = null;

    private DataSourceFactory() {}

    public static DataSource getDataSourceInstance() {
        if(dbDataSource == null) {
            Properties props = new Properties();
            FileInputStream fis = null;
            //                fis = new FileInputStream("./db/db.properties");
//                props.load(fis);
            dbDataSource = new MysqlDataSource();
            dbDataSource.setURL("jdbc:mysql://localhost:3306/chatapp");
            dbDataSource.setUser("root");
            dbDataSource.setPassword("admin");
            return dbDataSource;
        } else {
            return dbDataSource;
        }
    }
}
