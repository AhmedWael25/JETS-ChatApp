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
    private final Map<String, Parent> dashboardScreens = new HashMap<>();

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
            throw  new RuntimeException("DashBoard Container havenot ben init.");
        }
        if(!dashboardScreens.containsKey("chatscreen")){
            System.out.println("Created New Chat Screen");
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/testchatview.fxml"));
                Parent chatScreen = fxmlLoader.load();
                dashboardScreens.put("chatscreen",chatScreen);
                ((BorderPane)dashboardContainer).setCenter(chatScreen);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Coudlnt load chat screen");
            }
        }else {
            System.out.println("loading new CHt screen");
            Parent chatScreen = dashboardScreens.get("chatscreen");
            ((BorderPane)dashboardContainer).setCenter(chatScreen);
        }
    }
    public  void switchToGpChatScreen(){

    }
    public void switchToGroupScreen(){

    }
    public  void switchToProfileScreen(){
        if(dashboardContainer == null){
            throw  new RuntimeException("DashBoard Container havenot ben init.");
        }
        if(!dashboardScreens.containsKey("profilescreen")){
            System.out.println("Created New Profile Screen");
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/testprofileview.fxml"));
                Parent profileScreen = fxmlLoader.load();
                dashboardScreens.put("profilescreen",profileScreen);
                ((BorderPane)dashboardContainer).setCenter(profileScreen);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Coudlnt load Prof screen");
            }
        }else {
            System.out.println("Loading New Profil screen");
            Parent profileScreen = dashboardScreens.get("profilescreen");
            ((BorderPane)dashboardContainer).setCenter(profileScreen);
        }
    }

    public  void switchToSettingsScreen(){

    }



}
