package jets.chatclient.gui.controllers;

import commons.remotes.server.AddFriendServiceInt;
import commons.remotes.server.InvitationServiceInt;
import commons.sharedmodels.Invitation;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;



public class ContactsController implements Initializable {



    private InvitationServiceInt invitationService;
    private AddFriendServiceInt addFriendService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Test Chat View initialized");

        //Initialize Services
        //Init the Invitation And Friend Adding Services
        try {

            Registry reg = LocateRegistry.getRegistry(3000);
            invitationService = (InvitationServiceInt) reg.lookup("InvitationService");
            addFriendService = (AddFriendServiceInt) reg.lookup("AddFriendService");


           System.out.println( invitationService.sendInvitation(new Invitation(),"12") );


        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }


    }

    public void addFriend(ActionEvent actionEvent) {

    }
}
