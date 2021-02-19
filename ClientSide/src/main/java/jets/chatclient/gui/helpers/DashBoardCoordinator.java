package jets.chatclient.gui.helpers;

import com.mysql.cj.conf.PropertyDefinitions;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import javax.xml.transform.sax.SAXSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DashBoardCoordinator {

    private  static  Parent dashboardContainer;
    private static final DashBoardCoordinator dashBoardCoordinator = new DashBoardCoordinator();
    private final Map<String, ScreenData> dashboardScreens = new HashMap<>();

    private DashBoardCoordinator(){}



    public  void initScreen(Parent parent){
        if(dashboardContainer == null){
            dashboardContainer = parent;
        }
        else {
            //TODO remove after debugging
            System.out.println("Screen has already been initialized");
        }
    }

    public static DashBoardCoordinator getInstance(){
        return  dashBoardCoordinator;
    }

    public void switchToChatScreen(){
        if(dashboardContainer == null){
            throw  new RuntimeException("DashBoard Container haven't been init.");
        }
        if(!dashboardScreens.containsKey("chatscreen")){
            System.out.println("Created New Chat Screen");
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/P2PChat.fxml"));
                Parent chatScreen = fxmlLoader.load();
                ScreenData screenData = new ScreenData(fxmlLoader,chatScreen);
                dashboardScreens.put("chatscreen",screenData);
                ((BorderPane)dashboardContainer).setCenter(chatScreen);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Couldn't load chat screen");
            }
        }else {
            System.out.println("loading new CHt screen");
            Parent chatScreen = dashboardScreens.get("chatscreen").getView();
            ((BorderPane)dashboardContainer).setCenter(chatScreen);
        }
    }
    public  void switchToGpChatScreen(){
        if(dashboardContainer == null){
            throw  new RuntimeException("DashBoard Container haven't been init.");
        }
        if(!dashboardScreens.containsKey("gpchatscreen")){
            System.out.println("Created New gp chat Screen");
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/groupChat.fxml"));
                Parent gpChatScreen = fxmlLoader.load();
                ScreenData screenData = new ScreenData(fxmlLoader,gpChatScreen);
                dashboardScreens.put("gpchatscreen",screenData);
                ((BorderPane)dashboardContainer).setCenter(gpChatScreen);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Couldn't load Prof screen");
            }
        }else {
            System.out.println("Loading New gp chat screen");
            Parent gpChatScreen = dashboardScreens.get("gpchatscreen").getView();
            ((BorderPane)dashboardContainer).setCenter(gpChatScreen);
        }
    }
    public void switchToGroupScreen(){
        if(dashboardContainer == null){
            throw  new RuntimeException("DashBoard Container haven't been init.");
        }
        if(!dashboardScreens.containsKey("contactsscreen")){
            System.out.println("Created Contacts Screen");
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/contactsView.fxml"));
                Parent groupsScreen = fxmlLoader.load();
                ScreenData screenData = new ScreenData(fxmlLoader,groupsScreen);
                dashboardScreens.put("contactsscreen",screenData);
                ((BorderPane)dashboardContainer).setCenter(groupsScreen);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Coudlnt load Contacts screen");
            }
        }else {
            System.out.println("Loading  Contacts screen");
            Parent groupsScreen = dashboardScreens.get("contactsscreen").getView();
            ((BorderPane)dashboardContainer).setCenter(groupsScreen);
        }
    }
    public  void switchToProfileScreen(){
        if(dashboardContainer == null){
            throw  new RuntimeException("DashBoard Container haven't been init.");
        }
        if(!dashboardScreens.containsKey("profilescreen")){
            System.out.println("Created New Profile Screen");
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/userProfile.fxml"));
                Parent profileScreen = fxmlLoader.load();
                ScreenData screenData = new ScreenData(fxmlLoader,profileScreen);
                dashboardScreens.put("profilescreen",screenData);
                ((BorderPane)dashboardContainer).setCenter(profileScreen);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Couldn't load Prof screen");
            }
        }else {
            System.out.println("Loading New Profil screen");
            Parent profileScreen = dashboardScreens.get("profilescreen").getView();
            ((BorderPane)dashboardContainer).setCenter(profileScreen);
        }
    }

    public  void initAllScreens() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/P2PChat.fxml"));
        Parent chatScreen = fxmlLoader.load();
        ScreenData screenData = new ScreenData(fxmlLoader,chatScreen);
        dashboardScreens.put("chatscreen",screenData);

         fxmlLoader = new FXMLLoader(getClass().getResource("/views/groupChat.fxml"));
        Parent gpChatScreen = fxmlLoader.load();
         screenData = new ScreenData(fxmlLoader,gpChatScreen);
        dashboardScreens.put("gpchatscreen",screenData);


         fxmlLoader = new FXMLLoader(getClass().getResource("/views/contactsView.fxml"));
        Parent groupsScreen = fxmlLoader.load();
         screenData = new ScreenData(fxmlLoader,groupsScreen);
        dashboardScreens.put("contactsscreen",screenData);


         fxmlLoader = new FXMLLoader(getClass().getResource("/views/userProfile.fxml"));
        Parent profileScreen = fxmlLoader.load();
         screenData = new ScreenData(fxmlLoader,profileScreen);
        dashboardScreens.put("profilescreen",screenData);

    }

    public Map<String, ScreenData> getScreens(){
        return  dashboardScreens;
    }

}
