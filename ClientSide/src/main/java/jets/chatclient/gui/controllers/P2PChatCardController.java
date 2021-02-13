package jets.chatclient.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
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
        friendImg.setFill(new ImagePattern(chat.getFriendImg()));
    }

    public AnchorPane getChatCard(){
        return chatCardContainer;
    }

}

