package jets.chatserver.gui.helpers;


import commons.remotes.client.ClientInterface;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ModelsFactory {

    private static final ModelsFactory instance = new ModelsFactory();
    private Map<String, ClientInterface> currentConnectedUsers = new ConcurrentHashMap<>();;

    private ModelsFactory() { }

    public static ModelsFactory getInstance() {
        return instance;
    }


    public Map<String, ClientInterface> getCurrentConnectedUsers(){

        if(currentConnectedUsers == null){
         currentConnectedUsers = new ConcurrentHashMap<>();
        }
        return currentConnectedUsers;
    }



}
