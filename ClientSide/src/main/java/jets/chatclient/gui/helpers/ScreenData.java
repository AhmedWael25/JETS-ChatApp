package jets.chatclient.gui.helpers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class ScreenData {

    private final FXMLLoader fxmlLoader;
    private final Parent view;

    public ScreenData(FXMLLoader fxmlLoader, Parent view) {
        this.fxmlLoader = fxmlLoader;
        this.view = view;
    }

    public FXMLLoader getLoader() {
        return this.fxmlLoader;
    }

    public Parent getView() {
        return this.view;
    }

}
