package jets.chatserver.gui.helpers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServerDashBoardCoordinator {
    private  static Parent dashboardContainer;
    private static final ServerDashBoardCoordinator dashBoardCoordinator = new ServerDashBoardCoordinator();
    private final Map<String, ScreenData> dashboardScreens = new HashMap<>();

    private ServerDashBoardCoordinator(){}

    public  void initScreen(Parent parent){
        if(dashboardContainer == null){
            dashboardContainer = parent;
        }
        else {
            //TODO remove after debugging
            System.out.println("Screen has already been initialized");
        }
    }
    public static ServerDashBoardCoordinator getInstance(){
        return  dashBoardCoordinator;
    }

    public void switchToStatisticsScreen(){
        if(dashboardContainer == null){
            throw  new RuntimeException("DashBoard Container haven't been init.");
        }
        if(!dashboardScreens.containsKey("statsScreen")){
            System.out.println("Created New Stats Screen");
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/StatsView.fxml"));
                Parent chatScreen = fxmlLoader.load();
                ScreenData screenData = new ScreenData(fxmlLoader,chatScreen);
                dashboardScreens.put("statsScreen",screenData);
                ((BorderPane)dashboardContainer).setCenter(chatScreen);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Couldn't load Stats screen");
            }
        }else {
            System.out.println("loading new Stats Screen");
            Parent chatScreen = dashboardScreens.get("statsScreen").getView();
            ((BorderPane)dashboardContainer).setCenter(chatScreen);
        }

    }
    public void switchToAnnouncementScreen(){
        if(dashboardContainer == null){
            throw  new RuntimeException("DashBoard Container haven't been init.");
        }
        if(!dashboardScreens.containsKey("announceScreen")){
            System.out.println("Created New Announce Screen");
            try {
                //TODO Add screen url
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/announcsView.fxml"));
                Parent chatScreen = fxmlLoader.load();
                ScreenData screenData = new ScreenData(fxmlLoader,chatScreen);
                dashboardScreens.put("announceScreen",screenData);
                ((BorderPane)dashboardContainer).setCenter(chatScreen);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Couldn't load Announce screen");
            }
        }else {
            System.out.println("loading new Announce Screen");
            Parent chatScreen = dashboardScreens.get("announceScreen").getView();
            ((BorderPane)dashboardContainer).setCenter(chatScreen);
        }

    }
    public void switchToUsersScreen(){
        if(dashboardContainer == null){
            throw  new RuntimeException("DashBoard Container haven't been init.");
        }
        if(!dashboardScreens.containsKey("usersScreen")){
            System.out.println("Created New Users Screen");
            try {
                //TODO Add screen url
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/usersView.fxml"));
                Parent chatScreen = fxmlLoader.load();
                ScreenData screenData = new ScreenData(fxmlLoader,chatScreen);
                dashboardScreens.put("usersScreen",screenData);
                ((BorderPane)dashboardContainer).setCenter(chatScreen);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Couldn't load Users screen");
            }
        }else {
            System.out.println("loading new Users Screen");
            Parent chatScreen = dashboardScreens.get("usersScreen").getView();
            ((BorderPane)dashboardContainer).setCenter(chatScreen);
        }

    }
    public void switchToAvailScreen(){
        if(dashboardContainer == null){
            throw  new RuntimeException("DashBoard Container haven't been init.");
        }
        if(!dashboardScreens.containsKey("availScreen")){
            System.out.println("Created New Availability Screen");
            try {
                //TODO Add screen url
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(""));
                Parent chatScreen = fxmlLoader.load();
                ScreenData screenData = new ScreenData(fxmlLoader,chatScreen);
                dashboardScreens.put("availScreen",screenData);
                ((BorderPane)dashboardContainer).setCenter(chatScreen);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Couldn't load Availability screen");
            }
        }else {
            System.out.println("loading new Availability Screen");
            Parent chatScreen = dashboardScreens.get("availScreen").getView();
            ((BorderPane)dashboardContainer).setCenter(chatScreen);
        }

    }

    public  void initAllScreens() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/StatsView.fxml"));
        Parent chatScreen = fxmlLoader.load();
        ScreenData screenData = new ScreenData(fxmlLoader,chatScreen);
        dashboardScreens.put("statsScreen",screenData);

        fxmlLoader = new FXMLLoader(getClass().getResource("/views/announcsView.fxml"));
        chatScreen = fxmlLoader.load();
        screenData = new ScreenData(fxmlLoader,chatScreen);
        dashboardScreens.put("announceScreen",screenData);


        fxmlLoader = new FXMLLoader(getClass().getResource("/views/usersView.fxml"));
        chatScreen = fxmlLoader.load();
        screenData = new ScreenData(fxmlLoader,chatScreen);
        dashboardScreens.put("usersScreen",screenData);


        fxmlLoader = new FXMLLoader(getClass().getResource("/views/availView.fxml"));
        chatScreen = fxmlLoader.load();
        screenData = new ScreenData(fxmlLoader,chatScreen);
        dashboardScreens.put("availScreen",screenData);

    }

    public Map<String, ScreenData> getScreens(){
        return  dashboardScreens;
    }
}
