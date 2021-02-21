package jets.chatclient.gui.models;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class ContactModel {

    private String contactId;
    private String contactName;
    private Image contactImage;
    private Circle contactAvatar = new Circle();
    private Circle bindableStatus = new Circle();



    private  int contactAvail;
    private  int contactStatus;

    public Circle getBindableStatus() {
        return bindableStatus;
    }

    public void setBindableStatus(Circle bindableStatus) {
        this.bindableStatus = bindableStatus;
    }

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

    public Image getContactImage() {
        return contactImage;
    }

    public void setContactImage(Image contactImage) {
        this.contactImage = contactImage;
        contactAvatar.setFill(new ImagePattern(contactImage));
    }

    public Circle getContactAvatar() {
        return contactAvatar;
    }

    public void setContactAvatar(Circle contactAvatar) {
        this.contactAvatar = contactAvatar;
    }

    public int getContactAvail() {
        return contactAvail;
    }

    public void setContactAvail(int contactAvail) {
        this.contactAvail = contactAvail;
        if(contactAvail == 0){
            bindableStatus.setFill(Paint.valueOf("#a6a3a2"));
        }
    }

    public int getContactStatus() {
        return contactStatus;
    }

    public void setContactStatus(int contactStatus) {
        this.contactStatus = contactStatus;

        if(contactAvail == 0){
            bindableStatus.setFill(Paint.valueOf("#a6a3a2"));
            return;
        }
        switch (contactStatus){
            case 1:
                bindableStatus.setFill(Paint.valueOf("#14de4a"));
                break;
            case 2:
                bindableStatus.setFill(Paint.valueOf("#de1414"));
                break;
            case 3:
                bindableStatus.setFill(Paint.valueOf("#f59a40"));
                break;
        }
    }


    @Override
    public String toString() {
        return "ContactModel{" +
                "contactId='" + contactId + '\'' +
                ", contactName='" + contactName + '\'' +
                ", contactImage=" + contactImage +
                ", contactAvatar=" + contactAvatar +
                ", bindableStatus=" + bindableStatus +
                ", contactAvail=" + contactAvail +
                ", contactStatus=" + contactStatus +
                '}';
    }
}
