package jets.chatclient.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.RegisterLoginCoordinator;
import jets.chatclient.gui.helpers.StageCoordinator;
import jets.chatclient.gui.models.CurrentUserModel;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ResourceBundle;

public class PasswordViewController implements Initializable {

    public FontIcon fiPassword;
    public JFXPasswordField pfPassword;
    public JFXButton btnSignIn;
    public JFXButton btnSignup;

    private RegisterLoginCoordinator registerLoginCoordinator;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ModelsFactory modelsFactory = ModelsFactory.getInstance();
        CurrentUserModel currentUserModel = modelsFactory.getCurrentUserModel();

//        tfUsername.textProperty().bindBidirectional(currentUserModel.userNameProperty());
//        tfPassword.textProperty().bindBidirectional(currentUserModel.passwordProperty());

        btnSignIn.requestFocus();
        registerLoginCoordinator = RegisterLoginCoordinator.getInstance();

    }

    public void handleSignIn(){

    }

    public void handleSignUp(ActionEvent event) {
      registerLoginCoordinator.switchToSignupScreen();
    }


}
