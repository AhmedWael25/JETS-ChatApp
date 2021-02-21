package jets.chatclient.gui.controllers;

import commons.remotes.server.SignInServiceInt;
import commons.sharedmodels.CurrentUserDto;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import jets.chatclient.gui.helpers.ModelsFactory;
import jets.chatclient.gui.helpers.RegisterLoginCoordinator;
import jets.chatclient.gui.helpers.StageCoordinator;
import jets.chatclient.gui.helpers.adapters.DTOObjAdapter;
import jets.chatclient.gui.models.UserCredentials;
import jets.chatclient.gui.utils.ConfigManager;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private static RegisterLoginCoordinator registerLoginCoordinator;
   // private RegisterLoginCoordinator registerLoginCoordinator;
   // private static final MainController mainController = new MainController();
    @FXML
    public HBox hBox;

    @FXML
    public BorderPane MBorderPane;

    @FXML
    public AnchorPane mainPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        registerLoginCoordinator = RegisterLoginCoordinator.getInstance();

        registerLoginCoordinator.initScreen(MBorderPane);
        registerLoginCoordinator.switchToLoginScreen();
    }


   /* private void loadSplashScreen() {
        try {
            //Load splash screen view FXML
            StackPane pane = FXMLLoader.load(getClass().getResource(("Splash.fxml")));
            //Add it to root container (Can be StackPane, AnchorPane etc)
            mainPane.getChildren().setAll(pane);

            //Load splash screen with fade in effect
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            //Finish splash with fade out effect
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();

            //After fade in, start fade out
            fadeIn.setOnFinished((e) -> {
                File file = new File("config.xml");
                if(file.length()==0){
                   // stageCoordinator.switchToMainScene();
                    registerLoginCoordinator.initScreen(MBorderPane);
                    registerLoginCoordinator.switchToLoginScreen();
                    System.out.println("user doesn't have credentials stored");
                }

                else {
                    System.out.println("user has credentials stored");
                    ConfigManager configManager = new ConfigManager();
                    UserCredentials userCredentials = configManager.readConfigFile();
                    SignInServiceInt signInService=null;
                    try {
                        ModelsFactory modelsFactory = ModelsFactory.getInstance();

                        Registry reg = modelsFactory.getRegistry();
                        signInService = (SignInServiceInt) reg.lookup("SignInService");
                        boolean verified = signInService.checkUserCredentials(userCredentials.getUserPhone(),userCredentials.getEncryptedPassword() );

                        StageCoordinator stageCoordinator = StageCoordinator.getInstance();
                        if (verified) {
                            CurrentUserDto userDto= signInService.signUserIn(userCredentials.getUserPhone());
                            DTOObjAdapter.convertDtoToCurrentUser(userDto);
                            stageCoordinator.switchToChatDashBoard();
                        }
                        else {
                            // user password or userPhone has been changed on config file
                            stageCoordinator.switchToMainScene();
                        }

                    } catch (RemoteException | NotBoundException exe) {
                        System.out.println("can't find Service");
                        exe.printStackTrace();
                    }


                }
                fadeOut.play();
            });

            //After fade out, load actual content
         /*   fadeOut.setOnFinished((e) -> {
                try {
                    AnchorPane parentContent = FXMLLoader.load(getClass().getResource(("/main.fxml")));
                    root.getChildren().setAll(parentContent);
                } catch (IOException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    } */



}
