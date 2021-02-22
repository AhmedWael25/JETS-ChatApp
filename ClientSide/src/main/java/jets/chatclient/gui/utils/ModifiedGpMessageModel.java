package jets.chatclient.gui.utils;

import commons.sharedmodels.MsgType;
import jets.chatclient.gui.models.GpMessageModel;

public class ModifiedGpMessageModel {


    private String senderId;
    private String msgContent;
    private String timeStamp;
    private String senderName;
    private MsgType msgType;
    private Integer chatId;
    private boolean fromCurrentUser;


    public boolean isFromCurrentUser() {
        return fromCurrentUser;
    }

    public void setFromCurrentUser(boolean fromCurrentUser) {
        this.fromCurrentUser = fromCurrentUser;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
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

    public MsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }


    void fillwithGpMessageModel(GpMessageModel message, boolean isCurrentUser){
        
       this.setSenderId(message.getSenderId());
       this.setSenderName(message.getSenderName());
       this.setMsgContent(message.getMsgContent());
       this.setTimeStamp(message.getTimeStamp());
       this.setChatId(message.getChatId());
       this.setMsgType(message.getMsgType());
       this.setFromCurrentUser(isCurrentUser);
    }


    @Override
    public String toString() {
        return "MessageModel{" +
                "senderId='" + senderId + '\'' +
                ", msgContent='" + msgContent + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", senderName='" + senderName + '\'' +
                ", msgType=" + msgType +
                ", chatId=" + chatId +
                '}';
    }
}
