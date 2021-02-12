/**
 * Sample Skeleton for 'userProfile.fxml' Controller Class
 */

package jets.chatclient.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

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


public class userProfileController {


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

    @FXML // fx:id="firstName"
    private JFXTextField firstName; // Value injected by FXMLLoader

    @FXML // fx:id="phoneNumber"
    private JFXTextField phoneNumber; // Value injected by FXMLLoader

    @FXML // fx:id="emailAddress"
    private JFXTextField emailAddress; // Value injected by FXMLLoader

    @FXML // fx:id="birtdayPicker"
    private JFXDatePicker birtdayPicker; // Value injected by FXMLLoader

    @FXML // fx:id="countriesComboBox"
    private JFXComboBox<String> countriesComboBox; // Value injected by FXMLLoader



    @FXML
    void activateprofileBox(ActionEvent event) {
        if(userDataBox.isDisable()){
            userDataBox.setDisable(false);
            editProfileBtn.setText("Exit Edit");
        }else{
            userDataBox.setDisable(true);
            editProfileBtn.setText("Edit Profile");
        }

    }

    @FXML
    void saveChanges(ActionEvent event) {

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
    void changeProfilePic(ActionEvent event) throws IOException {
        try {

            FileChooser fileChooser = new FileChooser();
            File newImageFile = fileChooser.showOpenDialog(null);
            setProfilePic(newImageFile.getCanonicalPath());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Please choose an file");
        }
    }





    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        userDataBox.setDisable(true);
       //----------------- load the default pic -------------------//
        URL imageUrl = this.getClass().getResource("/images/userDefaultImage.png");
        setProfilePic(imageUrl.getPath());


        //----------------- Populate ComboBox with Countries -------------------//
        countriesComboBox.setItems(populateWithCountries());


    }

    private void setProfilePic(String imagePath) {
        Image im = new Image("file:"+ imagePath,false);
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

}
