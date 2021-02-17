package jets.chatclient.gui.controllers;

import com.jfoenix.controls.*;
import commons.remotes.server.GpChatServiceInt;
import commons.sharedmodels.GpChatDto;
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
import jets.chatclient.gui.helpers.ServicesFactory;
import jets.chatclient.gui.helpers.adapters.DTOObjAdapter;
import jets.chatclient.gui.models.FriendModel;
import jets.chatclient.gui.models.GpChatModel;
import jets.chatclient.gui.models.P2PChatModel;
import jets.chatclient.gui.models.guimodels.GpChatViewCell;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

public class groupChatController  implements Initializable {


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
    private JFXListView msgListView;


    private GpChatServiceInt gpChatService;
    private ObservableList<GpChatModel> chats = FXCollections.observableArrayList();

    private String userIdDummy = "7";


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


    public void createGpChat(ActionEvent actionEvent) throws IOException {
        JFXAlert alert = new JFXAlert((Stage) gpChatMainContainer.getScene().getWindow());
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


    public void sendMsg(KeyEvent keyEvent) {

    }

    public void addGpChatToList(GpChatDto gpChatDto){
        new Thread(() -> {
            GpChatModel gpChatModel = DTOObjAdapter.convertDtoToObj(gpChatDto);
            System.out.println("EL7AMAAAMDULLAAAAH");
            Platform.runLater(() ->{
                chats.add(gpChatModel);
                chatListView.setItems(chats);
            });
        }).start();
    }
//    ================= RUNNABLES =====================
    Runnable fetchGpChats = () -> {
        List<GpChatModel> gpChatModelList = null;
    try {
        gpChatModelList = DTOObjAdapter.convertDtoGpChat(gpChatService.fetchAllUserGpChats(userIdDummy));
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
