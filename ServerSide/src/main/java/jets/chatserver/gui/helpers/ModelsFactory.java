package jets.chatserver.gui.helpers;



import commons.remotes.client.ClientInterface;


import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ModelsFactory {

    private static final ModelsFactory instance = new ModelsFactory();
    private Map<String, ClientInterface> currentConnectedUsers = new ConcurrentHashMap<>();
    private  ChatBotManager botManager = null;
    private Registry registry = null;

    private ModelsFactory() { }

    public static ModelsFactory getInstance() {
        return instance;
    }


    public ChatBotManager getChatBotManager(){
        if(botManager == null){
            botManager = new ChatBotManager();
        }
        return botManager;
    }

    public Map<String, ClientInterface> getCurrentConnectedUsers(){

        if(currentConnectedUsers == null){
         currentConnectedUsers = new ConcurrentHashMap<>();
        }

        return currentConnectedUsers;
    }

    public Registry getRegistry() throws RemoteException {
        if(registry == null){
            registry = LocateRegistry.createRegistry(3000);
        }
        return  registry;
    }
}
