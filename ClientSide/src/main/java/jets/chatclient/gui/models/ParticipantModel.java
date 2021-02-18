package jets.chatclient.gui.models;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class ParticipantModel {

    private String participantId;
    private String participantName;
    private Circle participantImg;
    private Image participantImage;


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

    public Circle getParticipantImg() {
        return participantImg;
    }

    public void setParticipantImg(Circle participantImg) {
        this.participantImg = participantImg;
    }

    public Image getParticipantImage() {
        return participantImage;
    }

    public void setParticipantImage(Image participantImage) {
        this.participantImage = participantImage;
        participantImg.setFill(new ImagePattern(participantImage));
    }

    public  void bindToParticipantImg(Circle c){
        c.fillProperty().bind(this.participantImg.fillProperty());
    }


    @Override
    public String toString() {
        return "ParticipantModel{" +
                "participantId='" + participantId + '\'' +
                ", participantName='" + participantName + '\'' +
                ", participantImg=" + participantImg +
                ", participantImage=" + participantImage +
                '}';
    }
}
