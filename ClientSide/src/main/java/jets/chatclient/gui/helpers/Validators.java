package jets.chatclient.gui.helpers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

public class Validators {


    public static JFXTextField addNameValidator(JFXTextField field) {
        final String displayNameRegex = "^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";
        final String invalidDisplayNameMsg = "name must be 8-20 valid letters !";
        RegexValidator dispalynameValidator = getRegexValidator(displayNameRegex, invalidDisplayNameMsg);
        field.getValidators().add(dispalynameValidator);
        field.focusedProperty().addListener((o, old, foucs) -> {
            field.textProperty().addListener((observable, oldValue, newValue) -> field.setText(newValue.length() > 20 ? newValue.substring(0, 20) : newValue));
            if (!foucs) {
                if (!field.validate()) {
                    field.textProperty().addListener((observable, oldValue, newValue) -> {
                        Boolean valid = field.validate();
                        if (valid)
                            field.setUnFocusColor(Paint.valueOf("green"));
                        else
                            field.setUnFocusColor(Paint.valueOf("red"));
                    });
                }
            }
        });
        return field;
    }

    public static JFXTextField addPhoneNumberValidator(JFXTextField field) {
        final String phoneNumberRegex = "^01[0125]\\d{8}$";
        final String invalidPhoneNumberMsg = "phoneNumber must be 11 digit valid phone number !";
        RegexValidator phoneNumberValidator = getRegexValidator(phoneNumberRegex, invalidPhoneNumberMsg);
        field.getValidators().add(phoneNumberValidator);
        field.focusedProperty().addListener((o, old, foucs) -> {
            field.textProperty().addListener((observable, oldValue, newValue) -> field.setText(newValue.length() > 20 ? newValue.substring(0, 20) : newValue));
            if (!foucs) {
                if (!field.validate()) {
                    field.textProperty().addListener((observable, oldValue, newValue) -> {
                         field.validate();
                    });
                }
            }
        });
        return field;
    }

    public static JFXPasswordField addPasswordValidator(JFXPasswordField field) {
        final String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        final String invalidPasswordMsg = "password must be 8-20 chars. !";
        RegexValidator passwordValidator = getRegexValidator(passwordRegex, invalidPasswordMsg);
        field.getValidators().add(passwordValidator);
        field.focusedProperty().addListener((o, old, foucs) -> {
            field.textProperty().addListener((observable, oldValue, newValue) -> field.setText(newValue.length() > 20 ? newValue.substring(0, 20) : newValue));
            if (!foucs) {
                if (!field.validate()) {
                    field.textProperty().addListener((observable, oldValue, newValue) -> field.validate());
                }
            }
        });
        return field;
    }


    public static RegexValidator getRegexValidator(String regex, String msg) {
        RegexValidator validator = new RegexValidator();
        FontIcon warnIcon = new FontIcon(FontAwesomeSolid.EXCLAMATION_TRIANGLE);
        warnIcon.getStyleClass().add("error");
        validator.setMessage(msg);
        validator.setIcon(warnIcon);
        validator.setRegexPattern(regex);
        return validator;
    }
}
