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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jets.chatclient.gui.helpers.GpChatsManager;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.ServicesFactory;
import jets.chatclient.gui.helpers.StageCoordinator;
import jets.chatclient.gui.helpers.adapters.DTOObjAdapter;
import jets.chatclient.gui.models.CurrentUserModel;
import jets.chatclient.gui.models.GpChatModel;
import jets.chatclient.gui.models.GpMessageModel;
import jets.chatclient.gui.models.P2PMessageModel;
import jets.chatclient.gui.models.guimodels.GPChatMsgViewCell;
import jets.chatclient.gui.models.guimodels.GpChatViewCell;
import jets.chatclient.gui.utils.ExportMsgAsHtml;

import java.io.*;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    private CurrentUserModel userModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        userModel = ModelsFactory.getInstance().getCurrentUserModel();

        gpChatsManager = ModelsFactory.getInstance().getGpChatsManager();
        try {
            ServicesFactory servicesFactory =  ServicesFactory.getInstance();
            gpChatService = servicesFactory.getGpChatService();

            disableControls(true);
            new Thread(fetchGpChats).start();
            setUpActiveChat();
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
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

        if(typingArea.getText().isBlank()) return;

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
                int index = msgs.size();
                msgListView.scrollTo(index);
            });
        }).start();
    }

    public void sendMsgByKey(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.ENTER) {

            if (!typingArea.getText().isBlank()) {

                if (keyEvent.isAltDown()) {
                    typingArea.appendText("\n");
                } else {

                    new Thread(() -> {
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
                        Platform.runLater(() -> {
                            msgs.add(msg);
                            msgListView.setItems(msgs);
                            typingArea.clear();
                            int index = msgs.size();
                            msgListView.scrollTo(index);
                        });
                    }).start();
                }
            }
        }
    }



    public void sendFile(ActionEvent actionEvent) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        File file = fileChooser.showOpenDialog(null);
        if(file == null) return;
        long sizeInMb = file.length() / (1024 * 1024);
        if(sizeInMb > 1024 ){
            displayFileLargerAlert();
            return;
        }
        new Thread(() ->{
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                byte[] fileArr = bufferedInputStream.readAllBytes();

                GpMessageModel msgModel = createMsgFileModel(file.getName());
                gpChatService.sendFile(fileArr,DTOObjAdapter.convertoObjToDto(msgModel));
                gpChatsManager.addMsg(msgModel);
                Platform.runLater(() ->{
                    msgs.add(msgModel);
                    msgListView.setItems(msgs);
                    int index = msgs.size();
                    msgListView.scrollTo(index);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


    public void receiveFile(byte[] fileArr,GpMessageModel model){
        Platform.runLater(()->{
            msgs.add(model);
            msgListView.setItems(msgs);
            String fileName = model.getMsgContent();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            fileChooser.setInitialFileName(fileName);
            fileChooser.setTitle("DOWn Incoming File");
            File file = fileChooser.showSaveDialog(StageCoordinator.getInstance().getPrimaryStage());

            if(file != null){
                new Thread(() ->{
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                        bufferedOutputStream.write(fileArr);
                        bufferedOutputStream.flush();
                        bufferedOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });
    }

    public void addGpChatToList(GpChatDto gpChatDto){
        new Thread(() -> {

            GpChatModel gpChatModel = DTOObjAdapter.convertDtoToObj(gpChatDto);
            //If new chat is to be added, put it on the manager list
            gpChatsManager.addGpChat(gpChatModel);
            Platform.runLater(() ->{
                //Make Sure that if that is the first one we Add
                //Make it automatically active
                if(chats.size() <= 0 ){
                    gpChatsManager.setActiveChat(gpChatModel.getGpChatId());
                }
                chats.add(gpChatModel);
                chatListView.setItems(chats);
                disableControls(false);

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
        Platform.runLater(() ->{
            msgs.add(model);
            msgListView.setItems(msgs);
            int index = msgs.size();
            msgListView.scrollTo(index);
            msgListView.setCellFactory(param -> new GPChatMsgViewCell());
        });
    }

    public  void closeCreationAlert(){
        alert.hide();
    }

    private GpMessageModel createMsgModel(){
        GpMessageModel msg = new GpMessageModel();

        msg.setChatId(gpChatsManager.getActiveChat());
        msg.setMsgType(MsgType.TEXT);
        msg.setSenderName(userModel.getDisplayName());
        msg.setSenderId(userModel.getPhoneNumber());
        msg.setMsgContent(typingArea.getText().trim());

        Date currDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(  "dd-MM-yyyy HH:mm:ss");
        msg.setTimeStamp(formatter.format(currDate.getTime()));

        return  msg;
    }

    private GpMessageModel createMsgFileModel(String fileName){
        GpMessageModel msg = new GpMessageModel();

        msg.setChatId(gpChatsManager.getActiveChat());
        msg.setMsgType(MsgType.FILE);
        msg.setSenderName(userModel.getDisplayName());
        msg.setSenderId(userModel.getPhoneNumber());
        Date currDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(  "dd-MM-yyyy HH:mm:ss");
        msg.setTimeStamp(formatter.format(currDate.getTime()));
        msg.setMsgContent(fileName);
        return  msg;
    }

    //    ================= RUNNABLES =====================
    Runnable fetchGpChats = () -> {
        List<GpChatModel> gpChatModelList = null;
        try {
            gpChatModelList = DTOObjAdapter.convertDtoGpChat(gpChatService.fetchAllUserGpChats(userModel.getPhoneNumber()));
            //Creat GP chat in manager for model we get
            gpChatsManager.addGpChat(gpChatModelList);
            List<GpChatModel> finalGpChatModelList = gpChatModelList;
            Platform.runLater(() -> {
                chats.addAll(finalGpChatModelList);
                chatListView.setItems(chats);
                chatListView.setCellFactory(param -> new GpChatViewCell());
                msgListView.setCellFactory(param -> new GPChatMsgViewCell());

                //I Still have no chats--If So disable btn and text area
                //Until a new chat is added
                if(chats.size() > 0){
                    disableControls(false);
                    //Set Active Chat in manager
                    gpChatsManager.setActiveChat(chats.get(0).getGpChatId());
                    chatListView.getSelectionModel().select(0);
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    };

    private void disableControls(boolean status){
        typingArea.setDisable(status);
        sendMsgBtn.setDisable(status);
        leaveBtn.setDisable(status);
        uploadFilesBtn.setDisable(status);
    }
    private  void displayFileLargerAlert(){
        JFXAlert alert = new JFXAlert((Stage) gpChatMainContainer.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(true);
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("File Upload"));
        Label body = new Label("Maximum Allowed Size is 1GB.");
        layout.setBody(body);

        JFXButton closeButton = new JFXButton("Cancel");
        closeButton.getStyleClass().add("dialog-accept");
        closeButton.setStyle("-fx-text-fill: orange;-fx-font-size: 16");
        closeButton.setOnAction(event -> alert.hide());

        layout.setActions(closeButton);
        alert.setContent(layout);
        alert.show();
    }

    private void displayExportingDoneAlert(){
        JFXAlert alert = new JFXAlert((Stage) gpChatMainContainer.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(true);
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("Chat Export"));
        Label body = new Label("Your Chat Has Been Exported. \n Check it OUT!");
        layout.setBody(body);

        JFXButton closeButton = new JFXButton("Cancel");
        closeButton.getStyleClass().add("dialog-accept");
        closeButton.setStyle("-fx-text-fill: orange;-fx-font-size: 18;-fx-background-color: white ");
        closeButton.setOnAction(event -> alert.hide());

        layout.setActions(closeButton);
        alert.setContent(layout);
        alert.show();
    }

    public void exportHTML(ActionEvent actionEvent) {

        new Thread(() ->{
            List<GpMessageModel> messageList = gpChatsManager.getMsgList(gpChatsManager.getActiveChat());
            ExportMsgAsHtml exportMsgAsHtml = new ExportMsgAsHtml();
            exportMsgAsHtml.exportGroupMessages((ArrayList<GpMessageModel>) messageList);
            Platform.runLater(this::displayExportingDoneAlert);
        }).start();

    }

}
