package jets.chatclient.gui.models;

import javafx.scene.image.Image;

import java.util.List;

public class GpChatModel {

    private int gpChatId;
    private String gpChatName;
    private String gpChatAdminId;
    private String gpChatStartDate;
    private String gpChatDesc;
    private Image gChatImage;
    List<ParticipantModel> gpParticipants;


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

    public String getGpChatDesc() {
        return gpChatDesc;
    }

    public void setGpChatDesc(String gpChatDesc) {
        this.gpChatDesc = gpChatDesc;
    }

    public Image getgChatImage() {
        return gChatImage;
    }

    public void setgChatImage(Image gChatImage) {
        this.gChatImage = gChatImage;
    }

    public List<ParticipantModel> getGpParticipants() {
        return gpParticipants;
    }

    public void setGpParticipants(List<ParticipantModel> gpParticipants) {
        this.gpParticipants = gpParticipants;
    }


    @Override
    public String toString() {
        return "GpChatModel{" +
                "gpChatId=" + gpChatId +
                ", gpChatName='" + gpChatName + '\'' +
                ", gpChatAdminId='" + gpChatAdminId + '\'' +
                ", gpChatStartDate='" + gpChatStartDate + '\'' +
                ", gpChatDesc='" + gpChatDesc + '\'' +
                ", gChatImage=" + gChatImage +
                ", gpParticipants=" + gpParticipants +
                '}';
    }
}
