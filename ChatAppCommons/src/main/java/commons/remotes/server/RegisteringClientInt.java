package commons.remotes.server;

import commons.remotes.client.ClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RegisteringClientInt extends Remote {


    //These Will firstly Register the Client into the server hashmap
    //Also a method to disconnect tha client

    //These Methods Take the Client Interface Ref + this User Unique Id (The phone in our case)
    void registerClient(ClientInterface clientInterface , String UserId) throws RemoteException;
    void disconnectClient(ClientInterface clientInterface , String UserId) throws RemoteException;
}
