package jets.chatclient.gui.controllers;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class groupChatController {


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

    public void sendMsg(KeyEvent keyEvent) {

    }

}
