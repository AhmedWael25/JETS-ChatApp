package jets.chatserver.database.daoImpl;

import jets.chatserver.database.DataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class InvitationDaoImpl {


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
}
