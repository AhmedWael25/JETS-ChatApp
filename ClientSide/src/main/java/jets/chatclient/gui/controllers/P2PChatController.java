package jets.chatclient.gui.controllers;

import com.jfoenix.controls.*;
import commons.remotes.server.P2PChatServiceInt;
import commons.sharedmodels.P2PChatDto;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import jets.chatclient.gui.helpers.ServicesFactory;
import jets.chatclient.gui.helpers.adapters.DTOObjAdapter;
import jets.chatclient.gui.models.Invitation;
import jets.chatclient.gui.models.P2PChatModel;
import jets.chatclient.gui.models.guimodels.P2PChatViewCell;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;


public class P2PChatController implements Initializable {

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
    @FXML
    private JFXListView<P2PChatModel> chatCardListView;

    private ObservableList<P2PChatModel> chats = FXCollections.observableArrayList();
    private P2PChatServiceInt p2pChatService;

    private String userIdDummy = "1";
//    private String userIdDummy = "7";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chatCardListView.setStyle("-fx-control-inner-background: #304269;");

        try {

            ServicesFactory servicesFactory = ServicesFactory.getInstance();
            p2pChatService =  servicesFactory.getP2PChatService();

            new Thread(fetchChatList).start();
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }


//===========================Methods That Will BE Called Through call backs ============
    public void addNewChatToList(P2PChatDto chatDto){
        new Thread(() -> {
            P2PChatModel p2pChatModel = new P2PChatModel();
            p2pChatModel = DTOObjAdapter.convertDtoToObj(chatDto);
            P2PChatModel finalp2pChatModel = p2pChatModel;
            Platform.runLater(()->{
                chats.addAll(finalp2pChatModel);
                chatCardListView.setItems(chats);
            });
        }).start();
    }
//===================================RUNNABLES==========================



    Runnable fetchChatList = () -> {
        List<P2PChatModel> p2pChatModels = null;
        try {
            //TODO Should be Changed to Current User model ID(PHONE)
            p2pChatModels = DTOObjAdapter.convertDtop2pChatList(p2pChatService.fetchAllUserP2PChats(userIdDummy));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        for (P2PChatModel fd : p2pChatModels){
            System.out.println("FROM CHAT SCREEN"+fd);
        }
        List<P2PChatModel> finalP2pChatModels = p2pChatModels;
        Platform.runLater(() ->{
            chats.addAll(finalP2pChatModels);
            chatCardListView.setItems(chats);
            chatCardListView.setCellFactory(param -> new P2PChatViewCell());
        });
    };
}