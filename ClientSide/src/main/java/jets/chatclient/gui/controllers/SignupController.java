package jets.chatclient.gui.controllers;


import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.StageCoordinator;
import jets.chatclient.gui.utils.Countries;
import jets.chatclient.gui.utils.Validators;
import jets.chatclient.gui.models.CurrentUserModel;
import org.kordamp.ikonli.javafx.FontIcon;


import java.net.URL;
import java.util.*;

public class SignupController implements Initializable {


    public AnchorPane bgimage;

    public JFXTextField tfDisplayname;
    public JFXComboBox<Label> cbCountry;
    public JFXTextField tfPhonenumber;
    public JFXPasswordField pfPassword;
    public JFXDatePicker dpBirthdate;
    public JFXComboBox<Label> cbGender;

    public JFXButton btnRegister;
    public JFXButton btnSignIn;

    public FontIcon fiDisplayName;
    public FontIcon fiCountry;
    public FontIcon fiPhoneNumber;
    public FontIcon fiPassword;
    public FontIcon fiCalendar;
    public FontIcon fiGender;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ModelsFactory modelsFactory = ModelsFactory.getInstance();
        CurrentUserModel currentUserModel = modelsFactory.getCurrentUserModel();

        btnRegister.requestFocus();
        List<String> genders = Arrays.asList("male","female");
        fillComboBox(cbGender,genders);
        List<String> countries = Countries.getAll();
        fillComboBox(cbCountry,countries);




        Validators.addNameValidator(tfDisplayname, fiDisplayName);
        Validators.addPhoneNumberValidator(tfPhonenumber, fiPhoneNumber);
//        Validators.addRequiredValidator(tfPhonenumber, fiPhoneNumber);
        Validators.addPasswordValidator(pfPassword, fiPassword);
        Validators.addRequiredValidator(cbCountry, fiCountry);
        Validators.addRequiredValidator(cbGender, fiGender);
        Validators.addRequiredValidator(dpBirthdate, fiCalendar);


//        pfConfirmPassword.focusedProperty().addListener((o, old, foucs) -> fiPasswordConfirm.setIconColor(foucs ? pfConfirmPassword.getFocusColor() : pfConfirmPassword.getUnFocusColor()));

        // binding data of current user
//        tfUsername.textProperty().bindBidirectional(currentUserModel.usernameProperty());
//        tfPassword.textProperty().bindBidirectional(currentUserModel.passwordProperty());
//        tfEmail.textProperty().bindBidirectional(currentUserModel.emailProperty());


    }

    public void handleLoginBtnClick(ActionEvent e) {
        StageCoordinator stageCoordinator = StageCoordinator.getInstance();
        stageCoordinator.switchToLoginScene();

    }


    public void handleSignupBtnClick(ActionEvent e) {
        StageCoordinator stageCoordinator = StageCoordinator.getInstance();
        //TODO add db validation then mv to signin
        stageCoordinator.switchToLoginScene();
    }

    public void fillComboBox(JFXComboBox<Label> comboBox , List<String> values){

        values.stream().forEach(o -> comboBox.getItems().add(new Label(o.toString())));
        comboBox.setEditable(true);
        comboBox.setConverter(new StringConverter<Label>() {
            @Override
            public String toString(Label object) {
                return object==null?"": object.getText();
            }
            @Override
            public Label fromString(String string) {
                return new Label(string);
            }
        });
    }
}

