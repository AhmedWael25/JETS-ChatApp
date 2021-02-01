package jets.chatclient.gui.controllers;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import jets.chatclient.App;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class P2PChatController {

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

    Parent item;
    MessageViewController control;

    public void sendMsg(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (!typingArea.getText().isBlank()) {
                if(keyEvent.isAltDown()){
                    typingArea.appendText("\n");
                }else {
//                    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("MessageView.fxml"));
//                    item = fxmlLoader.load();
//                    control = fxmlLoader.getController();
////                  getListViewFactory();
//                    typingArea.setText("");
                }

            } else{
                typingArea.clear();
            }
        }
    }

//    private void getListViewFactory() {
//        msgListView.setCellFactory(p -> new ListCell<File>() {
//            @Override
//            protected void updateItem(File file, boolean empty) {
//                super.updateItem(file, empty);
//                if (file == null || empty) {
//                    setText(null);
//                    setGraphic(null);
//                } else {
//                    control.msgContainer.setTextAlignment(TextAlignment.valueOf(typingArea.getText()));
//                    //control.msgContainer.setWrapText(true);
//                    //control.userPic.setImage(profilePic.getImage());
//                    control.displayName.setText("Galal");
//                    control.msgTime.setText(new Date().toString());
//                }
//            }
//        });
//    }

}

