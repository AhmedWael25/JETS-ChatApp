package jets.chatclient.gui.models;

import javafx.scene.image.Image;

public class FriendModel {
    private String friendId;
    private String friendName;
    private Image friendImg;


    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public Image getFriendImg() {
        return friendImg;
    }

    public void setFriendImg(Image friendImg) {
        this.friendImg = friendImg;
    }

    @Override
    public String toString() {
        return "FriendModel{" +
                "friendId='" + friendId + '\'' +
                ", friendName='" + friendName + '\'' +
                ", friendImg=" + "friendImg" +
                '}';
    }
}
