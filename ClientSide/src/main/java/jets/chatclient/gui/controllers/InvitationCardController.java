package jets.chatclient.gui.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import jets.chatclient.gui.models.Invitation;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

public class InvitationCardController {

    @FXML
    public AnchorPane invitationCardContainer;
    @FXML
    private Circle invitationSenderImg;
    @FXML
    private Label invitationSenderName;
    @FXML
    private Label invitationContent;
    @FXML
    private JFXButton invitationAcceptBtn;
    @FXML
    private JFXButton invitationRejectBtn;



    public InvitationCardController( ){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/InvitationCard.fxml"));
        fxmlLoader.setController(this);
        try {
            invitationCardContainer =  fxmlLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }


        invitationAcceptBtn.setOnMouseClicked(event -> {
            System.out.println("Accepted");
        });
        invitationRejectBtn.setOnMouseClicked(event -> {
            System.out.println("Rejected");
        });
    }

    public void setData(Invitation inv){
        invitationContent.setText(inv.getInvitationContent());
        invitationSenderName.setText(inv.getSenderName());

        byte[] dst = Base64.getDecoder().decode(inv.getSenderImg());
        Image img = new Image(new ByteArrayInputStream(dst));
        invitationSenderImg.setFill(new ImagePattern(img));
    }

    public AnchorPane getCard(){
        return  invitationCardContainer;
    }


}
