package jets.chatclient.gui.models.guimodels;

import commons.sharedmodels.MsgType;
import javafx.scene.control.ListCell;
import jets.chatclient.gui.controllers.GpMessageController;
import jets.chatclient.gui.controllers.GpMessageFileController;
import jets.chatclient.gui.models.GpMessageModel;

public class GPChatMsgViewCell extends ListCell<GpMessageModel> {

    @Override
    protected void updateItem(GpMessageModel item, boolean empty) {
        super.updateItem(item, empty);

        if (item == null || empty){
            setText(null);
            setGraphic(null);
        }else{

            if(item.getMsgType() == MsgType.TEXT){
                GpMessageController gpMessageController = new GpMessageController();
                gpMessageController.setData(item);
                setGraphic(gpMessageController.getMsgCard());
            }else {
                GpMessageFileController gpMessageFileController = new GpMessageFileController();
                gpMessageFileController.setData(item);
                setGraphic(gpMessageFileController.getMsgCard());
            }
        }
    }
}

