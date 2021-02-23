package jets.chatclient.gui.models.guimodels;

import javafx.scene.control.ListCell;
import jets.chatclient.gui.controllers.ContactCardController;
import jets.chatclient.gui.controllers.InvitationCardController;
import jets.chatclient.gui.models.ContactModel;
import jets.chatclient.gui.models.Invitation;

public class ContactViewCell extends ListCell<ContactModel> {

    @Override
    protected void updateItem(ContactModel contact, boolean empty) {
        super.updateItem(contact, empty);

        if (contact == null || empty){
            setText(null);
            setGraphic(null);
        }else {
            ContactCardController contactCardController = new ContactCardController();
            contactCardController.setData(contact);
            setGraphic(contactCardController.getContactCard());
        }
    }
}
