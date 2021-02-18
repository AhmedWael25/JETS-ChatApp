package jets.chatclient.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import jets.chatclient.gui.helpers.GpChatsManager;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.models.GpMessageModel;

import java.io.IOException;


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

    public void setData(GpMessageModel msg){
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
