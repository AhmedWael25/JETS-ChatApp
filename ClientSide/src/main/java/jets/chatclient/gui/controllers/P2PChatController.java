package jets.chatclient.gui.controllers;

import com.jfoenix.controls.*;
import commons.remotes.server.AddFriendServiceInt;
import commons.remotes.server.InvitationServiceInt;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import jets.chatclient.gui.helpers.adapters.DTOObjAdapter;
import jets.chatclient.gui.models.Invitation;
import jets.chatclient.gui.models.P2PChatModel;
import jets.chatclient.gui.models.guimodels.InvitationViewCell;
import jets.chatclient.gui.models.guimodels.P2PChatViewCell;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
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

    P2PChatModel chat1 = new P2PChatModel(1, "Trika", "Ahmed Galal",
            "Welcome to chat", "12:44");

    P2PChatModel chat2 = new P2PChatModel(2, "Muller", "Ahmed Wael",
            "See you Soon", "12:10");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            new Thread(fetchChatList).start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    Runnable fetchChatList = () -> {

        List<P2PChatModel> chatList = new ArrayList<>();

        chatList.add(chat1);
        chatList.add(chat2);

        Platform.runLater(() ->{
            chats.addAll(chatList);
            chatCardListView.setStyle("-fx-control-inner-background: #304269;");
            chatCardListView.setItems(chats);
            chatCardListView.setCellFactory(param -> new P2PChatViewCell());
        });
    };
}