package commons.sharedmodels;

public class P2PChatDto {

    private int chatId;
    private String friendId;
    private String friendName;
    private String friendImg;
    private String chatStartDate;

    public String getChatStartDate() {
        return chatStartDate;
    }

    public void setChatStartDate(String chatStartDate) {
        this.chatStartDate = chatStartDate;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
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

    @Override
    public String toString() {
        return "P2PChatDto{" +
                "chatId=" + chatId +
                ", friendId='" + friendId + '\'' +
                ", friendName='" + friendName + '\'' +
                ", friendImg='" + "IMG" + '\'' +
                '}';
    }
}
