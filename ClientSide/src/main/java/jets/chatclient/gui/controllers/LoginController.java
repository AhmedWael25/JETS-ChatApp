package jets.chatclient.gui.controllers;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.RegisterLoginCoordinator;
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
    @FXML
    public HBox HBox;

    public JFXButton btnSignIn;

    public MainController mainController;

   private RegisterLoginCoordinator registerLoginCoordinator;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ModelsFactory modelsFactory = ModelsFactory.getInstance();
        CurrentUserModel currentUserModel = modelsFactory.getCurrentUserModel();

//        tfUsername.textProperty().bindBidirectional(currentUserModel.userNameProperty());
//        tfPassword.textProperty().bindBidirectional(currentUserModel.passwordProperty());

           registerLoginCoordinator = RegisterLoginCoordinator.getInstance();

           btnSignIn.requestFocus();
    }

    public void handleLoginBtnClick(ActionEvent e) {
        //TODO change the flow, passoword>> chatDashboard
        registerLoginCoordinator.switchToGetPasswordScreen();
    }


    public void handleSignupBtnClick(ActionEvent e) {
        registerLoginCoordinator.switchToSignupScreen();
    }

}
