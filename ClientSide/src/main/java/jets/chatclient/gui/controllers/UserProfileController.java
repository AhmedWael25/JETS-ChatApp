/**
 * Sample Skeleton for 'userProfile.fxml' Controller Class
 */

package jets.chatclient.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.*;

import commons.remotes.server.UserProfileServiceInt;
import commons.sharedmodels.CurrentUserDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.adapters.DTOObjAdapter;
import jets.chatclient.gui.models.CurrentUserModel;
import jets.chatclient.gui.utils.ImageEncoderDecoder;
import jets.chatclient.gui.utils.Validators;
import org.kordamp.ikonli.javafx.FontIcon;


public class UserProfileController {


    UserProfileServiceInt userProfileService;
    CurrentUserModel currentUserModel;
    ModelsFactory modelsFactory;


    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="leftVBox"
    private VBox leftVBox; // Value injected by FXMLLoader

    @FXML // fx:id="profilePic"
    private Circle profilePic; // Value injected by FXMLLoader

    @FXML // fx:id="changePicBtn"
    private JFXButton changePicBtn; // Value injected by FXMLLoader

    @FXML // fx:id="displayName"
    private JFXTextField displayName; // Value injected by FXMLLoader

    @FXML // fx:id="bioTextArea"
    private JFXTextArea bioTextArea; // Value injected by FXMLLoader

    @FXML // fx:id="rightSidePane"
    private BorderPane rightSidePane; // Value injected by FXMLLoader

    @FXML // fx:id="editProfileBtn"
    private JFXButton editProfileBtn; // Value injected by FXMLLoader

    @FXML // fx:id="saveEditsBtn"
    private JFXButton saveEditsBtn; // Value injected by FXMLLoader

    @FXML // fx:id="userDataBox"
    private VBox userDataBox; // Value injected by FXMLLoader

    @FXML // fx:id="userName"
    private JFXTextField userName; // Value injected by FXMLLoader

    @FXML // fx:id="phoneNumber"
    private JFXTextField phoneNumber; // Value injected by FXMLLoader

    @FXML // fx:id="emailAddress"
    private JFXTextField emailAddress; // Value injected by FXMLLoader

    @FXML // fx:id="birtdayPicker"
    private JFXDatePicker birtdayPicker; // Value injected by FXMLLoader

    @FXML // fx:id="countriesComboBox"
    private JFXComboBox<String> countriesComboBox; // Value injected by FXMLLoader

    @FXML
    private FontIcon nameIcon;

    @FXML
    private FontIcon phoneIcon;

    @FXML
    private FontIcon emailIcon;

    @FXML
    private FontIcon countryIcon;

    @FXML
    private FontIcon dateIcon;


    @FXML
    void activateprofileBox(ActionEvent event) {
        if (userDataBox.isDisable()) {
            userDataBox.setDisable(false);
            editProfileBtn.setText("Exit Edit");
        } else {
            userDataBox.setDisable(true);
            editProfileBtn.setText("Edit Profile");
        }

    }

    @FXML
    void saveChanges(ActionEvent event) {
        // fill the new data in userModel
        CurrentUserModel updatedUserModel = new CurrentUserModel();

        updatedUserModel.setUserName(userName.getText());
        updatedUserModel.setPhoneNumber(phoneNumber.getText());
        updatedUserModel.setEmailAddress(emailAddress.getText());
        updatedUserModel.setBirthdayDate(birtdayPicker.getValue());
        updatedUserModel.setCountry(countriesComboBox.getValue());
        updatedUserModel.setBio(bioTextArea.getText());

        // Convert userModel to DTO (use DTOObjAdapter
        CurrentUserDto updatedUserDto = DTOObjAdapter.convertObjToDto(updatedUserModel);

        // Call the service and send your data.
        try {
            // The phone number is the id of users in data base.
            userProfileService.updateUserData(updatedUserDto, currentUserModel.getPhoneNumber());

            // hard code this value for testing

            System.out.println("send Data to server.");
        } catch (RemoteException e) {
            System.out.println("Failed to send data to server.");
        }


    }

    @FXML
    void openPasswordDialog(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/PasswordDialog.fxml"));
        Parent parent = fxmlLoader.load();

        Scene scene = new Scene(parent, 350, 240);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.showAndWait();

    }

    @FXML
    void changeProfilePic(ActionEvent event) {
        try {

            FileChooser fileChooser = new FileChooser();
            File newImageFile = fileChooser.showOpenDialog(null);
            //------------------Update Image In Database ----------------------//
            //if the window is closed without choosing any file return.
            if(newImageFile == null){
                System.out.println("You have not choose any file!");
                return;
            }
            // Encode Image to Send It
            String encodedImage = ImageEncoderDecoder.getEncodedImage(newImageFile);
            // Call server service and send your photo
            boolean updateState = userProfileService.updateProfilePic(encodedImage, currentUserModel.getPhoneNumber());
            //check the returned boolean and update your user model depends on it.
            if (updateState) {
                System.out.println("Photo updated Successfully.");
                setProfilePic(newImageFile.getCanonicalPath());
            }
        } catch (RemoteException remEx) {
            System.out.println(remEx.getMessage());
            System.out.println("Faild to Update Image in database");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Please choose a valid image file");
        }

    }


    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
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


        userDataBox.setDisable(true);

        //----------------- load the default pic -------------------//
        URL imageUrl = this.getClass().getResource("/images/userDefaultImage.png");
        setProfilePic(imageUrl.getPath());


        //----------------- Populate ComboBox with Countries -------------------//
        countriesComboBox.setItems(populateWithCountries());

        bindNodes(currentUserModel);

        validateFields();


    }

    private void setProfilePic(String imagePath) {
        //ToDO: Bind With profile
        Image im = new Image("file:" + imagePath, false);

        profilePic.setFill(new ImagePattern(im));
    }


    public ObservableList<String> populateWithCountries() {

        String[] locales = Locale.getISOCountries();
        List<String> countryNameList = new ArrayList<String>();


        for (String countryCode : locales) {

            Locale obj = new Locale("", countryCode);
            countryNameList.add(obj.getDisplayCountry());
        }
        return FXCollections.observableList(countryNameList);

    }

    void bindNodes(CurrentUserModel currentUserModel) {

        //--------------------------Start binding----------------------------//
        displayName.textProperty().bindBidirectional(currentUserModel.userNameProperty());
        bioTextArea.textProperty().bindBidirectional(currentUserModel.bioProperty());
        userName.textProperty().bindBidirectional(currentUserModel.userNameProperty());
        phoneNumber.textProperty().bindBidirectional(currentUserModel.phoneNumberProperty());
        emailAddress.textProperty().bindBidirectional(currentUserModel.emailAddressProperty());
        countriesComboBox.valueProperty().bindBidirectional(currentUserModel.countryProperty());
        birtdayPicker.valueProperty().bindBidirectional(currentUserModel.birthdayDateProperty());


    }

    void validateFields() {
        Validators.addNameValidator(userName, nameIcon);
        Validators.addPhoneNumberValidator(phoneNumber, phoneIcon);
        Validators.addRequiredValidator(countriesComboBox, countryIcon);
        Validators.addRequiredValidator(birtdayPicker, dateIcon);
    }

}
