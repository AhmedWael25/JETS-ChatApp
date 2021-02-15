package commons.sharedmodels;

import java.io.Serializable;

public class CurrentUserDto implements Serializable {
    private String userPhone;
    private String userName;
    private String userGender;
    private String userEmail;
    private String userCountry;
    private String userBio;
    private String userImage;

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public String getUserBio() {
        return userBio;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }





    @Override
    public String toString() {
        return "CurrentUserDto{" +
                "userId='" + userPhone + '\'' +
                ", userName='" + userName + '\'' +
                ", userGender='" + userGender + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userCountry='" + userCountry + '\'' +
                ", userBio='" + userBio + '\'' +
                ", userImage='" + userImage + '\'' +
                '}';
    }
}
