package commons.remotes.server;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UpdateStatusServiceInt extends Remote {

        boolean updateStatus(String userId, Integer status) throws RemoteException;

}
