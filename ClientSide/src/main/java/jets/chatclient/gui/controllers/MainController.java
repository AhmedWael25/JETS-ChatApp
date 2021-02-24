package jets.chatclient.gui.controllers;

import commons.remotes.server.SignInServiceInt;
import commons.sharedmodels.CurrentUserDto;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.RegisterLoginCoordinator;
import jets.chatclient.gui.helpers.StageCoordinator;
import jets.chatclient.gui.helpers.adapters.DTOObjAdapter;
import jets.chatclient.gui.models.UserCredentials;
import jets.chatclient.gui.utils.ConfigManager;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private static RegisterLoginCoordinator registerLoginCoordinator;
   // private RegisterLoginCoordinator registerLoginCoordinator;
   // private static final MainController mainController = new MainController();
    @FXML
    public HBox hBox;

    @FXML
    public BorderPane MBorderPane;

    @FXML
    public AnchorPane mainPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        registerLoginCoordinator = RegisterLoginCoordinator.getInstance();

        registerLoginCoordinator.initScreen(MBorderPane);
        registerLoginCoordinator.switchToLoginScreen();
    }




}
