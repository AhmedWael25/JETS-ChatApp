package jets.chatclient.gui.controllers;


import commons.remotes.server.AddFriendServiceInt;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.ServicesFactory;
import jets.chatclient.gui.helpers.StageCoordinator;
import jets.chatclient.gui.helpers.adapters.DTOObjAdapter;
import jets.chatclient.gui.models.CurrentUserModel;

import java.io.File;
import java.io.LineNumberInputStream;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import jets.chatclient.gui.models.FriendModel;
import jets.chatclient.gui.models.guimodels.GpFrndViewCell;
import org.controlsfx.control.ListSelectionView;

public class GpChatCreationController implements  Initializable {

    @FXML
    private Circle gpChatImg;
    @FXML
    private JFXButton gpImgChangeBtn;
    @FXML
    private JFXTextField gpNameField;
    @FXML
    private ListSelectionView<FriendModel> friendsListView;
    @FXML
    private Label errorLabel;
    @FXML
    private JFXButton createGpChatBtn;

    private AddFriendServiceInt friendService;
    List<FriendModel> userFriends;
    List<FriendModel> groupTargetFriends;
    List<FriendModel> groupSourceFriends;
    private String errorMsg = "";
    private File imgFile;


    private String userDummyId = "1";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        imgFile = new File(getClass().getResource("/images/def-gpImg.jpg").getPath());
        gpChatImg.setFill(new ImagePattern(new Image(imgFile.toURI().toString())));

        new Thread(fetchFriends).start();

    }


    @FXML
    void createGpChat(ActionEvent event) {
        boolean isValid = validateAllFields();
        if(!isValid) {
            System.out.println("not valid");
            errorMsg = "";
        }else {
            errorLabel.setText("");
        }
        //TODO If Valid Creat Chat Through Services
    }

    @FXML
    void gpImgChange(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("images", "*.png", "*.jpg" ,"*.jpeg "));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        imgFile = fileChooser.showOpenDialog(null);

        if(imgFile == null) return;

        if(imgFile.length() > 1000000){
            //TODO File Is To Large.. Only allowed 1 MB Images
            showErrorMsg("File Is Too Large, Maximum Size Allowed is 1MB.");
            return;
        }
        errorLabel.setText("");
        gpChatImg.setFill(new ImagePattern(new Image(imgFile.toURI().toString())));

        setGpNameMax(gpNameField, 15);
    }


    private boolean validateAllFields() {


        boolean gpNameFieldValidation = validateGpNameField();
        boolean minGpUsersValidation = validateMinGpUsers();
        System.out.println(gpNameFieldValidation +""+ minGpUsersValidation);
        System.out.println(errorMsg);
        String errorMsg = "";

        if(!gpNameFieldValidation){
            errorMsg += "Group Name Is Empty ,";
            System.out.println(errorMsg);
        }

        if(!minGpUsersValidation){
            errorMsg +="You Need 2 Friends To Start a Group Chat ,";
            System.out.println(errorMsg);
        }
        System.out.println(errorMsg);
        //If ANy of Them is false, User as to Check What's Wrong
        if(!gpNameFieldValidation || !minGpUsersValidation){
            errorMsg +="Please Fix Them And Re-Submit";
            showErrorMsg(errorMsg);
            System.out.println(errorMsg);
            return false;
        }
        return true;
    }
    private boolean validateGpNameField(){

        if(gpNameField.getText().equals("")){
            return  false;
        }
        return true;
    }
    private boolean validateMinGpUsers(){
        if(groupTargetFriends.size() < 2){
            return  false;
        }
        return  true;
    }
    private void showErrorMsg(String str){
        System.out.println("STRS"+ str);
        errorLabel.setText("*"+str);
    }
    private void setGpNameMax(JFXTextField jfxTextField, int LIMIT){
        jfxTextField.lengthProperty().addListener((observable, oldValue, newValue) -> {
            // Check if the new character is greater than LIMIT
            if (jfxTextField.getText().length() >= LIMIT) {

            // if it's 11th character then just setText to previous
            // one
                jfxTextField.setText(jfxTextField.getText().substring(0, LIMIT));
                }
        });
    }


    //    ================== RUNNABLES =================
    Runnable fetchFriends = () -> {

        try {
            ServicesFactory servicesFactory = ServicesFactory.getInstance();
            friendService = servicesFactory.getAddFriendService();
            userFriends = DTOObjAdapter.convertDtoGpFriendList(friendService.fetchAllFriendsByUserId(userDummyId));

            Platform.runLater(() -> {
                friendsListView.getSourceItems().addAll(userFriends);
                friendsListView.setCellFactory(param -> new GpFrndViewCell());
                groupTargetFriends = friendsListView.getTargetItems();
            });

        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    };

}