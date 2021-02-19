package jets.chatclient.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import commons.remotes.server.SignInServiceInt;
import commons.sharedmodels.CurrentUserDto;
import commons.utils.HashEncoder;
import commons.utils.NotificationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.RegisterLoginCoordinator;
import jets.chatclient.gui.helpers.StageCoordinator;
import jets.chatclient.gui.helpers.adapters.DTOObjAdapter;
import jets.chatclient.gui.models.CurrentUserModel;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Optional;
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


    private RegisterLoginCoordinator registerLoginCoordinator;


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
        registerLoginCoordinator = RegisterLoginCoordinator.getInstance();

    }

    public void handleSignIn(ActionEvent event) {
        stageCoordinator = StageCoordinator.getInstance();
        try {
            //TODO fetch salt from DB
            String salt="SKgTpccoOReOUvXS/ORKuY1+mC0=";
            String password="";
            Optional<String> optionalpassword = HashEncoder.hashPassword(pfPassword.getText(),salt);
            if(optionalpassword.isPresent());
            password=optionalpassword.get();
            boolean verified = signInService.checkUserCredentials(currentUserModel.getPhoneNumber(),password );

            if (verified) {
                CurrentUserDto userDto= signInService.signUserIn(currentUserModel.getPhoneNumber());
                DTOObjAdapter.convertDtoToCurrentUser(userDto);
                stageCoordinator.switchToChatDashBoard();

            }
            else{
                //TODO handle wrong password here

            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void handleSignUp(ActionEvent event) {
      registerLoginCoordinator.switchToSignupScreen();
    }


}
