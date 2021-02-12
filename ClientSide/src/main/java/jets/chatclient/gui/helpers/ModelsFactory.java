package jets.chatclient.gui.helpers;


import commons.remotes.client.ClientInterface;
import jets.chatclient.gui.helpers.adapters.DTOObjAdapter;
import jets.chatclient.gui.models.CurrentUserModel;
import jets.chatclient.network.rmi.ClientInterfaceImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ModelsFactory {

    private static final ModelsFactory instance = new ModelsFactory();

    private CurrentUserModel currentUserModel = null;
    private ClientInterface clientInterface = null;
    private Registry registry = null;

    private ModelsFactory () { }

    public static ModelsFactory getInstance() {
        return instance;
    }

    public CurrentUserModel getCurrentUserModel() {
        if (currentUserModel == null) {
            currentUserModel = new CurrentUserModel();
        }
        return currentUserModel;
    }

    public  Registry getRegistry(){
        if(registry == null){
            try {
                registry = LocateRegistry.getRegistry(3000);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return registry;
    }

    public  ClientInterface getClient()   {
        if (clientInterface == null){
            try {
                clientInterface = new ClientInterfaceImpl();
            } catch (RemoteException e) {
                e.printStackTrace();
                System.out.println("Cannot Create Client Interface Impl");
            }
        }
        return  clientInterface;
    }



}
