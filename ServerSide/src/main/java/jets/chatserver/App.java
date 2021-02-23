package jets.chatserver;

import javafx.application.Application;
import javafx.stage.Stage;
import jets.chatserver.gui.helpers.StageCoordinator;
import jets.chatserver.network.ServerInit;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StageCoordinator stageCoordinator = StageCoordinator.getInstance();
        stageCoordinator.initStage(primaryStage);
        stageCoordinator.switchToServerAnnouncment();
        primaryStage.show();


    }

    @Override
    public void init() {
        //Init Server
        new Thread(() -> {
            new ServerInit().serverInit();
        }).start();
    }

    @Override
    public void stop() {
        // Terminate Database & Network Connections
    }

}
