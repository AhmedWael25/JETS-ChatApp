package commons.utils;

import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXComboBox;
import javafx.scene.control.TextField;

import java.util.List;

public class ComboBoxUtils {
    public static void fillComboBox(JFXComboBox<String> comboBox , List<String> values){

        values.stream().forEach(o -> comboBox.getItems().add(o));
        comboBox.setEditable(true);

    }

    public static void makeComboSearchable(JFXComboBox<String> comboBox){
        JFXAutoCompletePopup<String> autoCompletePopup = new JFXAutoCompletePopup<>();
        autoCompletePopup.getSuggestions().addAll(comboBox.getItems());
        autoCompletePopup.hide();

//SelectionHandler sets the value of the comboBox
        autoCompletePopup.setSelectionHandler(event -> {
            comboBox.setValue(event.getObject());
        });

        TextField editor = comboBox.getEditor();
        editor.focusedProperty().addListener((observable, oldValue, focus) ->

        editor.textProperty().addListener(o -> {
            //The filter method uses the Predicate to filter the Suggestions defined above
            //I choose to use the contains method while ignoring cases
            autoCompletePopup.filter(item -> item.toLowerCase().startsWith(editor.getText().toLowerCase()));
            //Hide the autocomplete popup if the filtered suggestions is empty or when the box's original popup is open
            if (autoCompletePopup.getFilteredSuggestions().isEmpty() || comboBox.showingProperty().get()) {
                autoCompletePopup.hide();
            }
            else {
                autoCompletePopup.show(editor);
            }
        })
        );
    }

}
