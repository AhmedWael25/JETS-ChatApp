package jets.chatclient.gui.controllers;

import com.jfoenix.controls.*;
import commons.remotes.server.GpChatServiceInt;
import commons.sharedmodels.GpChatDto;
import commons.sharedmodels.MsgType;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jets.chatclient.gui.helpers.GpChatsManager;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.ServicesFactory;
import jets.chatclient.gui.helpers.adapters.DTOObjAdapter;
import jets.chatclient.gui.models.GpChatModel;
import jets.chatclient.gui.models.GpMessageModel;
import jets.chatclient.gui.models.guimodels.GPChatMsgViewCell;
import jets.chatclient.gui.models.guimodels.GpChatViewCell;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class GroupChatController implements Initializable {

    JFXAlert alert;
    public ListView gpChatListView;
    public JFXButton createGpChatBtn;
    public AnchorPane gpChatMainContainer;
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
    private JFXListView<GpChatModel> chatListView;
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
    private JFXListView<GpMessageModel> msgListView;

    //Map That will hold gpchatId And List of it's Messages;
    private Integer activeChatId;
    private GpChatsManager gpChatsManager;
    private GpChatServiceInt gpChatService;

    private ObservableList<GpChatModel> chats = FXCollections.observableArrayList();
    private ObservableList<GpMessageModel> msgs = FXCollections.observableArrayList();

    //TODO CHange To curr user model
    private String userIdDummy = "3";
    private String userName  = "User 3";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        gpChatsManager = ModelsFactory.getInstance().getGpChatsManager();
        try {
            ServicesFactory servicesFactory =  ServicesFactory.getInstance();
            gpChatService = servicesFactory.getGpChatService();

            new Thread(fetchGpChats).start();

            } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        setUpActiveChat();
        activeChatId = 1;
        gpChatsManager.setActiveChat(1);
        msgs.setAll(gpChatsManager.getActiveChatMsgList());
        msgListView.setItems(msgs);
        msgListView.setCellFactory(param -> new GPChatMsgViewCell());

        //TRY

    }

    public void createGpChat(ActionEvent actionEvent) throws IOException {
        alert = new JFXAlert((Stage) gpChatMainContainer.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(true);
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("Create Your New Awesome Group Chat!"));
        layout.setBody((AnchorPane)new FXMLLoader(getClass().getResource("/views/GpChatCreation.fxml")).load());

        JFXButton closeButton = new JFXButton("Cancel");
        closeButton.getStyleClass().add("dialog-accept");
        closeButton.setStyle("-fx-text-fill: orange;-fx-font-size: 16");
        closeButton.setOnAction(event -> alert.hide());

        layout.setActions(closeButton);
        alert.setContent(layout);
        alert.show();
    }

    public void sendMessage(ActionEvent actionEvent) {
        new Thread(() ->{
            //--Construct Message Model Object
            GpMessageModel msg = createMsgModel();
            //--Send Msg Over The Network
            try {
                gpChatService.sendMessage(DTOObjAdapter.convertoObjToDto(msg));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            //--Add To List Of Messages
            //--Add In Gp Chat Manager
            gpChatsManager.addMsg(msg);
            Platform.runLater(()->{
                msgs.add(msg);
                msgListView.setItems(msgs);
                typingArea.clear();
            });
        }).start();
    }


    public void addGpChatToList(GpChatDto gpChatDto){
        new Thread(() -> {
            GpChatModel gpChatModel = DTOObjAdapter.convertDtoToObj(gpChatDto);
            gpChatsManager.addGpChat(gpChatModel);
            Platform.runLater(() ->{
                chats.add(gpChatModel);
                chatListView.setItems(chats);
            });
        }).start();
    }

    private void setUpActiveChat(){
        chatListView.getSelectionModel().selectedItemProperty().addListener((observable, oldChat, selectedChat) -> {
            activeChatId = selectedChat.getGpChatId();
            gpChatsManager.setActiveChat(activeChatId);

            msgs.setAll(gpChatsManager.getMsgList(activeChatId));
            msgListView.setItems(msgs);
            msgListView.setCellFactory(param -> new GPChatMsgViewCell());
        });
    }

    public void addMsgToUi(GpMessageModel model){
        msgs.add(model);
        msgListView.setItems(msgs);
//        msgListView.setCellFactory(param -> new GPChatMsgViewCell());
    }
    public void sendMsg(KeyEvent keyEvent) {
    }
    public  void closeCreationAlert(){
        alert.hide();
    }
    private GpMessageModel createMsgModel(){
        GpMessageModel msg = new GpMessageModel();

        msg.setChatId(gpChatsManager.getActiveChat());
        msg.setMsgType(MsgType.TEXT);
        msg.setSenderName(userName);
        msg.setSenderId(userIdDummy);
        Date currDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(  "dd-MM-yyyy HH:mm:ss");
        msg.setTimeStamp(formatter.format(currDate.getTime()));
        msg.setMsgContent(typingArea.getText());

        return  msg;
    }

    //    ================= RUNNABLES =====================
    Runnable fetchGpChats = () -> {
        List<GpChatModel> gpChatModelList = null;
        try {
            gpChatModelList = DTOObjAdapter.convertDtoGpChat(gpChatService.fetchAllUserGpChats(userIdDummy));
            gpChatsManager.addGpChat(gpChatModelList);
            List<GpChatModel> finalGpChatModelList = gpChatModelList;
            Platform.runLater(() -> {
                chats.addAll(finalGpChatModelList);
                chatListView.setItems(chats);
                chatListView.setCellFactory(param -> new GpChatViewCell());
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    };
}
