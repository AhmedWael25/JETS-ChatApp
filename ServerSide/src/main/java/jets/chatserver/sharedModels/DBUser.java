package jets.chatserver.sharedModels;

import java.io.Serializable;

enum UserAvail{ONLINE, OFFLINE};
enum UserStatus{FREE,BUSY,AWAY}

public class DBUser implements Serializable {
    private String phone;
    private String displayedName;
    private int id;
    private String gender;
    private String password;
    private String email;
    private String country;
    private String dob;
    private String bio;
    private String registerData;
    private String imgEncoded;
    private UserStatus userStatus ;
    private UserAvail userAvail;

    public DBUser() {}

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDisplayedName() {
        return displayedName;
    }

    public void setDisplayedName(String displayedName) {
        this.displayedName = displayedName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getRegisterData() {
        return registerData;
    }

    public void setRegisterData(String registerData) {
        this.registerData = registerData;
    }

    public String getImgEncoded() {
        return imgEncoded;
    }

    public void setImgEncoded(String imgEncoded) {
        this.imgEncoded = imgEncoded;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public UserAvail getUserAvail() {
        return userAvail;
    }

    public void setUserAvail(UserAvail userAvail) {
        this.userAvail = userAvail;
    }

    @Override
    public String toString() {
        return "DBUser{" +
                "phone='" + phone + '\'' +
                ", displayedName='" + displayedName + '\'' +
                ", id=" + id +
                ", gender='" + gender + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                ", dob='" + dob + '\'' +
                ", bio='" + bio + '\'' +
                ", registerData='" + registerData + '\'' +
                ", imgEncoded='" + imgEncoded + '\'' +
                ", userStatus=" + userStatus +
                ", userAvail=" + userAvail +
                '}';
    }
}
