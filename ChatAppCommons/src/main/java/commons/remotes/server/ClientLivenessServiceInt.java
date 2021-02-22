package commons.remotes.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientLivenessServiceInt extends Remote {

    String checkServerLiveness() throws RemoteException;

}
