package jets.chatclient.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import jets.chatclient.gui.models.GpChatModel;
import jets.chatclient.gui.models.P2PChatModel;

import java.io.IOException;


public class GpChatCardController {


    @FXML
    private AnchorPane gpChatCardContainer;

    @FXML
    private Circle gpChatImg;

    @FXML
    private Label gpChatName;

    public  GpChatCardController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/GpChatCard.fxml"));
        fxmlLoader.setController(this);

        try {
            gpChatCardContainer = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setData(GpChatModel chat){
        gpChatName.setText(chat.getGpChatName());
        gpChatImg.setFill(new ImagePattern(chat.getgChatImage()));
    }

    public AnchorPane getChatCard(){
        return gpChatCardContainer;
    }


}
