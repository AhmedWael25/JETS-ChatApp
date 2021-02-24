package jets.chatclient.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import commons.remotes.server.UserProfileServiceInt;
import commons.utils.HashEncoder;
import commons.utils.Validators;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.models.CurrentUserModel;
import org.kordamp.ikonli.javafx.FontIcon;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Optional;

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
    private FontIcon mainIcon;

    @FXML
    private FontIcon keyIcon;



    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

        lookupService();
        validateFields();

    }


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
        if(oldPassField.validate() && newPassField.validate() && confirmPassField.validate()){
            if (isValidPasswords()) {
                try {
                    boolean successUpdate = false;
                    //handle password
                    //TODO make new method to get the salt from db within the process
                    String salt = "SKgTpccoOReOUvXS/ORKuY1+mC0=";

                    String oldPassword = "";
                    Optional<String> optionalOldPassword = HashEncoder.hashPassword(oldPassField.getText(), salt);
                    if (optionalOldPassword.isPresent()) ;
                    oldPassword = optionalOldPassword.get();

                    String newPassword = "";
                    Optional<String> optionalNewPassword = HashEncoder.hashPassword(newPassField.getText(), salt);
                    if (optionalNewPassword.isPresent()) ;
                    newPassword = optionalNewPassword.get();

                    successUpdate = userProfileService.updateUserPassword(oldPassword, newPassword, currentUserModel.getPhoneNumber());

                    if (successUpdate) {

                        successNotification("Updated Successfully");
                        oldPassField.clear();
                        newPassField.clear();
                        confirmPassField.clear();
                        mainIcon.setIconColor(Color.web("#36ec0d"));
                        keyIcon.setIconColor(Color.web("#36ec0d"));
                        // Clear Success Notification after 3 ex and reset mainIcon
                        PauseTransition visiblePause = new PauseTransition(
                                Duration.seconds(3)
                        );
                        visiblePause.setOnFinished(
                                ev -> {mainIcon.setIconColor(Color.BLACK);
                                    keyIcon.setIconColor(Color.BLACK);
                                    wrongLabel.setVisible(false);
                                }
                        );
                        visiblePause.play();

                    } else {
                        failNotification("Failed to update");
                    }

                } catch (RemoteException remEx) {
                    failNotification("Server Failed");

                    System.out.println(remEx.getMessage());
                    System.out.println("Failed to Update Status in database");

                }
            }
        }else{
            failNotification("Password must be 8-20 chars.!");
            return;
        }


    }

    private boolean isValidPasswords() {
        String oldPass = oldPassField.getText();
        String newPass = newPassField.getText();
        String confirmPass = confirmPassField.getText();

        if((newPass != null) || (confirmPass != null)){
            if (!newPass.equals(confirmPass)) {

                failNotification("Does Not Match");

                return false;

            } else if (oldPass.isBlank() || newPass.isBlank() || confirmPass.isBlank()) {
               failNotification("Empty Fields");

                return false;
            } else {
                System.out.println("Text matches");
                return true;
            }
        }
        return false;
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

    void successNotification(String msg){
        wrongLabel.setStyle("-fx-text-fill: #36ec0d");
        wrongLabel.setText(msg);
        wrongLabel.setVisible(true);
        wrongIcon.setVisible(false);
    }

    void failNotification(String msg){
        wrongLabel.setStyle("-fx-text-fill: #d73838");
        wrongLabel.setText(msg);
        wrongIcon.setVisible(true);
        wrongLabel.setVisible(true);
    }

}
