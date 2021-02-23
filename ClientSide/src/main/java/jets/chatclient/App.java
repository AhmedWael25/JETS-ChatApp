package jets.chatclient;

import commons.remotes.client.ClientInterface;
import commons.remotes.server.RegisteringClientInt;
import commons.remotes.server.SignInServiceInt;
import commons.sharedmodels.CurrentUserDto;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;
import jets.chatclient.gui.helpers.LivenessChecker;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.ServicesFactory;
import jets.chatclient.gui.helpers.StageCoordinator;
import jets.chatclient.gui.helpers.adapters.DTOObjAdapter;
import jets.chatclient.gui.models.CurrentUserModel;
import jets.chatclient.gui.models.UserCredentials;
import jets.chatclient.gui.utils.ConfigManager;

import java.io.File;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        StageCoordinator stageCoordinator = StageCoordinator.getInstance();
        stageCoordinator.initStage(primaryStage);
        stageCoordinator.switchToMainScene();
        ConfigManager configManager = new ConfigManager();

        UserCredentials userCredentials = configManager.readConfigFile();

        if(userCredentials.getUserPhone().equals("")||!configManager.checkIfPasswordSaved() ){
            stageCoordinator.switchToMainScene();
        }


        else {
            System.out.println("user has credentials stored");
            SignInServiceInt  signInService=null;
            try {
                 signInService = ServicesFactory.getInstance().getSignInService();
                boolean verified = signInService.checkUserCredentials(userCredentials.getUserPhone(),userCredentials.getEncryptedPassword() );

                if (verified) {
                    CurrentUserDto userDto= signInService.signUserIn(userCredentials.getUserPhone());
                    DTOObjAdapter.convertDtoToCurrentUser(userDto);
                    stageCoordinator.switchToChatDashBoard();
                }

             } catch (RemoteException | NotBoundException e) {
                System.out.println("can't find Service");
                e.printStackTrace();
            }
        }

        primaryStage.setTitle("Connect ChatApp");
        primaryStage.getIcons().add(new Image(getClass().getResource("/images/symbol.png").toExternalForm()) );
        primaryStage.show();


    }


    @Override
    public void init() {
        // Initialize Database & Network Connections
        ClientInterface xd = ModelsFactory.getInstance().getClient();
        new LivenessChecker().initLivenessChecker();

    }

    @Override
    public void stop() {
        // Terminate Database & Network Connections
    }

    public static void closeApp(){
        Platform.exit();

    }

}
