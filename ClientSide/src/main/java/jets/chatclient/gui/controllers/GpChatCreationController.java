package jets.chatclient.gui.controllers;


import commons.remotes.server.AddFriendServiceInt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.StageCoordinator;
import jets.chatclient.gui.models.CurrentUserModel;

import java.io.LineNumberInputStream;
import java.net.URL;
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

public class GpChatCreationController implements  Initializable{

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

    private String userDummyId = "1";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        friendsListView.setCellFactory(param -> new GpFrndViewCell());

        FriendModel f1 = new FriendModel();
        f1.setFriendName("sayed");
        FriendModel f2 = new FriendModel();
        f2.setFriendName("sayed2");
        FriendModel f3 = new FriendModel();
        f3.setFriendName("sayed3");
        FriendModel f4 = new FriendModel();
        f4.setFriendName("Wael4");

        friendsListView.getSourceItems().addAll(f1,f2,f3);
        friendsListView.getTargetItems().addAll(f4);

    }


    @FXML
    void createGpChat(ActionEvent event) {

    }

    @FXML
    void gpImgChange(ActionEvent event) {

    }


    private boolean validateAllFields(){

        return false;
    }

}
