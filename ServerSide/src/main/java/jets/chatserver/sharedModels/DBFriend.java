package jets.chatserver.sharedModels;

public class DBFriend {

    String friendId;
    String friendName;
    String friendImgEncoded;
//    String befriendedData;


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

    public String getFriendImgEncoded() {
        return friendImgEncoded;
    }

    public void setFriendImgEncoded(String friendImgEncoded) {
        this.friendImgEncoded = friendImgEncoded;
    }
}
