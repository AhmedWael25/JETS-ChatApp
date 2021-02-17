package jets.chatclient.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class PasswordDialogController {

    @FXML
    private JFXPasswordField newPassTxt;

    @FXML
    private JFXPasswordField oldPassTxt;

    @FXML
    private JFXPasswordField confrimPassTxt;

    @FXML
    private JFXButton closeButton;


    @FXML
    void closeWithoutConfirm(ActionEvent event) {
        // get a handle to the stage
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        // do what you have to do
        stage.close();

    }

    @FXML
    void confirmPasswordAndClose(ActionEvent event) {

    }

}
