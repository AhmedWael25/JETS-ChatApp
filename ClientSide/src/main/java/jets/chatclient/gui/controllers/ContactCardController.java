package jets.chatclient.gui.controllers;



import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import jets.chatclient.gui.helpers.ContactsManager;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.models.ContactModel;
import jets.chatclient.gui.models.GpChatModel;
import jets.chatclient.gui.models.P2PChatModel;

import java.io.IOException;


public class ContactCardController {


    @FXML
    private HBox msgcontainer;

    @FXML
    private Circle userImg;

    @FXML
    private Circle userStatus;

    @FXML
    private Label userName;

    public  ContactCardController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/ContactsCard.fxml"));
        fxmlLoader.setController(this);

        try {
            msgcontainer = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setData(ContactModel contact){

        ContactsManager manager = ModelsFactory.getInstance().getContactsManager();

        userName.setText(contact.getContactName());

        Circle bindableImg = manager.getBindableContactImg(contact.getContactId());
        userImg.fillProperty().bind(bindableImg.fillProperty());


        Circle bindableStatus = manager.getBindableContactStatus(contact.getContactId());;
        userStatus.fillProperty().bind(bindableStatus.fillProperty());
    }

    public HBox getContactCard(){
        return msgcontainer;
    }


}
