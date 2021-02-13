package jets.chatclient.gui.controllers;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import commons.remotes.server.RegisteringClientInt;
import commons.remotes.server.SignInServiceInt;
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
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public AnchorPane loginpane;
    public FontIcon fiPhoneNumber;
    public JFXTextField tfPhonenumber;
    public JFXButton btnSignUp;
    public JFXButton btnSignIn;

    public SignInServiceInt signInService;
    CurrentUserModel currentUserModel;
    ModelsFactory modelsFactory;
    StageCoordinator stageCoordinator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        modelsFactory = ModelsFactory.getInstance();
        currentUserModel = modelsFactory.getCurrentUserModel();
       Registry reg = modelsFactory.getRegistry();

        try {
            signInService = (SignInServiceInt) reg.lookup("SignInService");
        } catch (RemoteException e) {
            System.out.println("can't find Service");
            e.printStackTrace();
        } catch (NotBoundException e) {
            System.out.println("can't wallahy");
            e.printStackTrace();
        }

        tfPhonenumber.textProperty().bindBidirectional(currentUserModel.phoneNumberProperty());
        btnSignIn.requestFocus();
    }

    public void handleLoginBtnClick(ActionEvent e) {
        stageCoordinator = StageCoordinator.getInstance();

        try {
            int UserRegStatus = signInService.checkUserCredentials(currentUserModel.getPhoneNumber());
            switch (UserRegStatus){
                case 1: //user registered // redirect to password
                    stageCoordinator.switchToPasswordScene();
                    break;
                case 2: //user not registered // redirect to sign up
                    handleinvalidPhoneNumber(currentUserModel.getPhoneNumber());
                    break;
                case 3://user registered by admin(no data saved for user)
                    stageCoordinator.switchToSignupScene();
                    break;
            }

        } catch (RemoteException remoteException) {
            System.out.println("can't check credentials");
            remoteException.printStackTrace();
        }
        //TODO change the flow, passoword>> chatDashboards


    }

    private void handleinvalidPhoneNumber(String phoneNumber) {
        System.out.println("There's no user with phone number: "+phoneNumber);
    }


    public void handleSignupBtnClick(ActionEvent e) {
        stageCoordinator = StageCoordinator.getInstance();
        stageCoordinator.switchToSignupScene();
    }

}
