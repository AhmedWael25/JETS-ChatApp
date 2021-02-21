package jets.chatclient.gui.controllers;

import com.jfoenix.controls.JFXButton;
import commons.remotes.server.RegisteringClientInt;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.Window;
import jets.chatclient.App;
import jets.chatclient.gui.helpers.*;
import jets.chatclient.gui.models.CurrentUserModel;
import jets.chatclient.gui.models.UserCredentials;
import jets.chatclient.gui.utils.ConfigManager;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class ChatDashBoardController implements Initializable {

    @FXML
    public StackPane mainContainer;
    @FXML
    public BorderPane borderContainer;
    @FXML
    private GridPane sideMenu;
    @FXML
    private Circle userImageCircle;
    @FXML
    private Circle userStatusCircle;
    @FXML
    private JFXButton profileBtn;
    @FXML
    private JFXButton chatBtn;
    @FXML
    private JFXButton groupChatBtn;
    @FXML
    private JFXButton groupsBtn;
    @FXML
    private JFXButton settingsBtn;

    @FXML
    private JFXButton signOutBtn;

    @FXML
    private JFXButton exitBtn;





    private DashBoardCoordinator dashBoardCoordinator;
    private StageCoordinator stageCoordinator;
    private RegisterLoginCoordinator registerLoginCoordinator;
    private RegisteringClientInt registeringClientService;
    private CurrentUserModel currentUserModel;
    private   ModelsFactory modelsFactory;

    @FXML
    void switchedToGPChatPane(ActionEvent event) {
        dashBoardCoordinator.switchToGpChatScreen();
        activateBtn(groupChatBtn);
    }

    @FXML
    void switchedToGroupsPane(ActionEvent event) {
        dashBoardCoordinator.switchToGroupScreen();
        activateBtn(groupsBtn);
    }

    @FXML
    void switchedToP2PChatPane(ActionEvent event) {

        dashBoardCoordinator.switchToChatScreen();
        activateBtn(chatBtn);
    }

    @FXML
    void switchedToSettingsPane(ActionEvent event) {

    }

    @FXML
    void switchedToUserProfilePane(ActionEvent event) {
        dashBoardCoordinator.switchToProfileScreen();
        activateBtn(profileBtn);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO REMOVE After debugging
        System.out.println("Chat Dash Board Initialized");

         modelsFactory = ModelsFactory.getInstance();
       currentUserModel = modelsFactory.getCurrentUserModel();
        //TODO rmv after Debugging
        System.out.println(currentUserModel);
        dashBoardCoordinator= DashBoardCoordinator.getInstance();
        dashBoardCoordinator.initScreen(borderContainer);

        activateBtn(chatBtn);

        //Register Client With Server
        //First Get Our Registry Through models factory
        //Then look up the service we require
        try {
            Registry reg = modelsFactory.getRegistry();
            registeringClientService = (RegisteringClientInt) reg.lookup("RegisteringService");

            //TODO Remove this ==> U Assumed the current user ID(Phone) is 1 Only For testing purposes
            //TODO SHOULD be replaced with current user model
            registeringClientService.registerClient(modelsFactory.getClient(), "7");

         } catch (RemoteException  | NotBoundException e) {
            e.printStackTrace();
        }
    }

    private void activateBtn(JFXButton btn){
        deActivateAllBtns();
        ObservableList<String> btnStyleClasses = btn.getStyleClass();
        if (!btnStyleClasses.contains("sideMenuBtn-active")){
            btnStyleClasses.add("sideMenuBtn-active");

            if (!btn.equals(profileBtn)){
                FontIcon n = (FontIcon) btn.getGraphic();
                n.setIconColor(Paint.valueOf("#636b61"));
            }
        }
        if (btnStyleClasses.contains("sideMenuBtn-inactive")){
            btnStyleClasses.remove("sideMenuBtn-inactive");
        }

    }

    private  void deActivateBtn(JFXButton btn){
        ObservableList<String> btnStyleClasses = btn.getStyleClass();
        if (!btnStyleClasses.contains("sideMenuBtn-inactive")){
            btnStyleClasses.add("sideMenuBtn-inactive");

            if (!btn.equals(profileBtn)){
                FontIcon n = (FontIcon) btn.getGraphic();
                n.setIconColor(Paint.valueOf("#ffffff"));
            }
        }
        if (btnStyleClasses.contains("sideMenuBtn-active")){
            btnStyleClasses.remove("sideMenuBtn-active");
        }
    }

    private  void deActivateAllBtns(){

        deActivateBtn(profileBtn);
        deActivateBtn(chatBtn);
        deActivateBtn(groupChatBtn);
        deActivateBtn(groupsBtn);
        deActivateBtn(settingsBtn);
    }



    @FXML
    private void exitApp(){
      /* try {
          String userPassword =  registeringClientService.getEncryptedPassword(currentUserModel.getPhoneNumber());
            System.out.println(userPassword);
          String userPhone = currentUserModel.getPhoneNumber();
            UserCredentials userCredentials = new UserCredentials(userPhone,userPassword);
          ConfigManager configManager = new ConfigManager();
          configManager.createConfigFile(userCredentials);
          registeringClientService.disconnectClient(userPhone);
            App.closeApp();
            Runtime rt = Runtime.getRuntime();
            rt.exit(0);
        } catch (RemoteException e) {
            e.printStackTrace();
        } */
        ExitFromApp.ExitApplication();
    }

    @FXML
    private void signOutUser(){
            PrintWriter writer = null;
            try {
                writer = new PrintWriter("config.xml");
                writer.print("");
                writer.close();
                ConfigManager configManager = new ConfigManager();
                configManager.saveUserId(currentUserModel.getPhoneNumber());
                registeringClientService.disconnectClient(currentUserModel.getPhoneNumber());
                registerLoginCoordinator = RegisterLoginCoordinator.getInstance();
                stageCoordinator = StageCoordinator.getInstance();
                stageCoordinator.clearSceneData();
                registerLoginCoordinator.clearScreens();
                dashBoardCoordinator.clearScreens();
                stageCoordinator.switchToMainScene();



            } catch (FileNotFoundException  | RemoteException e) {
                e.printStackTrace();
            }



    }
}
