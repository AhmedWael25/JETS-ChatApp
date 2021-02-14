package jets.chatclient.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import jets.chatclient.gui.helpers.RegisterLoginCoordinator;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private static RegisterLoginCoordinator registerLoginCoordinator;
   // private RegisterLoginCoordinator registerLoginCoordinator;
   // private static final MainController mainController = new MainController();
    @FXML
    public HBox hBox;

    @FXML
    public BorderPane MBorderPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        registerLoginCoordinator = RegisterLoginCoordinator.getInstance();
        registerLoginCoordinator.initScreen(MBorderPane);
        registerLoginCoordinator.switchToLoginScreen();
    }



}
