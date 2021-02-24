package jets.chatclient.gui.controllers;


import com.jfoenix.controls.*;
import commons.utils.*;
import commons.remotes.server.SignUpServiceInt;
import commons.sharedmodels.CurrentUserDto;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.RegisterLoginCoordinator;
import jets.chatclient.gui.helpers.ServicesFactory;
import jets.chatclient.gui.helpers.StageCoordinator;
import jets.chatclient.gui.helpers.adapters.DTOObjAdapter;
import jets.chatclient.gui.models.User;
import jets.chatclient.gui.models.CurrentUserModel;
import org.kordamp.ikonli.javafx.FontIcon;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.Locale;
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
    public Label invalidDt;
    private RegisterLoginCoordinator registerLoginCoordinator;
    public SignUpServiceInt signUpService;

    CurrentUserModel currentUserModel;
    ModelsFactory modelsFactory;
    StageCoordinator stageCoordinator;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        modelsFactory = ModelsFactory.getInstance();
        currentUserModel = modelsFactory.getCurrentUserModel();
        Registry reg = modelsFactory.getRegistry();

        try {
            signUpService = ServicesFactory.getInstance().getSignUpService();
        } catch (RemoteException | NotBoundException e) {
            System.out.println("can't find sign up Service");
            e.printStackTrace();
        }
        tfPhonenumber.textProperty().bindBidirectional(currentUserModel.phoneNumberProperty());

        //handling date picker
        //age between 16-60
        final LocalDate minDate = LocalDate.now().minusYears(60);
        final LocalDate maxDate = LocalDate.now().minusYears(16);
        dpBirthdate.setValue(maxDate.minusYears(1));
        DatePickerUtils.restrictDatePicker(dpBirthdate, minDate, maxDate);


        registerLoginCoordinator = RegisterLoginCoordinator.getInstance();

        //handling combboxes
        btnRegister.requestFocus();
        List<String> genders = Arrays.asList("male", "female");
        ComboBoxUtils.fillComboBox(cbGender, genders);
        List<String> countries = Countries.getAll();
        ComboBoxUtils.fillComboBox(cbCountry, countries);
        ComboBoxUtils.makeComboSearchable(cbCountry);

       //  cbCountry.getItems().addAll()




        Validators.addNameValidator(tfDisplayname, fiDisplayName);
        Validators.addPhoneNumberValidator(tfPhonenumber, fiPhoneNumber);
        Validators.addPasswordValidator(pfPassword, fiPassword);
        Validators.addRequiredValidator(cbCountry, fiCountry);
        // Validators.addRequiredValidator(cbGender, fiGender);
        Validators.addRequiredValidator(dpBirthdate, fiCalendar);


    }


    public void handleLoginBtnClick(ActionEvent e) {

        registerLoginCoordinator.switchToLoginScreen();

    }


    public void handleSignupBtnClick(ActionEvent e) {
        if (!validate())
            return;
        User user;
        try {

            System.out.println(currentUserModel.getPhoneNumber());
            int UserRegStatus = signUpService.checkUserExist(currentUserModel.getPhoneNumber(), tfDisplayname.getText());
            System.out.println(UserRegStatus);
            switch (UserRegStatus) {
                case 1: //user registered
                    invalidDt.setText("Phone number already registered");
                    break;
                case 2:
                case 3: //user registration successful
                    user = getUserData();
                    CurrentUserDto currentUserDto = DTOObjAdapter.convertToUserDto(user);
                    if (signUpService.signUpUser(currentUserDto)) {
                        registerLoginCoordinator.switchToLoginScreen();
                    } else
                        System.out.println("user registration failed");
                    break;

                case 4:
                    invalidDt.setText("Username has already been taken");

            }

        } catch (RemoteException remoteException) {
            System.out.println("can't check credentials");
            remoteException.printStackTrace();
        }

    }


    public User getUserData() {
        String userDefaultImage = "";
        ImageEncoderDecoder imageEncoderDecoder = new ImageEncoderDecoder();
        try {
            String defPath = "/images/userDefaultImage.png";
            InputStream inputStream = getClass().getResourceAsStream(defPath);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            userDefaultImage = imageEncoderDecoder.getEncodedImage(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = new User();

        //handle password
        String salt = "SKgTpccoOReOUvXS/ORKuY1+mC0=";
        //used in case of making salt field in db
//      Optional<String> optionalSalt = HashEncoder.generateSalt(LocalDateTime.now().getSecond());
//      if (optionalSalt.isPresent());
//      salt=sal.get();
//
        String password = "";
        Optional<String> optionalpassword = HashEncoder.hashPassword(pfPassword.getText(), salt);
        if (optionalpassword.isPresent()) ;
        password = optionalpassword.get();

        user.setUserPhone(tfPhonenumber.getText());
        user.setUserName(tfDisplayname.getText());
        user.setUserGender(cbGender.getValue());
        //to intialize field only
        user.setUserEmail("");
        user.setUserCountry(cbCountry.getValue());
        //to intialize field only
        user.setUserBio("");
        user.setUserImage(userDefaultImage);
        user.setUserDateOfBirth(dpBirthdate.getValue().toString());
        user.setUserPassword(password);
        user.setUserAvailability(1);
        user.setUserStatus(1);

        return user;

    }
    boolean validate(){
       return tfPhonenumber.validate()&tfDisplayname.validate()&cbCountry.validate()&cbGender.validate()&pfPassword.validate();
    }
}


