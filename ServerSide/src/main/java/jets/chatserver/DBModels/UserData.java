package jets.chatserver.DBModels;

public class UserData {

    String phone;
    String name;
    String gender;
    String email;
    String country;

    public UserData(String phone, String name, String gender, String email, String country) {
        this.phone = phone;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.country = country;
    }

    public UserData() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

}
