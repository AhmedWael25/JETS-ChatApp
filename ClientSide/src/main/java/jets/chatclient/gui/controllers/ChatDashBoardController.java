package jets.chatclient.gui.controllers;

import com.jfoenix.controls.JFXButton;
import commons.remotes.server.RegisteringClientInt;
import commons.remotes.server.UpdateStatusServiceInt;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import jets.chatclient.gui.helpers.*;
import jets.chatclient.gui.models.CurrentUserModel;
import jets.chatclient.gui.utils.ConfigManager;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
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

        ModelsFactory modelsFactory = ModelsFactory.getInstance();
        currentUserModel = modelsFactory.getCurrentUserModel();
        currentUserModel.bindToUserAvatar(userImageCircle);
        currentUserModel.bindToUserStatus(userStatusCircle);
        try {
            dashBoardCoordinator= DashBoardCoordinator.getInstance();
            dashBoardCoordinator.initScreen(borderContainer);
            dashBoardCoordinator.initAllScreens();
            dashBoardCoordinator.switchToGpChatScreen();
            activateBtn(groupChatBtn);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Register Client With Server
        //First Get Our Registry Through models factory
        //Then look up the service we require
        //Update Status To All Users
        try {
            registeringClientService = ServicesFactory.getInstance().getRegisterClientService();
            registeringClientService.registerClient(modelsFactory.getClient(), currentUserModel.getPhoneNumber());
            UpdateStatusServiceInt updatingService = ServicesFactory.getInstance().getUpdateService();
            updatingService.updateStatus(currentUserModel.getPhoneNumber(),1);
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
                UpdateStatusServiceInt updateStatusService = ServicesFactory.getInstance().getUpdateService();
                updateStatusService.updateStatus(currentUserModel.getPhoneNumber(),0);
                registeringClientService.disconnectClient(currentUserModel.getPhoneNumber());
                registerLoginCoordinator = RegisterLoginCoordinator.getInstance();
                stageCoordinator = StageCoordinator.getInstance();
                stageCoordinator.clearSceneData();
                registerLoginCoordinator.clearScreens();
                dashBoardCoordinator.clearScreens();
                stageCoordinator.switchToMainScene();

            } catch (FileNotFoundException | RemoteException | NotBoundException e) {
                e.printStackTrace();
            }



    }
}
