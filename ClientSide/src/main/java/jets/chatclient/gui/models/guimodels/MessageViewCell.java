package jets.chatclient.gui.models.guimodels;

import javafx.scene.control.ListCell;
import jets.chatclient.gui.controllers.MessageCardController;
import jets.chatclient.gui.models.MessageModel;

public class MessageViewCell extends ListCell<MessageModel> {

    @Override
    protected void updateItem(MessageModel msgCard, boolean empty) {
        super.updateItem(msgCard, empty);

        if (msgCard == null || empty){
            setText(null);
            setGraphic(null);
        }else{
            MessageCardController msgCardController = new MessageCardController();
            msgCardController.setData(msgCard);
            setGraphic(msgCardController.getMsgCard());
        }
    }
}
