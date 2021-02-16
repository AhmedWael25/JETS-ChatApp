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
    private String password;
    private String dob;
    private int status;
    private int availability;

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

    public void setDataOfBirth(String dataOfBirth){
        this.dob = dataOfBirth;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){return password;}

    public String getDataOfBirth(){
        return this.dob;
    }

    public int getUserStatus(){ return this.status;}
    public int getUserAvailability(){ return this.availability;}

    public void setUserStatus(int status){this.status = status;}
    public void setUserAvailability(int availability){this.availability= availability;}



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
