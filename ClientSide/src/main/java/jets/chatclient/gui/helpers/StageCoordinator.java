package jets.chatclient.gui.helpers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jets.chatclient.App;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StageCoordinator {

    private static Stage primaryStage;
    private static final StageCoordinator stageCoordinator = new StageCoordinator();
    private final Map<String, SceneData> scenes = new HashMap<>();

    private StageCoordinator() { }

    public void initStage(Stage stage) {
        if (primaryStage != null) {
            throw new IllegalArgumentException("The Stage Already been initialized");
        }
        primaryStage = stage;
    }

    public static StageCoordinator getInstance() {
        return stageCoordinator;
    }

    public void switchToLoginScene() {
        if (primaryStage == null) {
            throw new RuntimeException("Stage Coordinator should be initialized with a Stage before it could be used");
        }

        if (!scenes.containsKey("login")) {
            try {
                System.out.println("Created New Scene");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/LoginView.fxml"));
                Parent loginView = fxmlLoader.load();
                Scene loginScene = new Scene(loginView);
                SceneData loginSceneData = new SceneData(fxmlLoader, loginView, loginScene);
                scenes.put("login", loginSceneData);
                primaryStage.setScene(loginScene);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("IO Exception: Couldn't load 'Login View' FXML file");
            }
        } else {
            System.out.println("Loaded Existing Scene");
            SceneData loginSceneData = scenes.get("login");
            Scene loginScene = loginSceneData.getScene();
            primaryStage.setScene(loginScene);
        }

    }

    public void switchToSignupScene() {
        if (primaryStage == null) {
            throw new RuntimeException("Stage Coordinator should be initialized with a Stage before it could be used");
        }

        if (!scenes.containsKey("signup")) {
            try {
                System.out.println("Created New Scene");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/SignupView.fxml"));
                Parent signupView = fxmlLoader.load();
                Scene signupScene = new Scene(signupView);
                SceneData signupSceneData = new SceneData(fxmlLoader, signupView, signupScene);
                scenes.put("signup", signupSceneData);
                primaryStage.setScene(signupScene);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("IO Exception: Couldn't load 'Signup View' FXML file");
            }
        } else {
            System.out.println("Loaded Existing Scene");
            SceneData signupSceneData = scenes.get("signup");
            Scene signupScene = signupSceneData.getScene();
            primaryStage.setScene(signupScene);
        }
    }

    public void switchToChatDashBoard() {
        if (primaryStage == null) {
            throw new RuntimeException("Stage Coordinator should be initialized with a Stage before it could be used");
        }

        if (!scenes.containsKey("chatdashboard")) {
            try {
                System.out.println("Created New Scene");
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/views/ChatDashBoardView.fxml"));
                Parent chatDashBoardView = fxmlLoader.load();
                Scene chatDashBoardScene = new Scene(chatDashBoardView);
                SceneData chatDashBoardSceneData = new SceneData(fxmlLoader, chatDashBoardView, chatDashBoardScene);
                scenes.put("chatdashboard", chatDashBoardSceneData);
                primaryStage.setScene(chatDashBoardScene);
            } catch (IOException e) {
                //TODO remove stack trace after debugging
                e.printStackTrace();
                System.out.println("IO Exception: Couldn't load 'Chat Dash Board View' FXML file");
            }
        } else {
            System.out.println("Loaded Existing Scene");
            SceneData chatSceneData = scenes.get("chatdashboard");
            Scene chatScene = chatSceneData.getScene();
            primaryStage.setScene(chatScene);
        }
    }


    /////
    public void switchToGetPasswordScene() {
        if (primaryStage == null) {
            throw new RuntimeException("Stage Coordinator should be initialized with a Stage before it could be used");
        }

        if (!scenes.containsKey("password")) {
            try {
                System.out.println("Created New Scene");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/GetPasswordView.fxml"));
                Parent getPassView = fxmlLoader.load();
                Scene getPassScene = new Scene(getPassView);
                SceneData PasswordSceneData = new SceneData(fxmlLoader, getPassView, getPassScene);
                scenes.put("password", PasswordSceneData);
                primaryStage.setScene(getPassScene);
            } catch (IOException e) {
                System.out.println("IO Exception: Couldn't load 'Get Password View' FXML file");
            }
        } else {
            System.out.println("Loaded Existing Scene");
            SceneData PasswordSceneData = scenes.get("password");
            Scene PasswordScene = PasswordSceneData.getScene();
            primaryStage.setScene(PasswordScene);
        }
    }

}
