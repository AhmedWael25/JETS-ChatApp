package commons.sharedmodels;

import java.io.Serializable;


public class GpMessageDto implements Serializable {

    private String senderId;
    private String msgContent;
    private String timeStamp;
    private String senderName;
    private MsgType msgType;
    private Integer chatId;

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

    @Override
    public String toString() {
        return "MessageDto{" +
                "senderId='" + senderId + '\'' +
                ", msgContent='" + msgContent + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", senderName='" + senderName + '\'' +
                ", msgType=" + msgType +
                '}';
    }
}
