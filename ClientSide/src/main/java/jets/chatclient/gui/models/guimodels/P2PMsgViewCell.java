package jets.chatclient.gui.models.guimodels;

import javafx.scene.control.ListCell;
import jets.chatclient.gui.controllers.P2PMsgCardController;
import jets.chatclient.gui.models.P2PMessageModel;

public class P2PMsgViewCell extends ListCell<P2PMessageModel> {

    @Override
    protected void updateItem(P2PMessageModel msgCard, boolean empty) {
        super.updateItem(msgCard, empty);

        if (msgCard == null || empty){
            setText(null);
            setGraphic(null);
        }else{
            P2PMsgCardController msgCardController = new P2PMsgCardController();
            msgCardController.setData(msgCard);
            setGraphic(msgCardController.getMsgCard());
        }
    }
}
