package jets.chatserver;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;
import jets.chatserver.gui.helpers.ServerDashBoardCoordinator;
import jets.chatserver.gui.helpers.StageCoordinator;
import jets.chatserver.network.ServerInit;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StageCoordinator stageCoordinator = StageCoordinator.getInstance();
        stageCoordinator.initStage(primaryStage);
        primaryStage.setTitle("Connect ChatApp Server");
        primaryStage.getIcons().add(new Image(getClass().getResource("/images/symbol.png").toExternalForm()) );
       stageCoordinator.switchToServerDashBoard();
        primaryStage.setResizable(false);
        primaryStage.show();

        Window window = primaryStage.getScene().getWindow();
        window.setOnCloseRequest(e->{
            try {
                ServerInit.unbindServices();
            } catch (RemoteException | NotBoundException remoteException) {
                remoteException.printStackTrace();
            }
            Runtime rt = Runtime.getRuntime();
            rt.exit(0);
        });


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
