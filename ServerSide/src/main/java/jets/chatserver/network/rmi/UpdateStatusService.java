package jets.chatserver.network.rmi;

import commons.remotes.client.ClientInterface;
import commons.remotes.server.UpdateStatusServiceInt;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

public class UpdateStatusService   extends UnicastRemoteObject implements UpdateStatusServiceInt {

    Map<String, ClientInterface> currentConnectedUsers = null;

    protected UpdateStatusService() throws RemoteException {
    }

    public UpdateStatusService(Map<String, ClientInterface> currentConnectedUsers) throws RemoteException {
        super();
        this.currentConnectedUsers = currentConnectedUsers;
    }

    @Override
    public boolean updateStatus(String userId, Integer status) throws RemoteException {

        currentConnectedUsers.forEach((id, ci) -> {
            if(!id.equals(userId)){
                try {
                    ci.updateUserStatus(userId,status);
//                      clientInterface.pushStatusNotification(50,"lololo","Avail","isas");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        return  true;
    }
}
