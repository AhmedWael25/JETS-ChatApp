package jets.chatclient.gui.controllers;


import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXDialog;
import commons.remotes.server.AddFriendServiceInt;
import commons.remotes.server.GpChatServiceInt;
import commons.sharedmodels.GpChatDto;
import commons.sharedmodels.GpChatUserDto;
import commons.utils.ImageEncoderDecoder;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;
import jets.chatclient.gui.helpers.ControllersGetter;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.ServicesFactory;
import jets.chatclient.gui.helpers.StageCoordinator;
import jets.chatclient.gui.helpers.adapters.DTOObjAdapter;
import jets.chatclient.gui.models.CurrentUserModel;

import java.io.File;
import java.io.IOException;
import java.io.LineNumberInputStream;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
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

    public AnchorPane gpChatCreationContainer;
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
    private GpChatServiceInt gpChatService;
    List<FriendModel> userFriends;
    List<FriendModel> groupTargetFriends;
    List<FriendModel> groupSourceFriends;
    private String errorMsg = "";
    private File imgFile;
    private File defImg ;
    private String encodedImg;

    private CurrentUserModel userModel ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userModel = ModelsFactory.getInstance().getCurrentUserModel();

        defImg = new File(getClass().getResource("/images/van.jpg").getPath());
        imgFile = defImg;
        gpChatImg.setFill(new ImagePattern(new Image(imgFile.toURI().toString())));

        new Thread(fetchFriends).start();

        setGpNameMax(gpNameField, 15);
    }


    @FXML
    void createGpChat(ActionEvent event) {
        boolean isValid = validateAllFields();
        if(!isValid) {
            errorMsg = "";
        }else {
            errorLabel.setText("");
            try {

                GpChatUserDto dto = collectDataIntoDto();
                ServicesFactory servicesFactory = ServicesFactory.getInstance();
                gpChatService  = servicesFactory.getGpChatService();
                gpChatService.createGroupChat(dto);

                gpNameField.clear();
                gpChatImg.setFill(new ImagePattern(new Image(defImg.toURI().toString())));
                friendsListView.getSourceItems().addAll(userFriends);
                friendsListView.getTargetItems().clear();

            } catch (IOException | NotBoundException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void gpImgChange(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("images", "*.png", "*.jpg" ,"*.jpeg "));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        imgFile = fileChooser.showOpenDialog(null);

        if(imgFile == null) {
            imgFile = defImg;
        }

        if(imgFile.length() > 1000000){
            //TODO File Is To Large.. Only allowed 1 MB Images
            showErrorMsg("File Is Too Large, Maximum Size Allowed is 1MB.");
            return;
        }
        errorLabel.setText("");
        gpChatImg.setFill(new ImagePattern(new Image(imgFile.toURI().toString())));

    }

    private boolean validateAllFields() {


        boolean gpNameFieldValidation = validateGpNameField();
        boolean minGpUsersValidation = validateMinGpUsers();
        String errorMsg = "";

        if(!gpNameFieldValidation){
            errorMsg += "Group Name Is Empty ,";
        }

        if(!minGpUsersValidation){
            errorMsg +="You Need 2 Friends To Start a Group Chat ,";
        }
        //If ANy of Them is false, User as to Check What's Wrong
        if(!gpNameFieldValidation || !minGpUsersValidation){
            errorMsg +="Please Fix Them And Re-Submit";
            showErrorMsg(errorMsg);
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
        errorLabel.setText("*"+str);
    }
    private void setGpNameMax(JFXTextField jfxTextField, int LIMIT){
        jfxTextField.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if (jfxTextField.getText().length() >= LIMIT) {
                    jfxTextField.setText(jfxTextField.getText().substring(0, LIMIT));
            }
        });
    }

    private GpChatUserDto collectDataIntoDto() throws IOException {
        GpChatUserDto gpUserDto = new GpChatUserDto();

        gpUserDto.setChatName(gpNameField.getText());
        String str = null;
        if (imgFile == null){
             str = ImageEncoderDecoder.getEncodedImage(defImg);
        }else {
            str = ImageEncoderDecoder.getEncodedImage(imgFile);
        }
        gpUserDto.setChatImage(str);
        gpUserDto.setAdminId(userModel.getPhoneNumber() );

        List<String> userIds = new ArrayList<>();
        userIds.add(userModel.getPhoneNumber());
        for(FriendModel user : groupTargetFriends){
            userIds.add(user.getFriendId());
        }
        gpUserDto.setGpUserIds(userIds);
        return  gpUserDto;
    }

    //    ================== RUNNABLES =================
    Runnable fetchFriends = () -> {

        try {
            ServicesFactory servicesFactory = ServicesFactory.getInstance();
            friendService = servicesFactory.getAddFriendService();
            //TODO Refactor into user obj model
            userFriends = DTOObjAdapter.convertDtoGpFriendList(friendService.fetchAllFriendsByUserId(userModel.getPhoneNumber()));

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