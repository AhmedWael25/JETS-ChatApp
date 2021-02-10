package jets.chatserver;

import javafx.application.Application;
import javafx.stage.Stage;
import jets.chatserver.network.ServerInit;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
//        StageCoordinator stageCoordinator = StageCoordinator.getInstance();
//        stageCoordinator.initStage(primaryStage);
//        stageCoordinator.switchToLoginScene();
//        primaryStage.show();


    }

    @Override
    public void init() {
        // Initialize Database & Network Connections
        //Init Server
        new ServerInit().serverInit();
    }

    @Override
    public void stop() {
        // Terminate Database & Network Connections
    }

}
