package commons.utils;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.application.Platform;
import javafx.scene.Parent;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

public class Validators {
    //TODO Make mail Validator
    //TODO Bind validation to Button OnAction
    //acceptable name form {8-20 chars, may contain _,. but not folowing, no Spaces allowed}
    final static String displayNameRegex = "^(?=.{8,20}$)(?![_. ])(?!.*[_. ]{2})[a-zA-Z0-9 ._]+(?<![_. ])$";
    final static String invalidDisplayNameMsg = "name must be 8-20 valid letters !";

    //acceptable phone form {11 numbers, starts with 01, followed by 0/1/2/5}
    //should be changed
    final static String phoneNumberRegex = "^01[0125]\\d{8}$";
    final static String invalidPhoneNumberMsg = "phoneNumber format is 01********* !";

    //acceptable password form {8 chars at least, contains uppercase,lower case,number, and special char }
    final static String passwordRegex = "^(?=.*[a-zA-z0-9@#$%^&+=])(?=\\S*$).{8,}$";
    final static String invalidPasswordMsg = "password must be 8 chars at least !";



    //Email regex
    final static String emailRegex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    final static String invalidEmailMsg = "Enter a valid Email. !";

    //required Msg
    final static String requiredMsg = "Required";



    final static String IPAddressRegex = "(\\d{1,2}|(0|1)\\d{2}|2[0-4]\\d|25[0-5])" + "\\." +
                            "(\\d{1,2}|(0|1)\\d{2}|2[0-4]\\d|25[0-5])" + "\\." +
                            "(\\d{1,2}|(0|1)\\d{2}|2[0-4]\\d|25[0-5])" + "\\." +
                            "(\\d{1,2}|(0|1)\\d{2}|2[0-4]\\d|25[0-5])";

    final static String invalidIPAddressMsg = "Enter a valid IP!";


//TODO handle this shit
    public static void buttonValidate(JFXButton btn, Parent... fields) {
        btn.setDisable(true);
        for (Parent field : fields) {
            if (field instanceof JFXComboBox) {
                btn.setDisable(((JFXComboBox<String>) field).validate() ? false : true);
            }
            if (field instanceof JFXDatePicker) {
                btn.setDisable(((JFXDatePicker) field).validate() ? false : true);
            }
            if (field instanceof JFXTextField) {
                btn.setDisable(((JFXTextField) field).validate() ? false : true);
            }
            if (field instanceof JFXPasswordField) {
                btn.setDisable(((JFXPasswordField) field).validate() ? false : true);
            }
        }
    }


    public static JFXTextField addNameValidator(JFXTextField field, FontIcon fi) {
        RegexValidator dispalynameValidator = getRegexValidator(displayNameRegex, invalidDisplayNameMsg);
        field.getValidators().add(dispalynameValidator);

        bindValidation(field, fi);


        return field;
    }

    public static JFXTextField addPhoneNumberValidator(JFXTextField field, FontIcon fi) {

        RegexValidator phoneNumberValidator = getRegexValidator(phoneNumberRegex, invalidPhoneNumberMsg);
        field.getValidators().add(phoneNumberValidator);
        bindValidation(field, fi);
        return field;
    }
    //



    public static JFXPasswordField addPasswordValidator(JFXPasswordField field, FontIcon fi) {

        RegexValidator passwordValidator = getRegexValidator(passwordRegex, invalidPasswordMsg);
        field.getValidators().add(passwordValidator);
        bindValidation(field, fi);
        return field;
    }

    public static JFXTextField addEmailValidator(JFXTextField field, FontIcon fi) {
        RegexValidator dispalynameValidator = getRegexValidator(emailRegex, invalidEmailMsg);
        field.getValidators().add(dispalynameValidator);

        bindValidation(field, fi);


        return field;
    }

    public static JFXTextField addIPlValidator(JFXTextField field, FontIcon fi) {
        RegexValidator dispalynameValidator = getRegexValidator(IPAddressRegex, invalidIPAddressMsg);
        field.getValidators().add(dispalynameValidator);

        bindValidation(field, fi);

        return field;
    }



    public static void addRequiredValidator(Parent field, FontIcon fi) {
        RequiredFieldValidator validator = getRequiredValidator(requiredMsg);

        if (field instanceof JFXComboBox) {
//            ((JFXComboBox<Label>) field).getValidators().add(validator);
            ((JFXComboBox<Label>) field).setValidators(validator);
            bindValidation(field, fi);

        }
        if (field instanceof JFXDatePicker) {
            ((JFXDatePicker) field).getValidators().add(validator);
            bindValidation(field, fi);
        }
        if (field instanceof JFXTextField) {
            ((JFXTextField) field).getValidators().add(validator);
            bindValidation(field, fi);
        }
        if (field instanceof JFXPasswordField) {
            ((JFXPasswordField) field).getValidators().add(validator);
            bindValidation(field, fi);
        }

    }
    //TODO handle color binding of date picker and comboBox

    public static void bindValidation(Parent field, FontIcon fi) {
        if (field instanceof JFXTextField)
            field.focusedProperty().addListener((o, old, foucs) -> {
                if (!foucs) {
                    ((JFXTextField) field).validate();
                    new Thread(() -> {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Platform.runLater(() -> fi.setIconColor(((JFXTextField) field).getUnFocusColor()));
                    }).start();
                } else {
                    fi.setIconColor(((JFXTextField) field).getFocusColor());
                }
            });
        if (field instanceof JFXPasswordField)
            field.focusedProperty().addListener((o, old, foucs) -> {
                if (!foucs) {
                    ((JFXPasswordField) field).validate();
                    new Thread(() -> {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Platform.runLater(() -> fi.setIconColor(((JFXPasswordField) field).getUnFocusColor()));
                    }).start();
                } else {
                    fi.setIconColor(((JFXPasswordField) field).getFocusColor());
                }
            });
        if (field instanceof JFXComboBox) {
            field.focusedProperty().addListener((o, old, foucs) -> {
                if (!foucs) {
                    ((JFXComboBox) field).validate();
                    new Thread(() -> {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Platform.runLater(() -> fi.setIconColor(((JFXComboBox) field).getUnFocusColor()));
                    }).start();
                } else {
                    fi.setIconColor(((JFXComboBox) field).getFocusColor());
                }
            });
            System.out.println("Hello, you reached mile stone 1");
        }
        if (field instanceof JFXDatePicker)
            field.focusedProperty().addListener((o, old, foucs) -> {
                if (!foucs) {
                    ((JFXDatePicker) field).validate();
                    new Thread(() -> {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        fi.setIconColor(new Color(0.388, 0.419, 0.38, 1.0));
//                        Platform.runLater(() -> fi.setIconColor(((JFXDatePicker) field).getDefaultColor()));
                    }).start();
                } else {
                    Platform.runLater(() -> fi.setIconColor(((JFXDatePicker) field).getDefaultColor()));
//                    fi.setIconColor(new Color(0.388, 0.419, 0.38, 1.0));
                }
            });

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

    public static RequiredFieldValidator getRequiredValidator(String msg) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        FontIcon warnIcon = new FontIcon(FontAwesomeSolid.EXCLAMATION_TRIANGLE);
        warnIcon.getStyleClass().add("error");
        validator.setMessage(msg);
        validator.setIcon(warnIcon);
        return validator;
    }
}
