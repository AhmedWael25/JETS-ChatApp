package jets.chatclient.gui.controllers;


import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.RegisterLoginCoordinator;
import jets.chatclient.gui.utils.Countries;
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
    public JFXComboBox<Label> cbGender;

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
//        fillComboBox(cbGender,genders);
        List<String> countries = Countries.getAll();
        fillComboBox(cbCountry,countries);
        searchComboBox(cbCountry);




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

    public void fillComboBox(JFXComboBox<String> comboBox , List<String> values){

//        values.stream().forEach(o -> comboBox.getItems().add(new Label(o.toString())));
        values.stream().forEach(o -> comboBox.getItems().add(o));
        comboBox.setEditable(true);
//        comboBox.setConverter(new StringConverter<Label>() {
//            @Override
//            public String toString(Label object) {
//                return object==null?"": object.getText();
//            }
//            @Override
//            public Label fromString(String string) {
//                return new Label(string);
//            }
//        });
    }

    public void searchComboBox(JFXComboBox<String> comboBox){
        JFXAutoCompletePopup<String> autoCompletePopup = new JFXAutoCompletePopup<>();
        autoCompletePopup.getSuggestions().addAll(comboBox.getItems());
        autoCompletePopup.hide();

//SelectionHandler sets the value of the comboBox
        autoCompletePopup.setSelectionHandler(event -> {
            comboBox.setValue(event.getObject());
        });

        TextField editor = comboBox.getEditor();
        editor.textProperty().addListener(observable -> {
            //The filter method uses the Predicate to filter the Suggestions defined above
            //I choose to use the contains method while ignoring cases
            autoCompletePopup.filter(item -> item.toLowerCase().startsWith(editor.getText().toLowerCase()));
            //Hide the autocomplete popup if the filtered suggestions is empty or when the box's original popup is open
            if (autoCompletePopup.getFilteredSuggestions().isEmpty() || comboBox.showingProperty().get()) {
                autoCompletePopup.hide();
            }
            else {
                autoCompletePopup.show(editor);
            }
        });
    }



//||||||| e62eee9
//=======
//
//
//    private Country[] createCountryList() {
//        String[] countryCodes = Locale.getISOCountries();
//        Country[] listCountry = new Country[countryCodes.length];
//
//        for (int i = 0; i < countryCodes.length; i++) {
//            Locale locale = new Locale("", countryCodes[i]);
//            String code = locale.getCountry();
//            String name = locale.getDisplayCountry();
//
//            listCountry[i] = new Country(code, name);
//        }
//
//        Arrays.sort(listCountry);
//
//        return listCountry;
//    }
//>>>>>>> signup-updates
}


