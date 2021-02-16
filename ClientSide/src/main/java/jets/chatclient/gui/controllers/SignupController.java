package jets.chatclient.gui.controllers;


import com.jfoenix.controls.*;
import commons.utils.ImageEncoderDecoder;
import commons.remotes.server.SignUpServiceInt;
import commons.sharedmodels.CurrentUserDto;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.RegisterLoginCoordinator;
import jets.chatclient.gui.helpers.StageCoordinator;
import jets.chatclient.gui.helpers.adapters.DTOObjAdapter;
import jets.chatclient.gui.models.User;
import jets.chatclient.gui.utils.Countries;
import jets.chatclient.gui.utils.Validators;
import jets.chatclient.gui.models.CurrentUserModel;
import org.kordamp.ikonli.javafx.FontIcon;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
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
            signUpService = (SignUpServiceInt) reg.lookup("SignUpService");
        } catch (RemoteException | NotBoundException e) {
            System.out.println("can't find sign up Service");
            e.printStackTrace();
        }
        tfPhonenumber.textProperty().bindBidirectional(currentUserModel.phoneNumberProperty());



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
       // Validators.addRequiredValidator(cbGender, fiGender);
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
        User user ;
        try {

            System.out.println(currentUserModel.getPhoneNumber());
            int UserRegStatus = signUpService.checkUserExist(currentUserModel.getPhoneNumber(),tfDisplayname.getText());
            System.out.println(UserRegStatus);
            switch (UserRegStatus) {
                case 1: //user registered // redirect to password
               System.out.println("User already registered");
                    break;
                case 2: //user not registered
                    user = getUserData();
                    CurrentUserDto currentUserDto = DTOObjAdapter.convertToUserDto(user);
                    if(signUpService.signUpUser(currentUserDto))
                        System.out.println("user Registered Successfully");
                    else System.out.println("user registration failed");
                    break;

                case 3://user registered by admin(no data saved for user)
                    user = getUserData();
                    currentUserDto = DTOObjAdapter.convertToUserDto(user);
                    if(signUpService.signUpUser(currentUserDto))
                    System.out.println("user updated Successfully");
                    else System.out.println("user registration failed");
                    break;

                case 4:
                    System.out.println("username already exist");
            }

        } catch (RemoteException remoteException) {
            System.out.println("can't check credentials");
            remoteException.printStackTrace();
        }
        registerLoginCoordinator.switchToLoginScreen();
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



  public User getUserData (){
      String userDefaultImage ="";
      ImageEncoderDecoder imageEncoderDecoder = new ImageEncoderDecoder();
      try {
          File f = new File(getClass().getResource("/images/userDefaultImage.png").getPath());
         userDefaultImage = imageEncoderDecoder.getEncodedImage(f);
      } catch (IOException e) {
          e.printStackTrace();
      }
      User user = new User();
        user.setUserPhone(tfPhonenumber.getText());
        user.setUserName(tfDisplayname.getText());
        user.setUserCountry(cbCountry.getValue());
        user.setUserGender("female");
        user.setUserImage(userDefaultImage);

        user.setUserDateOfBirth(dpBirthdate.getValue().toString());
        user.setUserPassword(pfPassword.getText());
        user.setUserAvailability(1);
        user.setUserStatus(1);

      return user;

    }
}


