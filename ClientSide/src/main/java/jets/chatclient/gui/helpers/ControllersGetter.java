package jets.chatclient.gui.helpers;

import jets.chatclient.gui.controllers.ContactsController;
import jets.chatclient.gui.controllers.P2PChatController;
import jets.chatclient.gui.controllers.GroupChatController;

import java.util.Map;

public class ControllersGetter {

    private  static ControllersGetter  controllersGetter = new ControllersGetter();

    Map<String, ScreenData> screens;

    private ControllersGetter(){
        DashBoardCoordinator dashBoardCoordinator = DashBoardCoordinator.getInstance();
        screens = dashBoardCoordinator.getScreens();
    }

    public static ControllersGetter getInstance() {
        return controllersGetter;
    }


    public ContactsController  getContactsController(){
        return screens.get("contactsscreen").getLoader().getController();
    }

    public P2PChatController getP2PChatController(){
        return screens.get("chatscreen").getLoader().getController();
    }

    public GroupChatController getGpChatController(){
        return screens.get("gpchatscreen").getLoader().getController();
    }


}
