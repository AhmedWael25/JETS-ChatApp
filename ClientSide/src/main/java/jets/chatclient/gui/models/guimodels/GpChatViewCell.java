package jets.chatclient.gui.models.guimodels;

import javafx.scene.control.ListCell;
import jets.chatclient.gui.controllers.GpChatCardController;
import jets.chatclient.gui.controllers.P2PChatCardController;
import jets.chatclient.gui.models.GpChatModel;

public class GpChatViewCell extends ListCell<GpChatModel> {

    @Override
    protected void updateItem(GpChatModel item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty){
            setText(null);
            setGraphic(null);
        }else{
            GpChatCardController gpChatCardController =   new GpChatCardController();
            gpChatCardController.setData(item);
            setGraphic(gpChatCardController.getChatCard());
        }
    }
}
