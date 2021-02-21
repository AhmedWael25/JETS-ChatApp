package jets.chatclient.gui.models;

import javafx.beans.property.*;
import javafx.scene.image.Image;

import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class CurrentUserModel {

    private StringProperty displayName = new SimpleStringProperty();
    //TODO handle password availability,shouldn't be stored here
    private StringProperty phoneNumber = new SimpleStringProperty();
    //    private StringProperty displayName = new SimpleStringProperty();
    private StringProperty emailAddress = new SimpleStringProperty();
    private ObjectProperty<Image> image = new SimpleObjectProperty<Image>();
    private StringProperty gender = new SimpleStringProperty();
    private StringProperty country = new SimpleStringProperty();
    private StringProperty birthdayDate = new SimpleStringProperty();
    private StringProperty bio = new SimpleStringProperty();


    private SimpleIntegerProperty availability = new SimpleIntegerProperty();
    private SimpleIntegerProperty status = new SimpleIntegerProperty();

    private Circle userStatus = new Circle();
    private Circle userAvatar = new Circle();

    public Integer getAvailability() {
        return availability.get();
    }

    public void setAvailability(Integer availability) {
        this.availability.set(availability);
    }

    public IntegerProperty availabilityProperty() {
        return availability;
    }

    public Integer getStatus() {
        return status.get();
    }

    public void setStatus(Integer status) {

        switch (status){
            case 1:
                userStatus.setFill(Paint.valueOf("#14de4a"));
                break;
            case 2:
                userStatus.setFill(Paint.valueOf("#de1414"));
                break;
            case 3:
                userStatus.setFill(Paint.valueOf("#f59a40"));
                break;

        }
        this.status.set(status);

    }

    public IntegerProperty statusProperty() {
        return status;
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

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
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
        this.userAvatar.setFill(new ImagePattern(image));
    }


    public String getBirthdayDate() {
        return birthdayDate.get();
    }

    public StringProperty birthdayDateProperty() {
        return birthdayDate;
    }

    public void setBirthdayDate(String birthdayDate) {
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



    public void bindToUserAvatar(Circle circle){
        circle.fillProperty().bind(userAvatar.fillProperty());
    }
    public void  bindToUserStatus(Circle c){
        c.fillProperty().bind(userStatus.fillProperty());
    }

    @Override
    public String toString() {
        return "CurrentUserModel{" +
                "displayName=" + displayName +
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


    public void clear() {
        this.setPhoneNumber("");


    }
}
