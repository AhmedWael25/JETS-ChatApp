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
            try {
                fis = new FileInputStream("db.properties");
                props.load(fis);
                dbDataSource = new MysqlDataSource();
                dbDataSource.setURL(props.getProperty("MYSQL_DB_URL"));
                dbDataSource.setUser(props.getProperty("MYSQL_DB_USERNAME"));
                dbDataSource.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return dbDataSource;
        } else {
            return dbDataSource;
        }
    }
}
