package jets.chatclient.gui.helpers;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import jets.chatclient.gui.helpers.ScreenData;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegisterLoginCoordinator {

    private static Parent registerLoginContainer;
    private static final RegisterLoginCoordinator registerLoginCoordinator= new RegisterLoginCoordinator();
    private final Map<String, ScreenData> registerLoginScreens = new HashMap<>();


    public  void initScreen(Parent parent){
        if(registerLoginContainer == null){
            registerLoginContainer = parent;
        }
        else {
            //TODO remove after debugging
            System.out.println("Screen has already been initialized");
        }
    }

    public static RegisterLoginCoordinator getInstance(){
        return  registerLoginCoordinator; }

    public void switchToLoginScreen(){
        if(registerLoginContainer == null){
            throw  new RuntimeException("Register and Login Container haven't been init.");
        }
        if(!registerLoginScreens.containsKey("LoginScreen")){
            System.out.println("Created New Login Scene");
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/LoginView.fxml"));
                Parent loginScreen = fxmlLoader.load();
                ScreenData screenData = new ScreenData(fxmlLoader,loginScreen);
                registerLoginScreens.put("LoginScreen",screenData);
                ((BorderPane)registerLoginContainer).setCenter(loginScreen);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Couldn't load login screen");
            }
        }else {
            System.out.println("loading new login screen");
            Parent loginScreen = registerLoginScreens.get("LoginScreen").getView();
            ((BorderPane)registerLoginContainer).setCenter(loginScreen);
        }
    }
    public  void switchToSignupScreen(){
        if(registerLoginContainer == null){
            throw  new RuntimeException("Register and Login Container haven't been init.");
        }
        if(!registerLoginScreens.containsKey("signUpScreen")){
            System.out.println("Created New Sign up Scene");
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/SignupView.fxml"));
                Parent SignUpScreen = fxmlLoader.load();
                ScreenData screenData = new ScreenData(fxmlLoader,SignUpScreen);
                registerLoginScreens.put("signUpScreen",screenData);
                ((BorderPane)registerLoginContainer).setCenter(SignUpScreen);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Couldn't load Sign up scene");
            }
        }else {
            System.out.println("Loading Sign Up scene");
            Parent signUpScreen = registerLoginScreens.get("signUpScreen").getView();
            ((BorderPane)registerLoginContainer).setCenter(signUpScreen);
        }
    }

    public void switchToGetPasswordScreen(){
        if(registerLoginContainer == null){
            throw  new RuntimeException("Register and Login Container haven't been init.");
        }
        if(!registerLoginScreens.containsKey("passwordScreen")){
            System.out.println("Create Password View Scene");
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/PasswordView.fxml"));
                Parent passwordScreen = fxmlLoader.load();
                ScreenData screenData = new ScreenData(fxmlLoader,passwordScreen);
                registerLoginScreens.put("passwordScreen",screenData);
                ((BorderPane)registerLoginContainer).setCenter(passwordScreen);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Couldn't load Password scene");
            }
        }else {
            System.out.println("Loading Password scene");
            Parent passwordScreen = registerLoginScreens.get("passwordScreen").getView();
            ((BorderPane)registerLoginContainer).setCenter(passwordScreen);
        }
    }



    public Map<String, ScreenData> getScreens(){
        return  registerLoginScreens;
    }
    public void clearScreens(){
        registerLoginScreens.clear();
        registerLoginContainer = null;
    }

}

