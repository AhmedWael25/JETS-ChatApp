package jets.chatclient.gui.helpers;


import commons.remotes.client.ClientInterface;

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
    private  GpChatsManager gpChatsManager = null;
    private  P2PChatManager p2pChatMngr = null;
    private  ContactsManager contactsManager = null;

    private ModelsFactory () { }

    public static ModelsFactory getInstance() {
        return instance;
    }

    public ContactsManager getContactsManager(){
        if (contactsManager == null) {
            contactsManager = new ContactsManager();
        }
        return contactsManager;
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

    public GpChatsManager getGpChatsManager(){
        if(gpChatsManager == null){
            gpChatsManager = new GpChatsManager();
        }
        return  gpChatsManager;
    }

    public P2PChatManager getP2PChatManager(){
        if(p2pChatMngr == null){
            p2pChatMngr = new P2PChatManager();
        }
        return  p2pChatMngr;
    }



}
