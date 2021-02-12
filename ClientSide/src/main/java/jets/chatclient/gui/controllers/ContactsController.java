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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.adapters.DTOObjAdapter;
import jets.chatclient.gui.models.Invitation;
import jets.chatclient.gui.models.guimodels.InvitationViewCell;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.ResourceBundle;



public class ContactsController implements Initializable {

    public Button dummybtn;
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
    private  String userIdDummy = "7";
    private  String userNameDummy = "user7";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Test Chat View initialized");

        //Initialize Services
        //Init the Invitation And Friend Adding Services
        try {

            Registry reg = LocateRegistry.getRegistry(3000);//TODO change
            invitationService = (InvitationServiceInt) reg.lookup("InvitationService");
            addFriendService = (AddFriendServiceInt) reg.lookup("AddFriendService");

            new Thread(fetchUserInvitations).start();


        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

//        /==============================================================

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



    //================== RUNNABLES ===================
    Runnable addInvitationToList = () -> {

    };


    Runnable sendInvitation = () -> {
        //TODO Phone Validation
        boolean isSent;
        if(!phoneTxtField.getText().equals(""))  {

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
            myInvitations = DTOObjAdapter.convertDtoList(invitationService.getAllUserInvitations(userIdDummy));
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


    public void dumdum(ActionEvent actionEvent) {
        new Thread(fetchUserInvitations).start();
    }
}
