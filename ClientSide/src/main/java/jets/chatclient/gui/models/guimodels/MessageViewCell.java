package jets.chatclient.gui.models.guimodels;

import javafx.scene.control.ListCell;
import jets.chatclient.gui.controllers.MessageCardController;
import jets.chatclient.gui.models.P2PMessageModel;

public class MessageViewCell extends ListCell<P2PMessageModel> {

    @Override
    protected void updateItem(P2PMessageModel msgCard, boolean empty) {
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
