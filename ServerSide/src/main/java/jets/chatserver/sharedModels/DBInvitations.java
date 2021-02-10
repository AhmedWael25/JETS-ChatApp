package jets.chatserver.sharedModels;

import java.io.Serializable;

public class DBInvitations implements Serializable {

    private int invitationId;
    private String invitationData;
    private String senderId;
    private String receiverId;
    private String content;


    public int getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(int invitationId) {
        this.invitationId = invitationId;
    }

    public String getInvitationData() {
        return invitationData;
    }

    public void setInvitationData(String invitationData) {
        this.invitationData = invitationData;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    @Override
    public String toString() {
        return "DBInvitations{" +
                "invitationId=" + invitationId +
                ", invitationData='" + invitationData + '\'' +
                ", senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

}
