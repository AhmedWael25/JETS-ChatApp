package commons.sharedmodels;

import java.io.Serializable;

public class P2PChatDto implements Serializable {

    private int chatId;
    private String userId;
    private String friendId;
    private String friendName;
    private String friendImg;
    private String chatStartDate;
    private int availability;
    private int status;

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public String getFriendImg() {
        return friendImg;
    }

    public void setFriendImg(String friendImg) {
        this.friendImg = friendImg;
    }

    public String getChatStartDate() {
        return chatStartDate;
    }

    public void setChatStartDate(String chatStartDate) {
        this.chatStartDate = chatStartDate;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "P2PChatDto{" +
                "chatId=" + chatId +
                ", userId='" + userId + '\'' +
                ", friendId='" + friendId + '\'' +
                ", friendName='" + friendName + '\'' +
                ", friendImg='" + "IMG" + '\'' +
                ", chatStartDate='" + chatStartDate + '\'' +
                ", availability=" + availability +
                ", status=" + status +
                '}';
    }
}
