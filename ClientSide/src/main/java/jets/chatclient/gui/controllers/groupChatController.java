package jets.chatclient.gui.controllers;

import com.jfoenix.controls.*;
import commons.remotes.server.GpChatServiceInt;
import commons.sharedmodels.GpChatDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import jets.chatclient.gui.helpers.ServicesFactory;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

public class groupChatController  implements Initializable {


    public ListView chatCardListView;
    @FXML
    private JFXButton groupMembers;
    @FXML
    private JFXButton leaveBtn;
    @FXML
    private JFXButton chatOption;
    @FXML
    private JFXScrollPane msgScrollPane;
    @FXML
    private AnchorPane friendChatPane;
    @FXML
    private HBox searchChatContainer;
    @FXML
    private JFXButton searchChatBtn;
    @FXML
    private JFXTextField searchChatField;
    @FXML
    private JFXListView chatListView;
    @FXML
    private BorderPane chatViewPane;
    @FXML
    private JFXTextArea typingArea;
    @FXML
    private JFXButton uploadFilesBtn;
    @FXML
    private JFXButton sendMsgBtn;
    @FXML
    private JFXButton optionsBtn;
    @FXML
    private JFXListView msgListView;


    private GpChatServiceInt gpChatService;


    private String userIdDummy = "1";


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ServicesFactory servicesFactory = null;
        try {
            servicesFactory = ServicesFactory.getInstance();
            gpChatService =    servicesFactory.getGpChatService();

            new Thread(fetchGpChats).start();

            } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }


    public void sendMsg(KeyEvent keyEvent) {

    }

//    ================= RUNNABLES =====================
    Runnable fetchGpChats = () -> {
        List<GpChatDto> gpChatDtos = null;
    try {
        gpChatDtos = gpChatService.fetchAllUserGpChats(userIdDummy);
        System.out.println(gpChatDtos);
    } catch (RemoteException e) {
        e.printStackTrace();
    }
};


}
