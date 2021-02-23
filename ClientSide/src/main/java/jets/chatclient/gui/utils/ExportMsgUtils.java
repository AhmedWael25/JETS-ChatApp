package jets.chatclient.gui.utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import jets.chatclient.gui.helpers.GpChatsManager;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.P2PChatManager;
import jets.chatclient.gui.models.CurrentUserModel;
import jets.chatclient.gui.models.GpMessageModel;
import jets.chatclient.gui.models.P2PChatModel;
import jets.chatclient.gui.models.P2PMessageModel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExportMsgUtils {


    ModelsFactory modelsFactory;
    CurrentUserModel currentUserModel;
    GpMessageModelAdaptor modifiedGpMessage;
    P2PMessageModelAdaptor modifiedP2PMessage;
    GpChatsManager gpChatsManager;
    P2PChatManager p2PChatManager;
    P2PChatModel p2PChatModel;

    List<GpMessageModelAdaptor> generateModifiedGpChatList(List<GpMessageModel> GpMessagesList) {
        List<GpMessageModelAdaptor> modifiedGpMsgList = new ArrayList<GpMessageModelAdaptor>();
        String senderId;
        String imageURL;
        Map<String,String> senderImageMap = new HashMap<>();
        Image senderImage;
        modelsFactory = ModelsFactory.getInstance();
        currentUserModel = modelsFactory.getCurrentUserModel();
        for (GpMessageModel message : GpMessagesList) {
            modifiedGpMessage = new GpMessageModelAdaptor();


            if (message.getSenderId().equals(currentUserModel.getPhoneNumber())) {
                modifiedGpMessage.fillwithGpMessageModel(message, true);
            } else {
                modifiedGpMessage.fillwithGpMessageModel(message, false);
            }



            // Get Images and save it
            gpChatsManager = modelsFactory.getGpChatsManager();
            senderId = message.getSenderId();
            if(senderImageMap.containsKey(senderId)){
                modifiedGpMessage.setImagePath(senderImageMap.get(senderId));
            }else{

                senderImage = gpChatsManager.getParticipantAvatar(message.getChatId(), senderId);
                imageURL = saveImageOnDesk(senderImage,senderId);
                senderImageMap.put(senderId, imageURL);
                modifiedGpMessage.setImagePath(imageURL);
            } // end of if else statement.
            modifiedGpMsgList.add(modifiedGpMessage);
        }//end for loop

        return modifiedGpMsgList;
    }


    List<P2PMessageModelAdaptor> generateModifiedP2PChatList(List<P2PMessageModel> peersMessages) {
        List<P2PMessageModelAdaptor> modifiedP2PMsgList = new ArrayList<P2PMessageModelAdaptor>();
        String senderId;
        String imagePath;
        Image senderImage;
        Map<String,String> senderImageMap = new HashMap<>();

        modelsFactory = ModelsFactory.getInstance();
        currentUserModel = modelsFactory.getCurrentUserModel();
        p2PChatManager = modelsFactory.getP2PChatManager();
        for (P2PMessageModel message : peersMessages) {
            modifiedP2PMessage = new P2PMessageModelAdaptor();



            // Use P2PchatModel to get sender name.
            p2PChatModel = p2PChatManager.getP2PChat(message.getChatId());

            //flag the message if the sender is the current user.
            if (message.getSenderId().equals(currentUserModel.getPhoneNumber())) {
                modifiedP2PMessage.fillWithP2PMessageModel(message, true);
                modifiedP2PMessage.setSenderName(currentUserModel.getDisplayName());
            } else {
                modifiedP2PMessage.fillWithP2PMessageModel(message, false);
                modifiedP2PMessage.setSenderName(p2PChatModel.getFriendName());
            }


            // Get Images and save it
            senderId = message.getSenderId();
            if(senderImageMap.containsKey(senderId)){
                modifiedP2PMessage.setImagePath(senderImageMap.get(senderId));
            }else{
                if (message.getSenderId().equals(currentUserModel.getPhoneNumber())){
                    senderImage = currentUserModel.getImage();
                }else{
                    senderImage = p2PChatModel.getFriendImg();
                }
                imagePath = saveImageOnDesk(senderImage,senderId);
                //Add the iamgePath to senders map
                senderImageMap.put(senderId,imagePath);
                modifiedP2PMessage.setImagePath(imagePath);
            } // end of if else statement.
            modifiedP2PMsgList.add(modifiedP2PMessage);
        }//end for loop

        return modifiedP2PMsgList;
    }


    public String saveImageOnDesk(Image image, String outputName)  {
        File outputFile = new File(outputName+".png");
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            System.out.println(outputFile.getCanonicalPath());
            ImageIO.write(bImage, "png", outputFile);
            System.out.println(outputFile.toURI().toURL().toString());
            System.out.println(outputFile.getPath());
            System.out.println(outputFile.getAbsolutePath());

            return outputFile.getName();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
