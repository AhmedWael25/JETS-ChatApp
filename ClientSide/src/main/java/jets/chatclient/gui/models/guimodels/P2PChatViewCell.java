package jets.chatclient.gui.models.guimodels;

import javafx.scene.control.ListCell;
import jets.chatclient.gui.controllers.P2PChatCardController;
import jets.chatclient.gui.models.P2PChatModel;

public class P2PChatViewCell extends ListCell<P2PChatModel> {

    @Override
    protected void updateItem(P2PChatModel chatCard, boolean empty) {
        super.updateItem(chatCard, empty);

        if (chatCard == null || empty){
            setText(null);
            setGraphic(null);
        }else{
            P2PChatCardController p2pChatCardController = new P2PChatCardController();
            p2pChatCardController.setData(chatCard);
            setGraphic(p2pChatCardController.getChatCard());
        }
    }
}
