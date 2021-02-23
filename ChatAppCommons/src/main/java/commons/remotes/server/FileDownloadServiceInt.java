package commons.remotes.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileDownloadServiceInt extends Remote {

    byte[] downloadFile(Integer fileId) throws RemoteException;

}
