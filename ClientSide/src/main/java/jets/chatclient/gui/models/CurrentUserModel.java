package jets.chatclient.gui.models;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class CurrentUserModel {

    private StringProperty userName = new SimpleStringProperty();
    //TODO handle password availability,shouldn't be stored here
    private StringProperty phoneNumber = new SimpleStringProperty();
    private StringProperty displayName = new SimpleStringProperty();
    private StringProperty emailAddress = new SimpleStringProperty();
    private ObjectProperty<Image> image = new SimpleObjectProperty<Image>();
    private StringProperty gender = new SimpleStringProperty();
    private StringProperty country = new SimpleStringProperty();
    private ObjectProperty<LocalDate> birthdayDate = new SimpleObjectProperty<LocalDate>();
    private StringProperty bio = new SimpleStringProperty();



//    private static CurrentUserModel currentUser_instance = null;
//
//    private CurrentUserModel(){}
//
//    public static CurrentUserModel getInstance(){
//        if (currentUser_instance == null)
//            currentUser_instance = new CurrentUserModel();
//        return currentUser_instance;
//    }


    public String getUserName() {
        return userName.get();
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public String getDisplayName() {
        return displayName.get();
    }

    public StringProperty displayNameProperty() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName.set(displayName);
    }

    public String getEmailAddress() {
        return emailAddress.get();
    }

    public StringProperty emailAddressProperty() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress.set(emailAddress);
    }


    public String getGender() {
        return gender.get();
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public String getCountry() {
        return country.get();
    }

    public StringProperty countryProperty() {
        return country;
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public Image getImage() {
        return image.get();
    }

    public ObjectProperty<Image> imageProperty() {
        return image;
    }

    public void setImage(Image image) {
        this.image.set(image);
    }

    public ObjectProperty<LocalDate> birthdayDateProperty() {
        return birthdayDate;
    }

    public LocalDate getBirthdayDate() {
        return birthdayDate.get();
    }

    public void setBirthdayDate(LocalDate birthdayDate) {
        this.birthdayDate.set(birthdayDate);
    }


    public String getBio() {
        return bio.get();
    }

    public StringProperty bioProperty() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio.set(bio);
    }

    @Override
    public String toString() {
        return "CurrentUserModel{" +
                "userName=" + userName +
                ", phoneNumber=" + phoneNumber +
                ", displayName=" + displayName +
                ", emailAddress=" + emailAddress +
                ", image=" + image +
                ", gender=" + gender +
                ", country=" + country +
                ", birthdayDate=" + birthdayDate +
                ", bio=" + bio +
                '}';
    }

}
