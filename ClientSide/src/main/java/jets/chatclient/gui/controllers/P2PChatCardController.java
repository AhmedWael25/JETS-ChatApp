package jets.chatclient.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import jets.chatclient.gui.models.P2PChatModel;

import java.io.IOException;

public class P2PChatCardController {

    @FXML
    private Circle userImg;
    @FXML
    private Circle userStatus;
    @FXML
    private Label userName;
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
        userName.setText(chat.getSecondParticipant());
    }

    public AnchorPane getChatCard(){
        return chatCardContainer;
    }

}

