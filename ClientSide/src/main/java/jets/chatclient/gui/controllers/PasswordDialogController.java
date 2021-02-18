package jets.chatclient.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import commons.remotes.server.UserProfileServiceInt;
import commons.utils.Validators;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.models.CurrentUserModel;
import org.kordamp.ikonli.javafx.FontIcon;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class PasswordDialogController {

    UserProfileServiceInt userProfileService;
    CurrentUserModel currentUserModel;
    ModelsFactory modelsFactory;

    @FXML
    private JFXPasswordField newPassField;

    @FXML
    private JFXPasswordField oldPassField;

    @FXML
    private JFXPasswordField confirmPassField;

    @FXML
    private JFXButton confirmBtn;

    @FXML
    private FontIcon wrongIcon;

    @FXML
    private Label wrongLabel;

    @FXML
    private FontIcon newPassIcon;

    @FXML
    private FontIcon oldPassIcon;

    @FXML
    private FontIcon confirmPassIcon;




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
        if(isValidPasswords()){
            try{
                boolean successUpdate = false;
                successUpdate = userProfileService.updateUserPassword(newPassField.getText(), currentUserModel.getPhoneNumber());
                if(successUpdate) {
                    wrongLabel.setStyle("-fx-text-fill: #36ec0d");
                    wrongLabel.setText("Updated Successfully");
                    wrongLabel.setVisible(true);
                    confirmBtn.setDisable(true);

                }else{
                    wrongLabel.setStyle("-fx-text-fill: #d73838");
                    wrongLabel.setText("Failed to update");
                    wrongIcon.setVisible(true);
                    wrongLabel.setVisible(true);
                }

            } catch (RemoteException remEx) {
                wrongLabel.setText("Server failed");
                wrongIcon.setVisible(true);
                wrongLabel.setVisible(true);

                System.out.println(remEx.getMessage());
                System.out.println("Faild to Update Status in database");

            }

        }else{
            return;
        }


    }

    private boolean isValidPasswords() {
        String oldPass = oldPassField.getText();
        String newPass = newPassField.getText();
        String confrimPass = confirmPassField.getText();
        if ((newPass != null) | (confrimPass != null)) {
            if (!newPass.equals(confrimPass)) {
//              wrongIcon.setStyle("-fx-background-color: #d73838 ");
                wrongLabel.setStyle("-fx-text-fill: #d73838");
                wrongLabel.setText("Does Not Match");
                wrongIcon.setVisible(true);
                wrongLabel.setVisible(true);

                return false;

            } else if (oldPass.isBlank() | newPass.isBlank() | confrimPass.isBlank()) {
//                wrongIcon.setStyle("-fx-graphic:#d73838");
                wrongLabel.setStyle("-fx-text-fill: #d73838");
                wrongLabel.setText("Empty Fields");
                wrongIcon.setVisible(true);
                wrongLabel.setVisible(true);

                return false;
            } else {
                System.out.println("Text matches");
                wrongIcon.setVisible(false);
                wrongLabel.setVisible(false);

                return true;
            }
        }
        return false;
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

        lookupService();
        validateFields();




    }

    void lookupService(){
        // get an instance of current user through model factory.
        modelsFactory = ModelsFactory.getInstance();
        currentUserModel = modelsFactory.getCurrentUserModel();
        //Lookup for server services
        Registry reg = modelsFactory.getRegistry();

        try {
            userProfileService = (UserProfileServiceInt) reg.lookup("UserProfileService");
        } catch (RemoteException | NotBoundException e) {
            System.out.println("can't find Service");
            e.printStackTrace();
        }
    }

    void validateFields() {
        Validators.addPasswordValidator(oldPassField, oldPassIcon);
        Validators.addPasswordValidator(newPassField, newPassIcon);
        Validators.addPasswordValidator(confirmPassField, confirmPassIcon);
    }

}
