package jets.chatclient.gui.utils;

import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import org.kordamp.ikonli.javafx.FontIcon;

public class ColorBinder {
    public static void BindColor(JFXTextField field, FontIcon icon) {
        Platform.runLater(() -> field.focusedProperty().addListener((o, old, foucs) -> icon.setIconColor(foucs ? field.getFocusColor() : field.getUnFocusColor())));


    }

    public static void BindColor(JFXPasswordField field, FontIcon icon) {
        field.focusedProperty().addListener((o, old, foucs) -> icon.setIconColor(foucs ? field.getFocusColor() : field.getUnFocusColor()));
    }

    public static void BindColor(JFXComboBox field, FontIcon icon) {
        field.focusedProperty().addListener((o, old, foucs) -> icon.setIconColor(foucs ? field.getFocusColor() : field.getUnFocusColor()));
    }

    //radio button color binder, can't be handled in the easy way
    public static void BindColor(JFXRadioButton field, FontIcon icon) {
        field.focusedProperty().addListener((o, old, foucs) -> icon.setIconColor(foucs ? field.getSelectedColor() : field.getUnSelectedColor()));
    }

    //datePicker color binder, can't be handled in the easy way
    public static void BindColor(JFXDatePicker field, FontIcon icon) {
        field.focusedProperty().addListener((o, old, foucs) -> icon.setIconColor(foucs ? field.getDefaultColor() : field.getDefaultColor()));
    }

}
