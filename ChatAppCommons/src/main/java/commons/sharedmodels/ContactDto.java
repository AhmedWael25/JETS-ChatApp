package commons.sharedmodels;

import javafx.fxml.Initializable;

import java.io.Serializable;

public class ContactDto implements Serializable {

    private String contactId;
    private String contactName;
    private String contactImg;
    private int contactAvail;
    private int contactStatus;


    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactImg() {
        return contactImg;
    }

    public void setContactImg(String contactImg) {
        this.contactImg = contactImg;
    }

    public int getContactAvail() {
        return contactAvail;
    }

    public void setContactAvail(int contactAvail) {
        this.contactAvail = contactAvail;
    }

    public int getContactStatus() {
        return contactStatus;
    }

    public void setContactStatus(int contactStatus) {
        this.contactStatus = contactStatus;
    }


    @Override
    public String toString() {
        return "ContactDto{" +
                "contactId='" + contactId + '\'' +
                ", contactName='" + contactName + '\'' +
                ", contactImg='" + "contactImg" + '\'' +
                ", contactAvail=" + contactAvail +
                ", contactStatus=" + contactStatus +
                '}';
    }
}
