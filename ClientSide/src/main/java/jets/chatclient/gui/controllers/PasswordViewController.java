package jets.chatclient.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import commons.remotes.server.SignInServiceInt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.StageCoordinator;
import jets.chatclient.gui.models.CurrentUserModel;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class PasswordViewController implements Initializable {

    public FontIcon fiPassword;
    public JFXPasswordField pfPassword;
    public JFXButton btnSignIn;
    public JFXButton btnSignup;

    public SignInServiceInt signInService;
    CurrentUserModel currentUserModel;
    ModelsFactory modelsFactory;
    StageCoordinator stageCoordinator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        modelsFactory = ModelsFactory.getInstance();
        currentUserModel = modelsFactory.getCurrentUserModel();
        Registry reg = modelsFactory.getRegistry();
        System.out.println("i'm in password controller");
        try {
            signInService = (SignInServiceInt) reg.lookup("SignInService");
        } catch (RemoteException | NotBoundException e) {
            System.out.println("can't find Service");
            e.printStackTrace();
        }


        //TODO check why password is cached?!
        btnSignIn.requestFocus();

    }

    public void handleSignIn(ActionEvent event) {
        stageCoordinator = StageCoordinator.getInstance();
        try {
            boolean verified = signInService.checkUserCredentials(currentUserModel.getPhoneNumber(),pfPassword.getText());
            System.out.println(verified);
            if (verified)
                stageCoordinator.switchToChatDashBoard();
//            else ;
            //handle wrong password here

        } catch (RemoteException e) {
            e.printStackTrace();
        }



    }

    public void handleSignUp(ActionEvent event) {
        stageCoordinator = StageCoordinator.getInstance();
        stageCoordinator.switchToSignupScene();

    }


}
