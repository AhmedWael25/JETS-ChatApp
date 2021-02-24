package jets.chatclient.gui.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.P2PChatManager;
import jets.chatclient.gui.helpers.ServicesFactory;
import jets.chatclient.gui.helpers.StageCoordinator;
import jets.chatclient.gui.models.CurrentUserModel;
import jets.chatclient.gui.models.P2PMessageModel;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class P2PMsgFileController {

    @FXML
    private HBox msgcontainer;

    @FXML
    private Circle userImg;

    @FXML
    private Label msgBody;

    @FXML
    private HBox metaDataContainer;

    @FXML
    private Label msgTimestamp;

    private String fileName;
    private Integer  fileId;

    public P2PMsgFileController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/MessageCardF.fxml"));
        fxmlLoader.setController(this);

        try {
            msgcontainer = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        msgBody.setOnMouseClicked(event -> {
            System.out.println(fileName);
            System.out.println(fileId);
            if(fileId != -1){

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Download File");
                fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
                fileChooser.setInitialFileName(fileName);
                File file = fileChooser.showSaveDialog(StageCoordinator.getInstance().getPrimaryStage());
                if(file != null){
                    new Thread(() ->{
                        try {
                            ServicesFactory servicesFactory = ServicesFactory.getInstance();
                            byte[] fileArr = servicesFactory.getFileDownloadService().downloadFile(fileId);
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                            bufferedOutputStream.write(fileArr);
                            bufferedOutputStream.flush();
                            bufferedOutputStream.close();
                        } catch (RemoteException | NotBoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            }
        });

    }

    public void setData(P2PMessageModel msg){

        String[] arr = msg.getMsgBody().split(";");


        CurrentUserModel userModel = ModelsFactory.getInstance().getCurrentUserModel();

        if(msg.getSenderId().equals(userModel.getPhoneNumber())) {
            msgcontainer.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            msgBody.setStyle("-fx-background-color: #304269; -fx-border-radius:  0 10 10 10 ; -fx-background-radius: 0 10 10 10;");
            msgBody.setTextFill(Color.WHITE);
            FontIcon fontIcon = (FontIcon) msgBody.getGraphic();
            fontIcon.setIconColor(Paint.valueOf("#fff"));
            userModel.bindToUserAvatar(userImg);
            msgTimestamp.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            //If Am the sender dont allow the download
            fileName = arr[0];
            fileId = -1;
        }
        else {
            P2PChatManager manager =  ModelsFactory.getInstance().getP2PChatManager();
            Circle friendAvatar = manager.getFriendImg(msg.getChatId());
            userImg.fillProperty().bind(friendAvatar.fillProperty());
            //If i am receiver
            fileName = arr[0];
            fileId = Integer.parseInt(arr[1]);
        }

        msgTimestamp.setText(msg.getMsgTime());
        msgBody.setText(fileName);
    }

    public HBox getMsgCard(){
        return msgcontainer;
    }

}
