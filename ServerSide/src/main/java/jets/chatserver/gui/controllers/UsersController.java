package jets.chatserver.gui.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import commons.utils.ImageEncoderDecoder;
import commons.utils.Validators;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.StackPane;
import jets.chatserver.DBModels.DBUser;
import jets.chatserver.DBModels.UserData;
import jets.chatserver.database.dao.UserDao;
import jets.chatserver.database.daoImpl.UserDaoImpl;
import org.kordamp.ikonli.javafx.FontIcon;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;

public class UsersController implements Initializable {

    public StackPane root;
    public Label treeTableViewCount;

    public JFXTextField searchField;
    public JFXTreeTableView<Person> treeTableView;
    public JFXTreeTableColumn<Person, String> phoneEditableColumn;
    public JFXTreeTableColumn<Person, String> nameEditableColumn;
    public JFXTreeTableColumn<Person, String> genderEditableColumn;
    public JFXTreeTableColumn<Person, String> emailEditableColumn;
    public JFXTreeTableColumn<Person, String> countryEditableColumn;
    @FXML
    public JFXTextField userPhoneNum;

    private static final String PREFIX = "( ";
    private static final String POSTFIX = " )";
    public FontIcon ftPhone;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTableView();
    }
  //  Validators.addPhoneNumberValidator(userPhoneNum,ftPhone);
    private <T> void setupCellValueFactory(JFXTreeTableColumn<Person, T> column, Function<Person, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<Person, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }


    private void setupTableView() {
        setupCellValueFactory(phoneEditableColumn, Person::phoneProperty);
        setupCellValueFactory(nameEditableColumn, Person::nameProperty);
        setupCellValueFactory(genderEditableColumn, Person::genderProperty);
        setupCellValueFactory(emailEditableColumn, Person::emailProperty);
        setupCellValueFactory(countryEditableColumn, Person::countryProperty);


        ObservableList<Person> generatedData = generateData(30);

        treeTableView.setRoot(new RecursiveTreeItem<>(generatedData, RecursiveTreeObject::getChildren));

        treeTableView.setShowRoot(false);
        treeTableViewCount.textProperty()
                .bind(Bindings.createStringBinding(() -> PREFIX + treeTableView.getCurrentItemsCount() + POSTFIX,
                        treeTableView.currentItemsCountProperty()));

        searchField.textProperty().addListener(setupSearchField(treeTableView));
    }

    private ChangeListener<String> setupSearchField(final JFXTreeTableView<UsersController.Person> tableView) {
        return (o, oldVal, newVal) ->
                tableView.setPredicate(personProp -> {
                    final Person person = personProp.getValue();
                    return person.phone.get().toLowerCase().contains(newVal.toLowerCase())
                            || person.name.get().toLowerCase().contains(newVal.toLowerCase())
                            || person.gender.get().toLowerCase().contains(newVal.toLowerCase())
                            || person.country.get().toLowerCase().contains(newVal.toLowerCase())
                            || person.email.get().toLowerCase().contains(newVal.toLowerCase());
                });
    }

    private ObservableList<Person> generateData( int numberOfEntries) {
        List<UserData> data= new ArrayList<>();
        try {
            data = UserDaoImpl.getUserDaoInstance().getUsersforDashBoard();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        numberOfEntries=  numberOfEntries>data.size()?data.size():numberOfEntries;
        final ObservableList<Person> dummyData = FXCollections.observableArrayList();
        for (int i = 0; i < numberOfEntries; i++) {
            dummyData.add(getPerson(data.get(i)) );
        }
        return dummyData;
    }
     private Person getPerson(UserData u){
       return new Person(u.getPhone(), u.getName(), u.getGender(), u.getEmail(), u.getCountry()) ;

     }

    public void addUser(ActionEvent actionEvent) throws SQLException {
        if(!userPhoneNum.validate()){
            return;
        }
        userPhoneNum.clear();
        String userDefaultImage = "";
        ImageEncoderDecoder imageEncoderDecoder = new ImageEncoderDecoder();
        try {
            File f = new File(getClass().getResource("/images/userDefaultImage.png").getPath());
            userDefaultImage = imageEncoderDecoder.getEncodedImage(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DBUser dbUser = new DBUser();
        UserDao userDao = UserDaoImpl.getUserDaoInstance();
        if(!userDao.isUserExist(userPhoneNum.getText())){
            dbUser.setPhone(userPhoneNum.getText());
            dbUser.setPassword("");
            dbUser.setDisplayedName("");
            dbUser.setCountry("");
            dbUser.setImgEncoded(userDefaultImage);
            dbUser.setGender("");
            userDao.addUser(dbUser);
            System.out.println("new user added by admin");

        }
        else{
            System.out.println("user already exist");

    } }


    static final class Person extends RecursiveTreeObject<Person> {

        final StringProperty phone;
        final StringProperty name;
        final StringProperty gender;
        final StringProperty email;
        final StringProperty country;

        Person(String phone, String name, String gender, String email, String country) {
            this.phone = new SimpleStringProperty(phone);
            this.name = new SimpleStringProperty(name);
            this.gender = new SimpleStringProperty(gender);
            this.email = new SimpleStringProperty(email);
            this.country = new SimpleStringProperty(country);

        }

        StringProperty phoneProperty() {
            return phone;
        }

        StringProperty nameProperty() {
            return name;
        }

        StringProperty genderProperty() {
            return gender;
        }

        StringProperty emailProperty() {
            return email;
        }

        StringProperty countryProperty() {
            return country;
        }

    }

}
