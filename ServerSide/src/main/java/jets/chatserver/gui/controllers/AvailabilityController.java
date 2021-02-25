package jets.chatserver.gui.controllers;

import commons.remotes.client.ClientInterface;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import jets.chatserver.gui.helpers.ModelsFactory;
import jets.chatserver.network.ServerInit;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Map;
import java.util.ResourceBundle;

public class AvailabilityController implements Initializable {


    public static Map<String, ClientInterface> currentConnectedUsers ;


    @FXML
    private BorderPane container;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentConnectedUsers = ModelsFactory.getInstance().getCurrentConnectedUsers();

        Tile  switchTile = TileBuilder.create()
                .skinType(Tile.SkinType.SWITCH)
                .prefSize(300, 400)
                .title("Server Availability")
                .animated(true)
                .flatUI(true)
                .build();
        switchTile.setActive(true);
        container.setCenter(switchTile);

        switchTile.setOnSwitchPressed(e -> {

        });
        switchTile.setOnSwitchReleased(e -> {

        });

    }


}
