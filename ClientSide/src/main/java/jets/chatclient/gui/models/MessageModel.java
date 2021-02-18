package jets.chatclient.gui.models;


public class MessageModel {

    private String senderId;
    private String receiverId;
    private String msgBody;
    private String friendImg;
    private String msgTime;


    private String fontStyle;
    private String fontColor;
    private int fontsSize;
    private Boolean bold;
    private Boolean italic;
    private Boolean underline;

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public String getFriendImg() {
        return friendImg;
    }

    public void setFriendImg(String friendImg) {
        this.friendImg = friendImg;
    }

    public String getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }

    public String getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(String fontStyle) {
        this.fontStyle = fontStyle;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public int getFontsSize() {
        return fontsSize;
    }

    public void setFontsSize(int fontsSize) {
        this.fontsSize = fontsSize;
    }

    public Boolean getBold() {
        return bold;
    }

    public void setBold(Boolean bold) {
        this.bold = bold;
    }

    public Boolean getItalic() {
        return italic;
    }

    public void setItalic(Boolean italic) {
        this.italic = italic;
    }

    public Boolean getUnderline() {
        return underline;
    }

    public void setUnderline(Boolean underline) {
        this.underline = underline;
    }

    @Override
    public String toString() {
        return "MessageModel{" +
                "senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", msgBody='" + msgBody + '\'' +
                ", friendImg='" + "img" + '\'' +
                ", msgTime='" + msgTime + '\'' +
                ", fontStyle='" + fontStyle + '\'' +
                ", fontColor='" + fontColor + '\'' +
                ", fontsSize=" + fontsSize +
                ", bold=" + bold +
                ", italic=" + italic +
                ", underline=" + underline +
                '}';
    }
}
