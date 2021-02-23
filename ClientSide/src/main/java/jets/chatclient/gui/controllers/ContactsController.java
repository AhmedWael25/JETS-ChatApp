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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import jets.chatclient.gui.helpers.ContactsManager;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.ServicesFactory;
import jets.chatclient.gui.helpers.adapters.DTOObjAdapter;
import jets.chatclient.gui.models.ContactModel;
import jets.chatclient.gui.models.CurrentUserModel;
import jets.chatclient.gui.models.Invitation;
import jets.chatclient.gui.models.guimodels.ContactViewCell;
import jets.chatclient.gui.models.guimodels.InvitationViewCell;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;



public class ContactsController implements Initializable {

    public Button dummybtn;
    @FXML
    public AnchorPane contactsScreenContainer;
    public Label feedbackLabel;
    public ListView<ContactModel> contactsListView;
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
    private ObservableList<ContactModel> contacts = FXCollections.observableArrayList();

    private CurrentUserModel userModel ;
    private ContactsManager contactsManager;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        userModel = ModelsFactory.getInstance().getCurrentUserModel();
        contactsManager = ModelsFactory.getInstance().getContactsManager();
        System.out.println("Test Chat View initialized");

        //Initialize Services
        //Init the Invitation And Friend Adding Services
        try {

            ServicesFactory servicesFactory = ServicesFactory.getInstance();
            invitationService =  servicesFactory.getInvitationService();
            addFriendService = servicesFactory.getAddFriendService();

            new Thread(fetchUserInvitations).start();
            new Thread(fetchAlluserContacts).start();

        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
//=======================================================================================
        phoneTxtField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            feedbackLabel.setText("");
        });

    }

    @FXML
    void sendInvitation(ActionEvent event) {
        new Thread(sendInvitation).start();

    }
    public void addContactToList(ContactModel model){
        Platform.runLater(() ->{
            contacts.add(model);
            contactsListView.setItems(contacts);
        });
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

    public void deleteInv(Invitation tobeDelInv){
        Iterator<Invitation> i = invitations.iterator();
        while(i.hasNext()){
            Invitation inv = i.next();
            if(inv.getSenderId().equals(tobeDelInv.getReceiverId())){
                Platform.runLater(() ->{
                    i.remove();
                    invitationsListView.setItems(invitations);
                });
                return;
            }
        }

    }



    //================== RUNNABLES ===================
    Runnable fetchAlluserContacts = () -> {
        try {

            List<ContactModel> contactsList = DTOObjAdapter.convertDtoContactsList(addFriendService.fetchAllContactsByUserId(userModel.getPhoneNumber()));
            contactsManager.addContacts(contactsList);
            System.out.println(contactsList);

            Platform.runLater(() -> {
                contacts.addAll(contactsList);
                contactsListView.setItems(contacts);
                contactsListView.setCellFactory( param -> new ContactViewCell());
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    };


    Runnable sendInvitation = () -> {
        //TODO Phone Validation --- Validation Imp.
        boolean isSent;
        if(phoneTxtField.getText().equals("")  )  {
            displayFailFeedBackMsg("You Haven't Entered an Id");
        }else if(phoneTxtField.getText().equals(userModel.getPhoneNumber())) {
            displayFailFeedBackMsg("You Cannot Add Your Self!");
        }
        else {
            phoneTxtField.setDisable(true);
            sendInvitationBtn.setDisable(true);
            Invitation inv = new Invitation();
            String receiverId = phoneTxtField.getText();
            inv.setSenderId(userModel.getPhoneNumber());
            inv.setSenderName(userModel.getDisplayName());
            inv.setReceiverId(receiverId);
            inv.setInvitationContent("You Got a new Friend Request !");
            InvitationDto invDto = DTOObjAdapter.convertObjToDto(inv);
            try {

                if(invitationService.isInviteExists(invDto)){
                    displayFailFeedBackMsg("You Already Sent an Invitation to Them");
                } else if (addFriendService.areFriends(invDto.getSenderId(),invDto.getReceiverId())) {
                    displayFailFeedBackMsg("You Already Are Friends With Them");
                }
                else if(!invitationService.isUserExist(receiverId)){
                    displayFailFeedBackMsg("This User Doesn't Exist");
                }
                 else if(invitationService.isAlreadyInvited(invDto)){
                     //If User has already invite Me, Add Users to friend list
                    //Delete Invite In List Now
                    System.out.println("User"+ inv.getReceiverId() + "Has Alrdy invited u");
                    addFriendService.addFriend(invDto.getSenderId(),invDto.getReceiverId());
                    invitationService.deleteInvitation(invDto.getReceiverId(),invDto.getSenderId());
                    displaySucessFeedBackMsg("Friend Added !");
                    deleteInv(inv);
                }
                else {
                    isSent =  invitationService.sendInvitation(DTOObjAdapter.convertObjToDto(inv));
                    displaySucessFeedBackMsg("Invitation Sent!");
                    phoneTxtField.clear();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            phoneTxtField.setDisable(false);
            sendInvitationBtn.setDisable(false);
        }
    };

    Runnable fetchUserInvitations = () -> {
        List<Invitation> myInvitations = null;
        try {
            //TODO Should be Changed to Current User model ID(PHONE)
            myInvitations = DTOObjAdapter.convertDtoInvitationList(invitationService.getAllUserInvitations(userModel.getPhoneNumber()));
        } catch (RemoteException e) {
            e.printStackTrace();
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

    private  void displayFailFeedBackMsg(String str){
        Platform.runLater(() ->{
            feedbackLabel.setStyle("-fx-text-fill: #dc3545");
            feedbackLabel.setText(str);
        });
    }
    private  void displaySucessFeedBackMsg(String str){
        Platform.runLater(() ->{
            feedbackLabel.setStyle("-fx-text-fill: #198754");
            feedbackLabel.setText(str);
        });
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
