package jets.chatclient.gui.controllers;

import com.jfoenix.controls.*;
import commons.remotes.server.P2PChatServiceInt;
import commons.sharedmodels.MsgType;
import commons.sharedmodels.P2PChatDto;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.P2PChatManager;
import jets.chatclient.gui.helpers.ServicesFactory;
import jets.chatclient.gui.helpers.adapters.DTOObjAdapter;
import jets.chatclient.gui.models.P2PMessageModel;
import jets.chatclient.gui.models.P2PChatModel;
import jets.chatclient.gui.models.guimodels.P2PChatViewCell;
import jets.chatclient.gui.models.guimodels.P2PMsgViewCell;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.*;


public class P2PChatController implements Initializable {

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
    private ListView<P2PMessageModel> msgListView;
    @FXML
    private JFXListView<P2PChatModel> chatCardListView;

    private P2PChatServiceInt p2pChatService;
    private P2PChatManager p2pChatManager;
    private Integer activeP2PChatId;

    private ObservableList<P2PChatModel> chats = FXCollections.observableArrayList();
    private ObservableList<P2PMessageModel> messages = FXCollections.observableArrayList();

    private String userIdDummy = "1";
//    private String userIdDummy = "2";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        p2pChatManager = ModelsFactory.getInstance().getP2PChatManager();
//        chatCardListView.setStyle("-fx-control-inner-background: #304269;");

        try {

            ServicesFactory servicesFactory = ServicesFactory.getInstance();
            p2pChatService =  servicesFactory.getP2PChatService();

            new Thread(fetchChatList).start();
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        setUpActiveP2PChat();
//        messages.setAll(p2pChatManager.getActiveChatMsgList());
        msgListView.setItems(messages);
        msgListView.setCellFactory(param -> new P2PMsgViewCell());

    }


//=========================== Methods That Will BE Called Through call backs ============ //
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

    public void SendMessageToChat(ActionEvent actionEvent){
        new Thread(() -> {
            P2PMessageModel msgModel = createMsgModel();

            // sent Message through network
            try {
                p2pChatService.sendMessage(DTOObjAdapter.convertObjToDto(msgModel));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            p2pChatManager.addMsg(msgModel);
            Platform.runLater(()->{
                messages.add(msgModel);
                msgListView.setItems(messages);
                typingArea.clear();
            });
        }).start();
    }

    public void addMsgToUi(P2PMessageModel msgModel){
        messages.add(msgModel);
        msgListView.setItems(messages);
        msgListView.setCellFactory(param -> new P2PMsgViewCell());

    }

    private P2PMessageModel createMsgModel(){

        P2PMessageModel msg = new P2PMessageModel();

        msg.setChatId(p2pChatManager.getActiveP2PChat());
        msg.setSenderId(userIdDummy);
        System.out.println("ATIVE CHAT"+activeP2PChatId);
        msg.setReceiverId(p2pChatManager.getParticipantId(activeP2PChatId));
        msg.setMsgType(MsgType.TEXT);
        msg.setMsgBody(typingArea.getText());
        Date currentDate = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat(  "dd-MM-yyyy HH:mm:ss");
        msg.setMsgTime(formatDate.format(currentDate.getTime()));

        return msg;
    }

    private void setUpActiveP2PChat(){
        chatCardListView.getSelectionModel().selectedItemProperty().addListener((observable, oldChat, selectedChat) -> {
            activeP2PChatId = selectedChat.getChatId();
            p2pChatManager.setActiveP2PChat(activeP2PChatId);

            messages.setAll(p2pChatManager.getMsgList(activeP2PChatId));
            msgListView.setItems(messages);
            msgListView.setCellFactory(param -> new P2PMsgViewCell());
        });
    }

//=================================== Runnables ========================== //

    Runnable fetchChatList = () -> {
        List<P2PChatModel> p2pChatModels = null;
        try{
            //TODO Should be Changed to Current User model ID(PHONE)
            p2pChatModels = DTOObjAdapter.convertDtop2pChatList(p2pChatService.fetchAllUserP2PChats(userIdDummy));
            p2pChatManager.addP2PChat(p2pChatModels);

            List<P2PChatModel> finalP2pChatModels = p2pChatModels;
            Platform.runLater(() ->{
                chats.addAll(finalP2pChatModels);
                chatCardListView.setItems(chats);
                chatCardListView.setCellFactory(param -> new P2PChatViewCell());
            });
        } catch(RemoteException e) {
            e.printStackTrace();
        }
    };
}