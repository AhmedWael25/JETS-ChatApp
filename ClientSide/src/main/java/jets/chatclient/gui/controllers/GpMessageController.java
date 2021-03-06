package jets.chatclient.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import jets.chatclient.gui.helpers.GpChatsManager;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.models.CurrentUserModel;
import jets.chatclient.gui.models.GpMessageModel;

import java.io.IOException;


public class GpMessageController {

    @FXML
    private HBox msgcontainer;
    @FXML
    private Circle userStatus;
    @FXML
    private Circle userImg;

    @FXML
    private Label msgBody;

    @FXML
    private HBox metaDataContainer;

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

    public void setData(GpMessageModel msg){
        //TODO If I Am the user Bind on ME

        CurrentUserModel userModel = ModelsFactory.getInstance().getCurrentUserModel();

        if(msg.getSenderId().equals(userModel.getPhoneNumber())){
            msgcontainer.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            msgBody.setStyle("-fx-background-color: #304269; -fx-border-radius:  0 10 10 10 ; -fx-background-radius: 0 10 10 10;");
            msgBody.setTextFill(Color.WHITE);
            userModel.bindToUserAvatar(userImg);
            userModel.bindToUserStatus(userStatus);
            metaDataContainer.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        }else {
            GpChatsManager gpChatsManager = ModelsFactory.getInstance().getGpChatsManager();
            Circle participantImg = gpChatsManager.getParticipantImg(msg.getChatId(), msg.getSenderId());
            userImg.fillProperty().bind(participantImg.fillProperty());
            Circle participantStatus = gpChatsManager.getParticipantStatus(msg.getChatId(),msg.getSenderId());
            userStatus.fillProperty().bind(participantStatus.fillProperty());
        }

        msgBody.setText(msg.getMsgContent());
        msgSenderName.setText(msg.getSenderName());
        msgTimeStamp.setText(", @ "+msg.getTimeStamp());
    }

    public HBox getMsgCard(){
        return msgcontainer;
    }

}
