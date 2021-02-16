package jets.chatclient.gui.models.guimodels;

import javafx.scene.control.ListCell;
import jets.chatclient.gui.controllers.GpFriendMiniCardController;
import jets.chatclient.gui.controllers.InvitationCardController;
import jets.chatclient.gui.models.FriendModel;
import jets.chatclient.gui.models.Invitation;

public class GpFrndViewCell extends ListCell<FriendModel> {

    @Override
    protected void updateItem(FriendModel item, boolean empty) {
        super.updateItem(item, empty);

        if (item == null || empty){
            setText(null);
            setGraphic(null);
        }else {
            GpFriendMiniCardController gpFriendMiniCardController = new GpFriendMiniCardController();
            gpFriendMiniCardController.setData(item);
            setGraphic(gpFriendMiniCardController.getCard());
        }
    }




}
