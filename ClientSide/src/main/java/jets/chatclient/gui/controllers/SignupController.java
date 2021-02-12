package jets.chatclient.gui.controllers;


import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.StageCoordinator;
import jets.chatclient.gui.utils.ColorBinder;
import jets.chatclient.gui.utils.Validators;
import jets.chatclient.gui.models.CurrentUserModel;
import org.kordamp.ikonli.javafx.FontIcon;


import java.net.URL;
import java.util.ResourceBundle;

public class SignupController implements Initializable {


    public AnchorPane bgimage;

    public JFXTextField tfDisplayname;
    public JFXComboBox<?> cbCountry;
    public JFXTextField tfPhonenumber;
    public JFXPasswordField pfPassword;
    public JFXDatePicker dpBirthdate;
    public JFXComboBox cbGender;

    public JFXButton btnRegister;
    public JFXButton btnSignIn;

    public FontIcon fiDisplayName;
    public FontIcon fiPhoneNumber;
    public FontIcon fiPassword;
    public FontIcon fiCalendar;
    public FontIcon fiGender;


    @FXML
    void handeSignIn(ActionEvent event) {

    }

    @FXML
    void handleRegister(ActionEvent event) {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ModelsFactory modelsFactory = ModelsFactory.getInstance();
        CurrentUserModel currentUserModel = modelsFactory.getCurrentUserModel();

        //binding isn't finished Yet **********************************************
//        ColorBinder.BindColor(tfDisplayname,fiDisplayName);
//        ColorBinder.BindColor(tfPhonenumber,fiPhoneNumber);
//        ColorBinder.BindColor(pfPassword,fiPassword);
//        ColorBinder.BindColor(cbCountry,fiPhoneNumber);
//        //we have to bind comoboBox to phoneNumber
//        ColorBinder.BindColor(dpBirthdate,fiCalendar);
//        ColorBinder.BindColor(cbGender,fiGender);





        Validators.addNameValidator(tfDisplayname,fiDisplayName);
        Validators.addPhoneNumberValidator(tfPhonenumber);
        Validators.addPasswordValidator(pfPassword);
        Validators.addRequiredValidator(cbCountry);
        Validators.addRequiredValidator(cbGender);
        Validators.addRequiredValidator(dpBirthdate);


//        pfConfirmPassword.focusedProperty().addListener((o, old, foucs) -> fiPasswordConfirm.setIconColor(foucs ? pfConfirmPassword.getFocusColor() : pfConfirmPassword.getUnFocusColor()));

        // binding data of current user
//        tfUsername.textProperty().bindBidirectional(currentUserModel.usernameProperty());
//        tfPassword.textProperty().bindBidirectional(currentUserModel.passwordProperty());
//        tfEmail.textProperty().bindBidirectional(currentUserModel.emailProperty());


    }

    public void handleLoginBtnClick() {
        StageCoordinator stageCoordinator = StageCoordinator.getInstance();
        stageCoordinator.switchToLoginScene();
    }

    public void handleSignupBtnClick() {
        System.out.println("Send to Server Signup data and show errors if any");
    }



}

