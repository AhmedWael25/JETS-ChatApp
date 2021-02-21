package jets.chatclient.gui.helpers;

import commons.remotes.server.RegisteringClientInt;
import jets.chatclient.App;
import jets.chatclient.gui.models.CurrentUserModel;
import jets.chatclient.gui.models.UserCredentials;
import jets.chatclient.gui.utils.ConfigManager;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class ExitFromApp {

    public static void ExitApplication(){
     try {
         RegisteringClientInt registeringClientService = null;
         ModelsFactory modelsFactory = ModelsFactory.getInstance();
         CurrentUserModel currentUserModel = modelsFactory.getCurrentUserModel();
         Registry reg = modelsFactory.getRegistry();
         registeringClientService = (RegisteringClientInt) reg.lookup("RegisteringService");
         String userPassword = registeringClientService.getEncryptedPassword(currentUserModel.getPhoneNumber());
         System.out.println(userPassword);
         String userPhone = currentUserModel.getPhoneNumber();
         UserCredentials userCredentials = new UserCredentials(userPhone, userPassword);
         ConfigManager configManager = new ConfigManager();
         configManager.createConfigFile(userCredentials);
         registeringClientService.disconnectClient(userPhone);
         Runtime rt = Runtime.getRuntime();
         App.closeApp();
         rt.exit(0);
          }
     catch (RemoteException | NotBoundException exe) {
        exe.printStackTrace();
        }
     } }

