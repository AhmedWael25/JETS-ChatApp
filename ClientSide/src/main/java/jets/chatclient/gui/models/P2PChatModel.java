package jets.chatclient.gui.models;

public class P2PChatModel {

    private int chatId;
    private String firstParticipant;
    private String secondParticipant;
    private String msgContent;
    private String msgTime;

    public P2PChatModel(int chatId, String firstParticipant, String secondParticipant, String msgContent, String msgTime) {
        this.chatId = chatId;
        this.firstParticipant = firstParticipant;
        this.secondParticipant = secondParticipant;
        this.msgContent = msgContent;
        this.msgTime = msgTime;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getFirstParticipant() {
        return firstParticipant;
    }

    public void setFirstParticipant(String firstParticipant) {
        this.firstParticipant = firstParticipant;
    }

    public String getSecondParticipant() {
        return secondParticipant;
    }

    public void setSecondParticipant(String secondParticipant) {
        this.secondParticipant = secondParticipant;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }

    @Override
    public String toString() {
        return "P2PChat{" +
                "chatId=" + chatId +
                ", firstParticipant='" + firstParticipant + '\'' +
                ", secondParticipant='" + secondParticipant + '\'' +
                '}';
    }

}
