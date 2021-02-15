package commons.sharedmodels;

import java.io.Serializable;

public class FriendDto implements Serializable {

    private String friendId;
    private String friendName;
    private String friendImage;


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

    public String getFriendImage() {
        return friendImage;
    }

    public void setFriendImage(String friendImage) {
        this.friendImage = friendImage;
    }


    @Override
    public String toString() {
        return "FriendDto{" +
                "friendId='" + friendId + '\'' +
                ", friendName='" + friendName + '\'' +
                ", friendImage='" + "friendImage" + '\'' +
                '}';
    }
}
