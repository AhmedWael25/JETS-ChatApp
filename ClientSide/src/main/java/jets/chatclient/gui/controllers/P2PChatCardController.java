package jets.chatclient.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import jets.chatclient.gui.helpers.GpChatsManager;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.P2PChatManager;
import jets.chatclient.gui.models.P2PChatModel;

import java.io.IOException;

public class P2PChatCardController {

    @FXML
    private Circle friendImg;
    @FXML
    private Circle friendStatus;
    @FXML
    private Label friendName;
    @FXML
    public AnchorPane chatCardContainer;


    public P2PChatCardController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/P2PChatCard.fxml"));
        fxmlLoader.setController(this);

        try {
            chatCardContainer = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setData(P2PChatModel chat){

        friendName.setText(chat.getFriendName());
        P2PChatManager p2PChatManager = ModelsFactory.getInstance().getP2PChatManager();

        Circle c1 = p2PChatManager.getFriendImg(chat.getChatId());
        friendImg.fillProperty().bind(c1.fillProperty());

        Circle c2 = p2PChatManager.getFriendStatus(chat.getChatId());
        friendStatus.fillProperty().bind(c2.fillProperty());

    }

    public AnchorPane getChatCard(){
        return chatCardContainer;
    }

}

