package jets.chatserver.network;

import jets.chatserver.network.rmi.AddFriendImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerInit   {

    public void  serverInit(){

        try{
            Registry reg = LocateRegistry.createRegistry(3000);

            //Bind Services
            reg.rebind("AddFriendService",new AddFriendImpl());



            //
            System.out.println("Server Up And Running");

        }catch (RemoteException e){

        }

    }
}
