package jets.chatclient.gui.helpers;

import commons.remotes.server.ClientLivenessServiceInt;
import commons.remotes.server.RegisteringClientInt;
import javafx.application.Platform;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LivenessChecker {

    ClientLivenessServiceInt livenessService;
    public LivenessChecker() {

        try {
            livenessService = ServicesFactory.getInstance().getlivenessService();
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    public  void initLivenessChecker(){
        ScheduledExecutorService scheduledService = new ScheduledThreadPoolExecutor(1);
        final boolean[] serverDown = {false};
        final boolean[] switchedToScene = {true};

        scheduledService.scheduleWithFixedDelay(new Runnable() {
            DashBoardCoordinator dashBoardCoordinator = DashBoardCoordinator.getInstance();
            StageCoordinator stageCoordinator = StageCoordinator.getInstance();
            @Override
            public void run() {
                try {
                    if(serverDown[0] ==true){
//                        RegisteringClientInt registeringClientInt = ServicesFactory.getInstance().getRegisterClientService();
//                        registeringClientInt.registerClient(ModelsFactory.getInstance().getClient(),ModelsFactory.getInstance().getCurrentUserModel().getPhoneNumber());
                        ServicesFactory.getInstance().reInitServiceFactory();
                    }

//                    RegisteringClientInt registeringClientInt = ServicesFactory.getInstance().getRegisterClientService();
//                    registeringClientInt.registerClient(ModelsFactory.getInstance().getClient(),ModelsFactory.getInstance().getCurrentUserModel().getPhoneNumber());
                     livenessService.checkServerLiveness();
                    //If it reached here, then it's a normal operation
                    serverDown[0] = false;

                    if(serverDown[0] == false && switchedToScene[0] == false){
                        System.out.println("In Finally");
                        Platform.runLater(() ->{
                            stageCoordinator.switchToMainScene();
                        });
                        switchedToScene[0] = true;
                    }
                } catch (RemoteException  e) {
//                    e.printStackTrace();
                    serverDown[0] = true;
                        System.out.println("IN CTH");
                        Platform.runLater(() ->{
                            stageCoordinator.switchToSplash();
                            dashBoardCoordinator.clearScreens();
                        });
                        switchedToScene[0] = false;
                }
            }
        },0L,500L, TimeUnit.MILLISECONDS);
    }
}
