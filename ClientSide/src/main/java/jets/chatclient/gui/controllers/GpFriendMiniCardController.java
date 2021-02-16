package jets.chatclient.gui.controllers;

import com.jfoenix.controls.JFXButton;
import commons.remotes.server.AddFriendServiceInt;
import commons.remotes.server.InvitationServiceInt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import jets.chatclient.gui.helpers.ControllersGetter;
import jets.chatclient.gui.helpers.ServicesFactory;
import jets.chatclient.gui.helpers.adapters.DTOObjAdapter;
import jets.chatclient.gui.models.FriendModel;
import jets.chatclient.gui.models.Invitation;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Base64;
import java.util.ResourceBundle;


public class GpFriendMiniCardController  {
    @FXML
    private Circle friendImg;
    @FXML
    private Label friendName;
    @FXML
    private AnchorPane gpFriendMiniCardContainer;

    FriendModel friend = null;

    public GpFriendMiniCardController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/GpFriendMiniCardView.fxml"));
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setData(FriendModel friend){
        this.friend = friend;
        friendName.setText(friend.getFriendName());
        friendImg.setFill(new ImagePattern(friend.getFriendImg()));
    }

    public AnchorPane getCard(){
        return  gpFriendMiniCardContainer;
    }
}



