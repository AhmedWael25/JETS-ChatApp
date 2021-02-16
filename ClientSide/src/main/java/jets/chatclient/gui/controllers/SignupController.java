package jets.chatclient.gui.controllers;


import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.RegisterLoginCoordinator;
import jets.chatclient.gui.utils.ComboBoxUtils;
import jets.chatclient.gui.utils.Countries;
import jets.chatclient.gui.utils.HashEncoder;
import jets.chatclient.gui.utils.Validators;
import jets.chatclient.gui.models.CurrentUserModel;
import org.kordamp.ikonli.javafx.FontIcon;


import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;
import java.util.Arrays;


public class SignupController implements Initializable {


    public AnchorPane bgimage;

    public JFXTextField tfDisplayname;
    public JFXComboBox<String> cbCountry;
    public JFXTextField tfPhonenumber;
    public JFXPasswordField pfPassword;
    public JFXDatePicker dpBirthdate;
    public JFXComboBox<String> cbGender;

    public JFXButton btnRegister;
    public JFXButton btnSignIn;

    public FontIcon fiDisplayName;
    public FontIcon fiCountry;
    public FontIcon fiPhoneNumber;
    public FontIcon fiPassword;
    public FontIcon fiCalendar;
    public FontIcon fiGender;
    private RegisterLoginCoordinator registerLoginCoordinator;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ModelsFactory modelsFactory = ModelsFactory.getInstance();
        CurrentUserModel currentUserModel = modelsFactory.getCurrentUserModel();

//        Country[] listCountry = createCountryList();
//        final ObservableList<Country> countries = FXCollections.observableArrayList(listCountry);
//        cbCountry.getItems().addAll(countries);
//
//       cbCountry.setButtonCell(new CountryButtonCell());

       registerLoginCoordinator = RegisterLoginCoordinator.getInstance();

        btnRegister.requestFocus();
        List<String> genders = Arrays.asList("male","female");
        ComboBoxUtils.fillComboBox(cbGender,genders);
        List<String> countries = Countries.getAll();
        ComboBoxUtils.fillComboBox(cbCountry,countries);
        ComboBoxUtils.makeComboSearchable(cbCountry);

//        String s="";
//        String p;
//        Optional<String> salt = HashEncoder.generateSalt(20);
//        if (salt.isPresent())
//            s= salt.get();
//        System.out.println(s);
//        Optional<String> password = HashEncoder.hashPassword("asdasda55",s);
//        if(password.isPresent());
//            p=password.get();
//        System.out.println(p);
//        boolean valid = HashEncoder.verifyPassword("asdasda55",p,s);
//        System.out.println(valid);



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

        registerLoginCoordinator.switchToLoginScreen();

    }


    public void handleSignupBtnClick(ActionEvent e) {
       // StageCoordinator stageCoordinator = StageCoordinator.getInstance();
        registerLoginCoordinator.switchToLoginScreen();
        //TODO add db validation then mv to signin
    }



}


