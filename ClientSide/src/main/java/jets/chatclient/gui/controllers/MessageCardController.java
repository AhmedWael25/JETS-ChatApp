package jets.chatclient.gui.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import jets.chatclient.gui.helpers.GpChatsManager;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.P2PChatManager;
import jets.chatclient.gui.models.P2PMessageModel;

import java.io.IOException;
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

    public void setData(P2PMessageModel msg){
        msgBody.setText(msg.getMsgBody());
        msgTime.setText(msg.getMsgTime());

//        P2PChatManager p2pChatManager = ModelsFactory.getInstance().getP2PChatManager();
//        Circle partImg = p2pChatManager.getParticipantImg(msg.getChatId(), msg.getSenderId());
//        friendImg.fillProperty().bind(participantImg.fillProperty());

    }

    public HBox getMsgCard(){
        return chatBubbleContainer;
    }

}
