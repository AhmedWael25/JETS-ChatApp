package jets.chatclient.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import jets.chatclient.gui.helpers.GpChatsManager;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.ServicesFactory;
import jets.chatclient.gui.helpers.StageCoordinator;
import jets.chatclient.gui.models.CurrentUserModel;
import jets.chatclient.gui.models.GpMessageModel;

import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


public class GpMessageFileController {

    @FXML
    private HBox msgcontainer;
    @FXML
    private Circle userStatus;
    @FXML
    private Circle userImg;

    @FXML
    private Label msgBody;

    @FXML
    private HBox metaDataContainer;

    @FXML
    private Label msgSenderName;

    @FXML
    private Label msgTimeStamp;

    private  String fileName;
    private  Integer fileId;

    public GpMessageFileController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/GpMsgFileCard.fxml"));
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
                        } catch (RemoteException | NotBoundException | FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            }
        });
    }

    public void setData(GpMessageModel msg){

        String[] arr = msg.getMsgContent().split(";");


        CurrentUserModel userModel = ModelsFactory.getInstance().getCurrentUserModel();

        if(msg.getSenderId().equals(userModel.getPhoneNumber())){
            msgcontainer.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            userModel.bindToUserAvatar(userImg);
            userModel.bindToUserStatus(userStatus);
            metaDataContainer.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            fileName = arr[0];
            fileId = -1;
        }else {
            GpChatsManager gpChatsManager = ModelsFactory.getInstance().getGpChatsManager();
            Circle participantImg = gpChatsManager.getParticipantImg(msg.getChatId(), msg.getSenderId());
            userImg.fillProperty().bind(participantImg.fillProperty());
            Circle participantStatus = gpChatsManager.getParticipantStatus(msg.getChatId(),msg.getSenderId());
            userStatus.fillProperty().bind(participantStatus.fillProperty());
            fileName = arr[0];
            fileId = Integer.parseInt(arr[1]);
        }

        msgBody.setText(fileName);
        msgSenderName.setText(msg.getSenderName());
        msgTimeStamp.setText(", @ "+msg.getTimeStamp());
    }

    public HBox getMsgCard(){
        return msgcontainer;
    }

}
