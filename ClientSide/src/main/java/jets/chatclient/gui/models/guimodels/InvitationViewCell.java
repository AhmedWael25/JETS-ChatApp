package jets.chatclient.gui.models.guimodels;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import jets.chatclient.gui.controllers.InvitationCardController;
import jets.chatclient.gui.models.Invitation;

import java.io.IOException;

public class InvitationViewCell extends ListCell<Invitation> {

    @Override
    protected void updateItem(Invitation inv, boolean empty) {
        super.updateItem(inv, empty);

        if (inv == null || empty){
            setText(null);
            setGraphic(null);
        }else {
            InvitationCardController invitationCardController = new InvitationCardController();
            invitationCardController.setData(inv);
            setGraphic(invitationCardController.getCard());
        }
    }
}
