package jets.chatclient.gui.models;


import javafx.beans.property.*;
import javafx.scene.image.Image;

import javafx.scene.paint.ImagePattern;

public class CurrentUserModel {

    private StringProperty displayName = new SimpleStringProperty();
    //TODO handle password availability,shouldn't be stored here
    private StringProperty phoneNumber = new SimpleStringProperty();
    private StringProperty emailAddress = new SimpleStringProperty();
    private ObjectProperty<Image> image = new SimpleObjectProperty<Image>();
    private StringProperty gender = new SimpleStringProperty();
    private StringProperty country = new SimpleStringProperty();
    private StringProperty birthdayDate = new SimpleStringProperty();
    private StringProperty bio = new SimpleStringProperty();
    private SimpleIntegerProperty availability = new SimpleIntegerProperty();
    private SimpleIntegerProperty status = new SimpleIntegerProperty();


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

    @Override
    public String toString() {
        return "CurrentUserModel{" +
                "userName=" + displayName +
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
