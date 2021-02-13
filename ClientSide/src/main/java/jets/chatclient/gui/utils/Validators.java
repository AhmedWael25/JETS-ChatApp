package jets.chatclient.gui.utils;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

public class Validators {

    //acceptable name form {8-20 chars, may contain _,. but not folowing, no Spaces allowed}
    final static String displayNameRegex = "^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
    final static String invalidDisplayNameMsg = "name must be 8-20 valid letters !";

    //acceptable phone form {11 numbers, starts with 01, followed by 0/1/2/5}
    //should be changed
    final static String phoneNumberRegex = "^01[0125]\\d{8}$";
    final static String invalidPhoneNumberMsg = "phoneNumber must be 11 digit valid phone number !";

    //acceptable password form {8 chars at least, contains uppercase,lower case,number, and special char }
    final static String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
    final static String invalidPasswordMsg = "password must be 8-20 chars. !";

    //required Msg
    final static String requiredMsg = "Required";


    public static JFXTextField addNameValidator(JFXTextField field, FontIcon fi) {
        RegexValidator dispalynameValidator = getRegexValidator(displayNameRegex, invalidDisplayNameMsg);
        field.getValidators().add(dispalynameValidator);
        field.focusedProperty().addListener((o, old, foucs) -> {
//            field.textProperty().addListener((observable, oldValue, newValue) -> field.setText(newValue.length() > 20 ? newValue.substring(0, 20) : newValue));
//            System.out.println( foucs);
//            System.out.println(old);
//            System.out.println("fcolor " + field.getFocusColor());
//            System.out.println("unf clolr " + field.getUnFocusColor());
//            System.out.println("icon clolor " + fi.getIconColor());
            if (!foucs){
                field.validate();
                new Thread(()->{
                    try {
                        //TODO handle this configration to all other Validators
                        //TODO separate Validatio with thread thing
                        //TODO return Color Binding to its class
                        //TODO wirte onTextChange methods again(check related performance)
                        //TODO try logo moving thing using position (SHERBO)
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(()->fi.setIconColor( field.getUnFocusColor()));
                }).start();
//                fi.setIconColor( field.getUnFocusColor());
            }
            else {
                fi.setIconColor( field.getFocusColor());
            }
//            System.out.println("fcolor " + field.getTextFormatter());
//            System.out.println("unf clolr " + field.getUnFocusColor());
//            System.out.println("icon clolor " + fi.getIconColor());


        });
        return field;
    }

    public static JFXTextField addPhoneNumberValidator(JFXTextField field) {

        RegexValidator phoneNumberValidator = getRegexValidator(phoneNumberRegex, invalidPhoneNumberMsg);
        field.getValidators().add(phoneNumberValidator);
        field.focusedProperty().addListener((o, old, foucs) -> {
            field.textProperty().addListener((observable, oldValue, newValue) -> field.setText(newValue.length() > 20 ? newValue.substring(0, 20) : newValue));
            if (!foucs) field.validate();
        });
        return field;
    }

    public static JFXPasswordField addPasswordValidator(JFXPasswordField field) {

        RegexValidator passwordValidator = getRegexValidator(passwordRegex, invalidPasswordMsg);
        field.getValidators().add(passwordValidator);
        field.focusedProperty().addListener((o, old, foucs) -> {
            field.textProperty().addListener((observable, oldValue, newValue) -> field.setText(newValue.length() > 20 ? newValue.substring(0, 20) : newValue));
            if (!foucs) field.validate();
        });
        return field;
    }

    //    public static void addRequiredValidator(JFXComboBox field){
//        RequiredFieldValidator validator = getRequiredValidator(requiredMsg);
//        field.getValidators().add(validator);
//        field.focusedProperty().addListener((observable, old, focus) ->field.validate());
//    }
    public static void addRequiredValidator(Parent field) {
        RequiredFieldValidator validator = getRequiredValidator(requiredMsg);

        if (field instanceof JFXComboBox) {
            ((JFXComboBox<?>) field).getValidators().add(validator);
            field.focusedProperty().addListener((observable, old, focus) -> ((JFXComboBox<?>) field).validate());
        }
        if (field instanceof JFXDatePicker) {
            ((JFXDatePicker) field).getValidators().add(validator);
            field.focusedProperty().addListener((observable, old, focus) -> ((JFXDatePicker) field).validate());
        }
        if (field instanceof JFXDatePicker) {
            ((JFXDatePicker) field).getValidators().add(validator);
            field.focusedProperty().addListener((observable, old, focus) -> ((JFXDatePicker) field).validate());
        }

    }


//    public static void bindValidation(Parent parent){
//        if (parent instanceof JFXTextField)
//             JFXTextField field= (JFXTextField)parent;
//
//    }


    public static RegexValidator getRegexValidator(String regex, String msg) {
        RegexValidator validator = new RegexValidator();
        FontIcon warnIcon = new FontIcon(FontAwesomeSolid.EXCLAMATION_TRIANGLE);
        warnIcon.getStyleClass().add("error");
        validator.setMessage(msg);
        validator.setIcon(warnIcon);
        validator.setRegexPattern(regex);
        return validator;
    }

    public static RequiredFieldValidator getRequiredValidator(String msg) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        FontIcon warnIcon = new FontIcon(FontAwesomeSolid.EXCLAMATION_TRIANGLE);
        warnIcon.getStyleClass().add("error");
        validator.setMessage(msg);
        validator.setIcon(warnIcon);
        return validator;
    }
}
