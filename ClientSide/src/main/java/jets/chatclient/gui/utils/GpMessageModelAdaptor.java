package jets.chatclient.gui.utils;

import commons.sharedmodels.MsgType;
import jets.chatclient.gui.models.GpMessageModel;

public class GpMessageModelAdaptor {


    private String senderName;
    private String imagePath;
    private String msgContent;
    private String timeStamp;
    private boolean fromCurrentUser;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isFromCurrentUser() {
        return fromCurrentUser;
    }

    public void setFromCurrentUser(boolean fromCurrentUser) {
        this.fromCurrentUser = fromCurrentUser;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }




    void fillwithGpMessageModel(GpMessageModel message, boolean isCurrentUser){

        if(message.getMsgType() == MsgType.TEXT){
            this.setMsgContent(message.getMsgContent());
        }else {
            this.setMsgContent(message.getMsgContent().split(";")[0]);
        }
        this.setSenderName(message.getSenderName());
        this.setTimeStamp(message.getTimeStamp());
        this.setFromCurrentUser(isCurrentUser);
    }

}
