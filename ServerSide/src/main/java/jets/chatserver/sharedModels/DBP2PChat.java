package jets.chatserver.sharedModels;

import java.io.Serializable;

public class DBP2PChat implements Serializable {

    private int chatId;
    private String chatStartDate;
    private String firstParticipant;
    private String secondParticipant;

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getChatStartDate() {
        return chatStartDate;
    }

    public void setChatStartDate(String chatStartDate) {
        this.chatStartDate = chatStartDate;
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

    @Override
    public String toString() {
        return "DBP2PChat{" +
                "chatId=" + chatId +
                ", chatStartDate='" + chatStartDate + '\'' +
                ", firstParticipant='" + firstParticipant + '\'' +
                ", secondParticipant='" + secondParticipant + '\'' +
                '}';
    }
}
