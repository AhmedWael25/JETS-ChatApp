package jets.chatclient.network.rmi;

import commons.remotes.client.ClientInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientInterfaceImpl extends UnicastRemoteObject implements ClientInterface {

    public ClientInterfaceImpl() throws RemoteException {

    }
}
