package jets.chatserver.DBModels;

import java.util.List;

public class DBGpChat {

    private int gpChatId;
    private String gpChatName;
    private String gpChatImg;
    private String gpChatAdminId;
    private String gpChatStartDate;
    private String gpChatDesc;
    private List<String> participantsId;

    public String getGpChatDesc() {
        return gpChatDesc;
    }

    public void setGpChatDesc(String gpChatDesc) {
        this.gpChatDesc = gpChatDesc;
    }

    public int getGpChatId() {
        return gpChatId;
    }

    public void setGpChatId(int gpChatId) {
        this.gpChatId = gpChatId;
    }

    public String getGpChatName() {
        return gpChatName;
    }

    public void setGpChatName(String gpChatName) {
        this.gpChatName = gpChatName;
    }

    public String getGpChatImg() {
        return gpChatImg;
    }

    public void setGpChatImg(String gpChatImg) {
        this.gpChatImg = gpChatImg;
    }

    public String getGpChatAdminId() {
        return gpChatAdminId;
    }

    public void setGpChatAdminId(String gpChatAdminId) {
        this.gpChatAdminId = gpChatAdminId;
    }

    public String getGpChatStartDate() {
        return gpChatStartDate;
    }

    public void setGpChatStartDate(String gpChatStartDate) {
        this.gpChatStartDate = gpChatStartDate;
    }

    public List<String> getParticipantsId() {
        return participantsId;
    }

    public void setParticipantsId(List<String> participantsId) {
        this.participantsId = participantsId;
    }


    @Override
    public String toString() {
        return "DBGpChat{" +
                "gpChatId=" + gpChatId +
                ", gpChatName='" + gpChatName + '\'' +
                ", gpChatImg='" + "IMG" + '\'' +
                ", gpChatAdminId='" + gpChatAdminId + '\'' +
                ", gpChatStartDate='" + gpChatStartDate + '\'' +
                ", participantsId=" + participantsId +
                '}';
    }
}
