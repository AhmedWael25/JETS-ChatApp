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
import jets.chatclient.gui.models.CurrentUserModel;
import jets.chatclient.gui.models.P2PMessageModel;
import jets.chatclient.gui.models.P2PChatModel;
import jets.chatclient.gui.models.guimodels.P2PChatViewCell;
import jets.chatclient.gui.models.guimodels.P2PMsgViewCell;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;


public class P2PChatController implements Initializable {

    public JFXButton chatOption;
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
    private JFXListView<P2PChatModel> chatListView;

    private P2PChatServiceInt p2pChatService;
    private P2PChatManager p2pChatManager;
    private Integer activeP2PChatId;

    private ObservableList<P2PChatModel> chats = FXCollections.observableArrayList();
    private ObservableList<P2PMessageModel> messages = FXCollections.observableArrayList();

    private CurrentUserModel userModel;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        p2pChatManager = ModelsFactory.getInstance().getP2PChatManager();
        userModel = ModelsFactory.getInstance().getCurrentUserModel();

        try {

            ServicesFactory servicesFactory = ServicesFactory.getInstance();
            p2pChatService =  servicesFactory.getP2PChatService();

            disableControls(true);
            new Thread(fetchChatList).start();
            setUpActiveP2PChat();

        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

//=========================== Methods That Will BE Called Through call backs ============ //
    public void addNewChatToList(P2PChatDto chatDto){
        new Thread(() -> {
            P2PChatModel p2pChatModel = DTOObjAdapter.convertDtoToObj(chatDto);
            p2pChatManager.addP2PChat(p2pChatModel);
            Platform.runLater(()->{
                if(chats.size() <= 0 ){
                    p2pChatManager.setActiveP2PChat(p2pChatModel.getChatId());
                }
                chats.addAll(p2pChatModel);
                chatListView.setItems(chats);
                disableControls(false);
            });
        }).start();
    }

    public void sendMessage(ActionEvent actionEvent){
        if(typingArea.getText().equals("")) return;
        new Thread(() -> {
            System.out.println(activeP2PChatId);
            System.out.println(p2pChatManager.getActiveP2PChat());


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
                int index = messages.size();
                msgListView.scrollTo(index);
            });
        }).start();
    }

    public void addMsgToUi(P2PMessageModel msgModel){
        Platform.runLater(() -> {

            System.out.println("MSG ADDED TO UI");
            messages.add(msgModel);
            msgListView.setItems(messages);
            msgListView.setCellFactory(param -> new P2PMsgViewCell());
//            int index = messages.size();
//            msgListView.scrollTo(index);
//            System.out.println("MSG SENT TO UT");
//            System.out.println(activeP2PChatId);
//            System.out.println(p2pChatManager.getActiveP2PChat());

        });
    }

    private P2PMessageModel createMsgModel(){

        P2PMessageModel msg = new P2PMessageModel();

        msg.setChatId(p2pChatManager.getActiveP2PChat());
        msg.setMsgType(MsgType.TEXT);
        msg.setSenderId(userModel.getPhoneNumber());
        msg.setReceiverId(p2pChatManager.getParticipantId(p2pChatManager.getActiveP2PChat()));
        msg.setMsgBody(typingArea.getText());

        Date currentDate = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat(  "dd-MM-yyyy HH:mm:ss");
        msg.setMsgTime(formatDate.format(currentDate.getTime()));

        return msg;
    }

    private void setUpActiveP2PChat(){
        chatListView.getSelectionModel().selectedItemProperty().addListener((observable, oldChat, selectedChat) -> {
            activeP2PChatId = selectedChat.getChatId();
            p2pChatManager.setActiveP2PChat(activeP2PChatId);

            System.out.println(activeP2PChatId);
            System.out.println(p2pChatManager.getActiveP2PChat());

            messages.setAll(p2pChatManager.getMsgList(p2pChatManager.getActiveP2PChat()));
            msgListView.setItems(messages);
            msgListView.setCellFactory(param -> new P2PMsgViewCell());
        });
    }

//=================================== Runnables ========================== //

    Runnable fetchChatList = () -> {
        List<P2PChatModel> p2pChatModels = null;
        try{
            //TODO Should be Changed to Current User model ID(PHONE)
            p2pChatModels = DTOObjAdapter.convertDtop2pChatList(p2pChatService.fetchAllUserP2PChats(userModel.getPhoneNumber()));
            //Create P2P Chats in manager for all chats we fetch
            p2pChatManager.addP2PChat(p2pChatModels);
            List<P2PChatModel> finalP2pChatModels = p2pChatModels;

            Platform.runLater(() ->{
                chats.addAll(finalP2pChatModels);
                chatListView.setItems(chats);
                chatListView.setCellFactory(param -> new P2PChatViewCell());
                msgListView.setCellFactory(param -> new P2PMsgViewCell());

                //I Still have no chats--If So disable btn and text area
                //Until a new chat is added
                if(chats.size() > 0){
                    disableControls(false);

                    p2pChatManager.setActiveP2PChat(chats.get(0).getChatId());
                    chatListView.getSelectionModel().select(0);
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    };

    public void sendFile(ActionEvent actionEvent) {
    }



    private void disableControls(boolean status){
        typingArea.setDisable(status);
        sendMsgBtn.setDisable(status);
        chatOption.setDisable(status);
        uploadFilesBtn.setDisable(status);
    }


}