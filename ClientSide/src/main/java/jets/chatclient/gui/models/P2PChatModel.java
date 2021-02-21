package jets.chatclient.gui.models;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class P2PChatModel {

    private int chatId;
    private int friendStatus;
    private int friendAvailability;
    private String friendName;
    private  String friendId;
    private String chatStartDate;
    private Image friendImg;
    Circle avatar = new Circle();
    Circle status = new Circle();

    public Circle getAvatar() {
        return avatar;
    }

    public void setAvatar(Circle avatar) {
        this.avatar = avatar;
    }

    public Circle getStatus() {
        return status;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public int getFriendStatus() {
        return friendStatus;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public void setFriendStatus(int friendStatus) {
        this.friendStatus = friendStatus;
        switch (friendStatus){
            case 1:
                status.setFill(Paint.valueOf("#14de4a"));
                break;
            case 2:
                status.setFill(Paint.valueOf("#de1414"));
                break;
            case 3:
                status.setFill(Paint.valueOf("#f59a40"));
                break;
        }
    }

    public int getFriendAvailability() {
        return friendAvailability;
    }

    public void setFriendAvailability(int friendAvailability) {
        this.friendAvailability = friendAvailability;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getChatStartDate() {
        return chatStartDate;
    }

    public void setChatStartDate(String chatStartDate) {
        this.chatStartDate = chatStartDate;
    }

    public Image getFriendImg() {
        return friendImg;
    }

    public void setFriendImg(Image friendImg) {
        this.friendImg = friendImg;
        avatar.setFill(new ImagePattern(friendImg));
    }

    @Override
    public String toString() {
        return "P2PChatModel{" +
                "chatId=" + chatId +
                ", friendStatus=" + friendStatus +
                ", friendAvailability=" + friendAvailability +
                ", friendName='" + friendName + '\'' +
                ", chatStartDate='" + chatStartDate + '\'' +
                ", friendImg=" + friendImg +
                '}';
    }
}
