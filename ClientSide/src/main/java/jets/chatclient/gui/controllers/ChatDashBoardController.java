package jets.chatclient.gui.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatDashBoardController implements Initializable {

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

    @FXML
    void switchedToGPChatPane(ActionEvent event) {

    }

    @FXML
    void switchedToGroupsPane(ActionEvent event) {

    }

    @FXML
    void switchedToP2PChatPane(ActionEvent event) {

    }

    @FXML
    void switchedToSettingsPane(ActionEvent event) {

    }

    @FXML
    void switchedToUserProfilePane(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Chat Dash Board Initialized");
    }
}
