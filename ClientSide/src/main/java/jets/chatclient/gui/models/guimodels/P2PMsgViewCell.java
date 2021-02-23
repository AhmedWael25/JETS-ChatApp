package jets.chatclient.gui.models.guimodels;

import commons.sharedmodels.MsgType;
import javafx.scene.control.ListCell;
import jets.chatclient.gui.controllers.P2PMsgCardController;
import jets.chatclient.gui.controllers.P2PMsgFileController;
import jets.chatclient.gui.models.P2PMessageModel;

public class P2PMsgViewCell extends ListCell<P2PMessageModel> {

    @Override
    protected void updateItem(P2PMessageModel msgCard, boolean empty) {
        super.updateItem(msgCard, empty);

        if (msgCard == null || empty){
            setText(null);
            setGraphic(null);
        }else{

            if(msgCard.getMsgType() == MsgType.TEXT){
                P2PMsgCardController msgCardController = new P2PMsgCardController();
                msgCardController.setData(msgCard);
                setGraphic(msgCardController.getMsgCard());
            }else {
                P2PMsgFileController msgFileController = new P2PMsgFileController();
                msgFileController.setData(msgCard);
                setGraphic(msgFileController.getMsgCard());
            }
        }
    }
}
