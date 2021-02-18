package jets.chatclient.gui.models.guimodels;

import commons.sharedmodels.MessageDto;
import javafx.scene.control.ListCell;
import jets.chatclient.gui.controllers.GpChatCardController;
import jets.chatclient.gui.controllers.GpMessageController;
import jets.chatclient.gui.models.MessageModel;

public class GPChatMsgViewCell extends ListCell<MessageModel> {

    @Override
    protected void updateItem(MessageModel item, boolean empty) {
        super.updateItem(item, empty);

        if (item == null || empty){
            setText(null);
            setGraphic(null);
        }else{
            GpMessageController gpMessageController = new GpMessageController();
            gpMessageController.setData(item);
            setGraphic(gpMessageController.getMsgCard());
        }
    }
}
