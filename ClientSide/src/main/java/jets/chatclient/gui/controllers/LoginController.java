package jets.chatclient.gui.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.StageCoordinator;
import jets.chatclient.gui.models.CurrentUserModel;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfPassword;
    @FXML
    private Button btnLogin;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ModelsFactory modelsFactory = ModelsFactory.getInstance();
        CurrentUserModel currentUserModel = modelsFactory.getCurrentUserModel();

        tfUsername.textProperty().bindBidirectional(currentUserModel.userNameProperty());
        tfPassword.textProperty().bindBidirectional(currentUserModel.passwordProperty());
        btnLogin.requestFocus();
    }

    public void handleLoginBtnClick(ActionEvent e) {
        StageCoordinator stageCoordinator = StageCoordinator.getInstance();
        stageCoordinator.switchToChatDashBoard();
    }

    public void handleSignupBtnClick(ActionEvent e) {
        StageCoordinator stageCoordinator = StageCoordinator.getInstance();
        stageCoordinator.switchToSignupScene();
    }

}
