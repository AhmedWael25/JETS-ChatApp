package jets.chatclient.gui.models.guimodels;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import jets.chatclient.gui.utils.Iso2Phone;
import jets.chatclient.gui.models.guimodels.Country;


public class CountryButtonCell extends ListCell<Country> {
    JFXTextField phoneCountryLabel;
    Node node;


    @Override
    protected void updateItem(Country country, boolean empty) {
       // super.updateItem(country, empty);

        if(empty){
            setText(null);
            setGraphic(null);
        }
        else {
           phoneCountryLabel=new JFXTextField();

            String selectedCountryPhone = Iso2Phone.getPhone(country.getCode());
            phoneCountryLabel.setText(country.getCode() +selectedCountryPhone );
            setGraphic(phoneCountryLabel);

        }
    }


}
