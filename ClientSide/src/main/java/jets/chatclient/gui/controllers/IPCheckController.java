package jets.chatclient.gui.controllers;

import com.jfoenix.controls.JFXTextField;
import commons.utils.Validators;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import jets.chatclient.gui.helpers.RegisterLoginCoordinator;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ResourceBundle;

public class IPCheckController implements Initializable {

    @FXML
    private FontIcon hostIcon;
    @FXML
    private JFXTextField hostContainer;

    private RegisterLoginCoordinator registerLoginCoordinator;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        registerLoginCoordinator = RegisterLoginCoordinator.getInstance();

        Validators.addIPlValidator(hostContainer, hostIcon);

    }

    public void connectButton(ActionEvent actionEvent) {
        registerLoginCoordinator.switchToLoginScreen();
    }

}