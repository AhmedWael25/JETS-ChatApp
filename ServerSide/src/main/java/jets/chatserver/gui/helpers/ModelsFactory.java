package jets.chatserver.gui.helpers;


public class ModelsFactory {

    private static final ModelsFactory instance = new ModelsFactory();

    private  ChatBotManager botManager = null;

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

}
