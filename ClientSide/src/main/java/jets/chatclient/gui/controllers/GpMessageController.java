package jets.chatclient.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.mysql.cj.protocol.Message;
import commons.remotes.server.AddFriendServiceInt;
import commons.remotes.server.InvitationServiceInt;
import commons.sharedmodels.MessageDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import jets.chatclient.gui.helpers.ControllersGetter;
import jets.chatclient.gui.helpers.GpChatsManager;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.ServicesFactory;
import jets.chatclient.gui.helpers.adapters.DTOObjAdapter;
import jets.chatclient.gui.models.FriendModel;
import jets.chatclient.gui.models.Invitation;
import jets.chatclient.gui.models.MessageModel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Base64;
import java.util.ResourceBundle;



public class GpMessageController {

    @FXML
    private HBox msgcontainer;

    @FXML
    private Circle userImg;

    @FXML
    private Label msgBody;

    @FXML
    private Label msgSenderName;

    @FXML
    private Label msgTimeStamp;

    public  GpMessageController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/GpMsgCard.fxml"));
        fxmlLoader.setController(this);

        try {
            msgcontainer = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setData(MessageModel msg){
        //TODO If I Am the user Bind on ME
        GpChatsManager gpChatsManager = ModelsFactory.getInstance().getGpChatsManager();
        Circle participantImg = gpChatsManager.getParticipantImg(msg.getChatId(), msg.getSenderId());
        userImg.fillProperty().bind(participantImg.fillProperty());

        msgBody.setText(msg.getMsgContent());
        msgSenderName.setText(msg.getSenderName());
        msgTimeStamp.setText(", @ "+msg.getTimeStamp());
    }

    public HBox getMsgCard(){
        return msgcontainer;
    }

}
