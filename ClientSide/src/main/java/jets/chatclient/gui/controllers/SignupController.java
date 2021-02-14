package jets.chatclient.gui.controllers;


import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.RegisterLoginCoordinator;
import jets.chatclient.gui.helpers.StageCoordinator;
import jets.chatclient.gui.models.guimodels.Country;
import jets.chatclient.gui.models.guimodels.CountryButtonCell;
import jets.chatclient.gui.utils.Validators;
import jets.chatclient.gui.models.CurrentUserModel;
import org.kordamp.ikonli.javafx.FontIcon;


import java.net.URL;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class SignupController implements Initializable {


    public AnchorPane bgimage;

    public JFXTextField tfDisplayname;
    public JFXComboBox<Country> cbCountry;
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
    private RegisterLoginCoordinator registerLoginCoordinator;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ModelsFactory modelsFactory = ModelsFactory.getInstance();
        CurrentUserModel currentUserModel = modelsFactory.getCurrentUserModel();

        Country[] listCountry = createCountryList();
        final ObservableList<Country> countries = FXCollections.observableArrayList(listCountry);
        cbCountry.getItems().addAll(countries);

       cbCountry.setButtonCell(new CountryButtonCell());

       registerLoginCoordinator = RegisterLoginCoordinator.getInstance();

        btnRegister.requestFocus();
        //binding isn't finished Yet **********************************************
//        ColorBinder.BindColor(tfDisplayname,fiDisplayName);
//        ColorBinder.BindColor(tfPhonenumber,fiPhoneNumber);
//        ColorBinder.BindColor(pfPassword,fiPassword);
//        ColorBinder.BindColor(cbCountry,fiPhoneNumber);
//        //we have to bind comoboBox to phoneNumber
//        ColorBinder.BindColor(dpBirthdate,fiCalendar);
//        ColorBinder.BindColor(cbGender,fiGender);


        Validators.addNameValidator(tfDisplayname, fiDisplayName);
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







    public void handleLoginBtnClick(ActionEvent e) {

        registerLoginCoordinator.switchToLoginScreen();

    }


    public void handleSignupBtnClick(ActionEvent e) {
       // StageCoordinator stageCoordinator = StageCoordinator.getInstance();
        //TODO add db validation then mv to signin
    }



    private Country[] createCountryList() {
        String[] countryCodes = Locale.getISOCountries();
        Country[] listCountry = new Country[countryCodes.length];

        for (int i = 0; i < countryCodes.length; i++) {
            Locale locale = new Locale("", countryCodes[i]);
            String code = locale.getCountry();
            String name = locale.getDisplayCountry();

            listCountry[i] = new Country(code, name);
        }

        Arrays.sort(listCountry);

        return listCountry;
    }
}


