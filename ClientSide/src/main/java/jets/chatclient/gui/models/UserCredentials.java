package jets.chatclient.gui.models;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserCredentials {

    private String userPhone;
    private String encryptedPassword;

     public UserCredentials(){}
    public UserCredentials(String userPhone, String userEncryptedPassword){
        this.userPhone =userPhone;
        this.encryptedPassword=userEncryptedPassword;
    }
    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public String getUserPhone() {
        return userPhone;
    }
}
