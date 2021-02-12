package jets.chatserver.DBModels;

public class DBFriend {

    String friendId;
    String friendName;
    String friendReqDate;

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

    public String getFriendReqDate() {
        return friendReqDate;
    }

    public void setFriendReqDate(String friendReqDate) {
        this.friendReqDate = friendReqDate;
    }
}
