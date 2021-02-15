package commons.sharedmodels;

import java.io.Serializable;
import java.util.List;

public class GpChatDto implements Serializable {

    private int gpChatId;
    private String gpChatName;
    private String gpChatImage;
    private String grpChatAdminId;
    private String grpChatDesc;
    private String gpChatStartDate;
    private List<ParticipantDto> gpParticipants;

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

    public String getGpChatImage() {
        return gpChatImage;
    }

    public void setGpChatImage(String gpChatImage) {
        this.gpChatImage = gpChatImage;
    }

    public String getGrpChatAdminId() {
        return grpChatAdminId;
    }

    public void setGrpChatAdminId(String grpChatAdminId) {
        this.grpChatAdminId = grpChatAdminId;
    }

    public String getGrpChatDesc() {
        return grpChatDesc;
    }

    public void setGrpChatDesc(String grpChatDesc) {
        this.grpChatDesc = grpChatDesc;
    }

    public String getGpChatStartDate() {
        return gpChatStartDate;
    }

    public void setGpChatStartDate(String gpChatStartDate) {
        this.gpChatStartDate = gpChatStartDate;
    }

    public List<ParticipantDto> getGpParticipants() {
        return gpParticipants;
    }

    public void setGpParticipants(List<ParticipantDto> gpParticipants) {
        this.gpParticipants = gpParticipants;
    }


    @Override
    public String toString() {
        return "GpChatDto{" +
                "gpChatId=" + gpChatId +
                ", gpChatName='" + gpChatName + '\'' +
                ", gpChatImage='" + "IMG" + '\'' +
                ", grpChatAdminId='" + grpChatAdminId + '\'' +
                ", grpChatDesc='" + grpChatDesc + '\'' +
                ", gpChatStartDate='" + gpChatStartDate + '\'' +
                ", gpParticipants=" + gpParticipants +
                '}';
    }
}
