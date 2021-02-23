package jets.chatclient.gui.utils;


import commons.sharedmodels.MsgType;
import jets.chatclient.gui.models.P2PMessageModel;

public class P2PMessageModelAdaptor {


    private String senderName;
    private String imagePath;
    private String msgContent;
    private String timeStamp;
    private boolean fromCurrentUser;




    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
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

    void fillWithP2PMessageModel(P2PMessageModel message, boolean isCurrentUser){

        if(message.getMsgType() == MsgType.TEXT){
            this.setMsgContent(message.getMsgBody());
        }else {
            this.setMsgContent(message.getMsgBody().split(";")[0]);
        }
        this.setTimeStamp(message.getMsgTime());
        this.setFromCurrentUser(isCurrentUser);
    }
}
