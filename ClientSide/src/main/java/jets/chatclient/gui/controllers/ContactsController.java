package jets.chatclient.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import commons.remotes.server.AddFriendServiceInt;
import commons.remotes.server.InvitationServiceInt;
import commons.sharedmodels.InvitationDto;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.ServicesFactory;
import jets.chatclient.gui.helpers.adapters.DTOObjAdapter;
import jets.chatclient.gui.models.Invitation;
import jets.chatclient.gui.models.guimodels.InvitationViewCell;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;



public class ContactsController implements Initializable {

    public Button dummybtn;
    @FXML
    public AnchorPane contactsScreenContainer;
    @FXML
    private JFXButton sendInvitationBtn;

    @FXML
    private JFXTextField phoneTxtField;

    @FXML
    private ListView<Invitation> invitationsListView;



    private InvitationServiceInt invitationService;
    private AddFriendServiceInt addFriendService;
    private ModelsFactory modelsFactory = ModelsFactory.getInstance();
    private ObservableList<Invitation> invitations = FXCollections.observableArrayList();

    //TODO Remove When  Current User Mode Is Ready ===
//    private  String userIdDummy = "7";
    private  String userIdDummy = "1";
    private  String userNameDummy = "sayed2";
//    private  String userNameDummy = "MySC";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Test Chat View initialized");

        //Initialize Services
        //Init the Invitation And Friend Adding Services
        try {

            ServicesFactory servicesFactory = ServicesFactory.getInstance();
            invitationService = (InvitationServiceInt) servicesFactory.getInvitationService();
            addFriendService = (AddFriendServiceInt) servicesFactory.getAddFriendService();

            new Thread(fetchUserInvitations).start();

        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
//=======================================================================================



    }

    @FXML
    void sendInvitation(ActionEvent event) {

        new Thread(sendInvitation).start();
    }


    public void addInvitationToList(InvitationDto invitationDto){
        new Thread(()->{
            Invitation inv = new Invitation();
            inv = DTOObjAdapter.convertDtoToObj(invitationDto);
            Invitation finalInv = inv;
            Platform.runLater(() -> {
                invitations.addAll(finalInv);
                invitationsListView.setItems(invitations);
//                invitationsListView.setCellFactory(param  -> new InvitationViewCell());
            });
        }).start();
    }

    public void deleteInvite(Invitation invitation){
        invitations.remove(invitation);
        invitationsListView.setItems(invitations);
    }



    //================== RUNNABLES ===================
    Runnable addInvitationToList = () -> {

    };


    Runnable sendInvitation = () -> {
        //TODO Phone Validation ---Validation Imp.
        boolean isSent;
        if(!phoneTxtField.getText().equals("") && !phoneTxtField.getText().equals(userIdDummy))  {

            phoneTxtField.setDisable(true);
            Invitation inv = new Invitation();
            inv.setSenderId(userIdDummy);
            inv.setSenderName(userNameDummy);
            inv.setReceiverId(phoneTxtField.getText());
            inv.setInvitationContent("You Got a new Friend Request !");
            try {
               isSent =  invitationService.sendInvitation(DTOObjAdapter.convertObjToDto(inv));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            phoneTxtField.setDisable(false);
        }else {

        }
    };


    Runnable fetchUserInvitations = () -> {

        List<Invitation> myInvitations = null;
        try {
            //TODO Should be Changed to Current User model ID(PHONE)
            myInvitations = DTOObjAdapter.convertDtoInvitationList(invitationService.getAllUserInvitations(userIdDummy));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        for (Invitation fd : invitations){
            System.out.println("FROM OBX"+fd);
        }

        List<Invitation> finalMyInvitations = myInvitations;
        Platform.runLater(() ->{
            invitations.addAll(finalMyInvitations);
            invitationsListView.setItems(invitations);
            invitationsListView.setCellFactory(param  -> new InvitationViewCell());
        });
    };


    public void dumdum(ActionEvent actionEvent){


    }

//    private void initNotificationPane(){
//        friendAddedNotificationPane = new NotificationPane();
//        friendAddedNotificationPane.setText("Test ya m3alem");
//        FontIcon f = new FontIcon("far-bell");
//        friendAddedNotificationPane.setGraphic(f);
//        friendAddedNotificationPane.setCloseButtonVisible(true);
//        friendAddedNotificationPane.setShowFromTop(true);
//        friendAddedNotificationPane.getStyleClass().add(NotificationPane.STYLE_CLASS_DARK);
//        friendAddedNotificationPane.getActions().addAll(new Action("Done",actionEvent -> {
//            friendAddedNotificationPane.hide();
//        }));
//        friendAddedNotificationPane.setContent(contactsScreenContainer);
//    }
}
