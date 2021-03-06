package jets.chatserver.gui.controllers;

import com.jfoenix.controls.JFXTextArea;
import commons.remotes.client.ClientInterface;
import commons.utils.ImageEncoderDecoder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import jets.chatserver.database.daoImpl.UserDaoImpl;
import jets.chatserver.gui.helpers.ModelsFactory;
import jets.chatserver.network.ServerInit;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;

public class ServerAnnouncementsController implements Initializable {

    public Label emptyDt;
    Map<String, ClientInterface> currentConnectedUsers = null;
    @FXML
    private JFXTextArea announcementMsg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setCurrentConnectedUsers(){
      this.currentConnectedUsers= ModelsFactory.getInstance().getCurrentConnectedUsers();
    }

    @FXML
    public boolean sendAnnouncement(){
        if(!announcementMsg.validate()){
            return false;
        }
        if(!announcementMsg.getText().equals("")){
            currentConnectedUsers=  ServerInit.currentConnectedUsers;
        String serverImage = "";
        ImageEncoderDecoder imageEncoderDecoder = new ImageEncoderDecoder();
        try {
            File f = new File(getClass().getResource("/images/symbol.png").getPath());
            serverImage = imageEncoderDecoder.getEncodedImage(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String finalServerImage = serverImage;
        currentConnectedUsers.forEach((id, clientInterface) -> {
            try {
                    clientInterface.pushServerAnnouncements(announcementMsg.getText(), finalServerImage);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

        });
            emptyDt.setText("");
            announcementMsg.clear();
            return  true;
        }
        emptyDt.setText("Please Enter Your Message");
     return false;
    }
    }


