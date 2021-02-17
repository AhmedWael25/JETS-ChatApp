package commons.sharedmodels;

import java.io.Serializable;
import java.util.List;

public class GpChatUserDto implements Serializable {
    private String chatImage;
    private String chatName;
    private  String adminId;
    List<String> gpUserIds;


    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getChatImage() {
        return chatImage;
    }

    public void setChatImage(String chatImage) {
        this.chatImage = chatImage;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public List<String> getGpUserIds() {
        return gpUserIds;
    }

    public void setGpUserIds(List<String> gpUserIds) {
        this.gpUserIds = gpUserIds;
    }

    @Override
    public String toString() {
        return "GpChatUserDto{" +
                "chatImage='" + chatImage + '\'' +
                ", chatName='" + chatName + '\'' +
                ", gpUserIds=" + gpUserIds +
                '}';
    }
}
