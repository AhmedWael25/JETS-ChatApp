package jets.chatclient.gui.models;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class ParticipantModel {

    private String participantId;
    private String participantName;
    private Circle participantImg = new Circle();
    private Image participantImage;
    private int participantStatus;

    public Circle getpStatus() {
        return pStatus;
    }

    public void setpStatus(Circle pStatus) {
        this.pStatus = pStatus;
    }

    private Circle pStatus = new Circle();


    public int getParticipantStatus() {
        return participantStatus;
    }

    public void setParticipantStatus(int status) {
        switch (status){
            case 0:
                pStatus.setFill(Paint.valueOf("#abaaa7"));
                break;
            case 1:
                pStatus.setFill(Paint.valueOf("#14de4a"));
                break;
            case 2:
                pStatus.setFill(Paint.valueOf("#de1414"));
                break;
            case 3:
                pStatus.setFill(Paint.valueOf("#f59a40"));
                break;
        }

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
