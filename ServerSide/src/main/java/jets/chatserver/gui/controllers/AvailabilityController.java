package jets.chatserver.gui.controllers;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AvailabilityController implements Initializable {

    @FXML
    private BorderPane container;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Tile  switchTile = TileBuilder.create()
                .skinType(Tile.SkinType.SWITCH)
                .prefSize(300, 400)
                .title("Server Availability")
                .animated(true)
                .build();
        container.setCenter(switchTile);

        switchTile.setOnSwitchPressed(e -> System.out.println("Switch pressed"));
        switchTile.setOnSwitchReleased(e -> System.out.println("Switch released"));


    }
}
