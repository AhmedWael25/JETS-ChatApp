package jets.chatclient.gui.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import jets.chatclient.gui.helpers.DashBoardCoordinator;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.models.CurrentUserModel;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
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


    private DashBoardCoordinator dashBoardCoordinator;

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
        ModelsFactory modelsFactory = ModelsFactory.getInstance();
        CurrentUserModel currentUserModel = modelsFactory.getCurrentUserModel();

        dashBoardCoordinator= DashBoardCoordinator.getInstance();
        dashBoardCoordinator.initScreen(borderContainer);

        activateBtn(chatBtn);
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
}