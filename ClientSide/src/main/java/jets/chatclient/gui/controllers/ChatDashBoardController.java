package jets.chatclient.gui.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import jets.chatclient.gui.helpers.DashBoardCoordinator;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.models.CurrentUserModel;

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
    private JFXButton profileBtn;
    @FXML
    private Circle userImageCircle;
    @FXML
    private Circle userStatusCircle;
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

    }

    @FXML
    void switchedToGroupsPane(ActionEvent event) {

    }

    @FXML
    void switchedToP2PChatPane(ActionEvent event) {

        dashBoardCoordinator.switchToChatScreen();
    }

    @FXML
    void switchedToSettingsPane(ActionEvent event) {

    }

    @FXML
    void switchedToUserProfilePane(ActionEvent event) {
        dashBoardCoordinator.switchToProfileScreen();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO REMOVE After debugging
        System.out.println("Chat Dash Board Initialized");
        ModelsFactory modelsFactory = ModelsFactory.getInstance();
        CurrentUserModel currentUserModel = modelsFactory.getCurrentUserModel();

        dashBoardCoordinator= DashBoardCoordinator.getInstance();
        dashBoardCoordinator.initScreen(borderContainer);

    }
}
