package jets.chatclient.gui.controllers;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.skins.JFXTextFieldSkin;
import com.jfoenix.skins.ValidationPane;
import commons.remotes.server.RegisteringClientInt;
import commons.remotes.server.SignInServiceInt;
import commons.sharedmodels.CurrentUserDto;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.RegisterLoginCoordinator;
import jets.chatclient.gui.helpers.ServicesFactory;
import jets.chatclient.gui.helpers.StageCoordinator;
import jets.chatclient.gui.helpers.adapters.DTOObjAdapter;
import jets.chatclient.gui.models.CurrentUserModel;
import jets.chatclient.gui.models.UserCredentials;
import jets.chatclient.gui.utils.ConfigManager;
import org.kordamp.ikonli.javafx.FontIcon;


import java.io.File;
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

    public Label errText;

    public JFXButton btnSignIn;

    public SignInServiceInt signInService;
    CurrentUserModel currentUserModel;
    ModelsFactory modelsFactory;
    StageCoordinator stageCoordinator;
    private RegisterLoginCoordinator registerLoginCoordinator;
    private ConfigManager configManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
         configManager = new ConfigManager();

        modelsFactory = ModelsFactory.getInstance();
        stageCoordinator = StageCoordinator.getInstance();
        currentUserModel = modelsFactory.getCurrentUserModel();
        registerLoginCoordinator = RegisterLoginCoordinator.getInstance();

                try {
                    signInService = ServicesFactory.getInstance().getSignInService();
                } catch (RemoteException | NotBoundException e) {
                    System.out.println("can't find Service");
                    e.printStackTrace();
                }
                   UserCredentials userCredentials =  configManager.readConfigFile();

                  tfPhonenumber.textProperty().bindBidirectional(currentUserModel.phoneNumberProperty());

                  //read userId from configFile if user signed out
                 if(userCredentials.getUserPhone()!=null){
                   System.out.println(userCredentials.getUserPhone());
                   tfPhonenumber.setText(userCredentials.getUserPhone());}

        btnSignIn.requestFocus();
        btnSignIn.requestFocus();
            }

    public void handleLoginBtnClick(ActionEvent e) {

        try {
            System.out.println(currentUserModel.getPhoneNumber());
            int UserRegStatus = signInService.checkUserCredentials(currentUserModel.getPhoneNumber());

            switch (UserRegStatus) {
                case 1: //user registered // redirect to password
                    registerLoginCoordinator.switchToGetPasswordScreen();
                    break;
                case 2: //user not registered // redirect to sign up
                    handleinvalidPhoneNumber(currentUserModel.getPhoneNumber());
                    break;
                case 3://user registered by admin(no data saved for user)
                    registerLoginCoordinator.switchToSignupScreen();
                    break;
                case -1 :
                    errText.setText("User is Already SignedIn!");
                    System.out.println("already signed in");
                    break;
            }

        } catch (RemoteException remoteException) {
            //TODO server down label
            errText.setText("Server is Down, Try reconnecting later");
            System.out.println("Server Down");
        }
        //TODO change the flow, passoword>> chatDashboards


    }

    private void handleinvalidPhoneNumber(String phoneNumber) {
        System.out.println("There's no user with phone number: " + phoneNumber);
        //TODO change the flow, passoword>> chatDashboard

//        registerLoginCoordinator.switchToGetPasswordScreen();
    }


    public void handleSignupBtnClick(ActionEvent e) {

        registerLoginCoordinator.switchToSignupScreen();
    }





}


