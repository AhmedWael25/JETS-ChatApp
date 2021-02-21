package jets.chatclient.gui.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import jets.chatclient.gui.helpers.GpChatsManager;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.P2PChatManager;
import jets.chatclient.gui.models.CurrentUserModel;
import jets.chatclient.gui.models.P2PMessageModel;

import java.io.IOException;
import java.util.Date;

public class P2PMsgCardController {

    @FXML
    private HBox msgcontainer;

    @FXML
    private Circle userImg;

    @FXML
    private Label msgBody;

    @FXML
    private HBox metaDataContainer;

    @FXML
    private Label msgTimestamp;

    public P2PMsgCardController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/MessageCard1.fxml"));
        fxmlLoader.setController(this);

        try {
            msgcontainer = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setData(P2PMessageModel msg){

        CurrentUserModel userModel = ModelsFactory.getInstance().getCurrentUserModel();

        if(msg.getSenderId().equals(userModel.getPhoneNumber())) {
            msgcontainer.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            userModel.bindToUserAvatar(userImg);
            msgTimestamp.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        }
        else {
            P2PChatManager manager =  ModelsFactory.getInstance().getP2PChatManager();
            Circle friendAvatar = manager.getFriendImg(msg.getChatId());
            userImg.fillProperty().bind(friendAvatar.fillProperty());
        }

        msgTimestamp.setText(msg.getMsgTime());
        msgBody.setText(msg.getMsgBody());
    }

    public HBox getMsgCard(){
        return msgcontainer;
    }

}
