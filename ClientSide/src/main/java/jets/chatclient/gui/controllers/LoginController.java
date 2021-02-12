package jets.chatclient.gui.controllers;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.StageCoordinator;
import jets.chatclient.gui.models.CurrentUserModel;
import org.kordamp.ikonli.javafx.FontIcon;


import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public AnchorPane loginpane;
    public FontIcon fiPhoneNumber;

    public JFXTextField tfPhonenumber;
    public JFXButton btnSignUp;

    public JFXButton btnSignIn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ModelsFactory modelsFactory = ModelsFactory.getInstance();
        CurrentUserModel currentUserModel = modelsFactory.getCurrentUserModel();

//        tfUsername.textProperty().bindBidirectional(currentUserModel.userNameProperty());
//        tfPassword.textProperty().bindBidirectional(currentUserModel.passwordProperty());

        btnSignIn.requestFocus();
    }

    public void handleLoginBtnClick(ActionEvent e) {
        StageCoordinator stageCoordinator = StageCoordinator.getInstance();
        //TODO change the flow, passoword>> chatDashboard
        stageCoordinator.switchToPasswordScene();

    }


    public void handleSignupBtnClick(ActionEvent e) {
        StageCoordinator stageCoordinator = StageCoordinator.getInstance();
        stageCoordinator.switchToSignupScene();
    }

}
