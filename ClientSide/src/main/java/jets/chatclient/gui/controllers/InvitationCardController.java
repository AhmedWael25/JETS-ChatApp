package jets.chatclient.gui.controllers;

import com.jfoenix.controls.JFXButton;
import commons.remotes.server.AddFriendServiceInt;
import commons.remotes.server.InvitationServiceInt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import jets.chatclient.gui.helpers.ControllersGetter;
import jets.chatclient.gui.helpers.ServicesFactory;
import jets.chatclient.gui.helpers.adapters.DTOObjAdapter;
import jets.chatclient.gui.models.Invitation;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
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

    private Invitation invitation;
    private InvitationServiceInt invitationService;
    private AddFriendServiceInt addFriendService;
    private  ControllersGetter controllersGetter;

    public InvitationCardController( ){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/InvitationCard.fxml"));
        fxmlLoader.setController(this);
        try {
            invitationCardContainer =  fxmlLoader.load();

            controllersGetter = ControllersGetter.getInstance();

            ServicesFactory servicesFactory = ServicesFactory.getInstance();
             invitationService = servicesFactory.getInvitationService();
             addFriendService = servicesFactory.getAddFriendService();
        }catch (IOException | NotBoundException e){
            e.printStackTrace();
        }

        invitationAcceptBtn.setOnMouseClicked(event -> {
            //Accept Invitation===
            //--Add Friend In my Friend List
            //--Create Chat As Well Thru CallBacks

            try {
                invitationService.deleteInvitation(DTOObjAdapter.convertObjToDto(invitation));

                //todo thread l7whda
                addFriendService.addFriend(invitation.getSenderId(),invitation.getReceiverId());

                ContactsController ctrl = controllersGetter.getContactsController();
                ctrl.deleteInvite(invitation);
            } catch (RemoteException  e) {
                e.printStackTrace();
            }
        });
        invitationRejectBtn.setOnMouseClicked(event -> {
            //Reject Invitation
            //--Delete Invitation From DB
            //--Remove Invitation Card
            try {
                invitationService.deleteInvitation(DTOObjAdapter.convertObjToDto(invitation));

                ContactsController ctrl = controllersGetter.getContactsController();
                ctrl.deleteInvite(invitation);
            } catch (RemoteException  e) {
                e.printStackTrace();
            }
        });
    }

    public void setData(Invitation inv){
        this.invitation = inv;
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
