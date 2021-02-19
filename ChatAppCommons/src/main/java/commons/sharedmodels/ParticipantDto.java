package commons.sharedmodels;

import java.io.Serializable;

public class ParticipantDto implements Serializable {

    private String participantId;
    private String participantName;
    private String participantImage;
    private int participantStatus;

    public int getParticipantStatus() {
        return participantStatus;
    }

    public void setParticipantStatus(int participantStatus) {
        this.participantStatus = participantStatus;
    }

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public String getParticipantImage() {
        return participantImage;
    }

    public void setParticipantImage(String participantImage) {
        this.participantImage = participantImage;
    }

    @Override
    public String toString() {
        return "ParticipantDto{" +
                "participantId='" + participantId + '\'' +
                ", participantName='" + participantName + '\'' +
                ", participantImage='" + "participantImage" + '\'' +
                '}';
    }
}
