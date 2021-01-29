package jets.chatserver.gui.controllers;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import jets.chatserver.gui.helpers.ModelsFactory;
import jets.chatserver.gui.helpers.StageCoordinator;
import jets.chatserver.gui.models.CurrentUserModel;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    @FXML
    private Label lblUsername;
    @FXML
    private Label lblPassword;
    @FXML
    private Label lblEmail;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ModelsFactory modelsFactory = ModelsFactory.getInstance();
        CurrentUserModel currentUserModel = modelsFactory.getCurrentUserModel();

        lblUsername.textProperty().bindBidirectional(currentUserModel.usernameProperty());
        lblPassword.textProperty().bindBidirectional(currentUserModel.passwordProperty());
        lblEmail.textProperty().bindBidirectional(currentUserModel.emailProperty());
    }

    public void handleLogoutBtnClick() {
        StageCoordinator stageCoordinator = StageCoordinator.getInstance();
        stageCoordinator.switchToLoginScene();
    }

}
