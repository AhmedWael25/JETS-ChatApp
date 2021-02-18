package jets.chatclient.gui.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import jets.chatclient.gui.models.MessageModel;
import jets.chatclient.gui.models.P2PChatModel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;

public class MessageCardController {

    @FXML
    private Label msgBody;
    @FXML
    private Circle friendImg;
    @FXML
    private Label msgTime;
    @FXML
    private HBox chatBubbleContainer;

    public MessageCardController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/MessageCard.fxml"));
        fxmlLoader.setController(this);

        try {
            chatBubbleContainer = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setData(MessageModel msg){
        msgBody.setText(msg.getMsgBody());
        msgTime.setText(new Date().toString());

        byte[] decodeImg = Base64.getDecoder().decode(msg.getFriendImg());
        Image frndImg = new Image(new ByteArrayInputStream(decodeImg));
        friendImg.setFill(new ImagePattern(frndImg));

    }

    public HBox getMsgCard(){
        return chatBubbleContainer;
    }

}
