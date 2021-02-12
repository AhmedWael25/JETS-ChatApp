package jets.chatclient.gui.controllers;

import com.jfoenix.controls.JFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class PasswordDialogController {

    @FXML
    private JFXPasswordField newPassTxt;

    @FXML
    private JFXPasswordField oldPassTxt;

    @FXML
    private JFXPasswordField confrimPassTxt;

    @FXML
    void closeWithoutConfirm(ActionEvent event) {

    }

    @FXML
    void confirmPasswordAndClose(ActionEvent event) {

    }

}
