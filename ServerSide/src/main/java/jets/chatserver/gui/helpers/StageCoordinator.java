package jets.chatserver.gui.helpers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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


    public void switchToServerDashBoard() {
        if (primaryStage == null) {
            throw new RuntimeException("Stage Coordinator should be initialized with a Stage before it could be used");
        }

        if (!scenes.containsKey("dashboard")) {
            try {
                System.out.println("Created New Server DashBoard Scene");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/DashBoardView.fxml"));
                Parent dashboardView = fxmlLoader.load();
                Scene  dashBoardScene = new Scene(dashboardView);
                SceneData dashBoardSceneData = new SceneData(fxmlLoader, dashboardView, dashBoardScene);
                scenes.put("dashboard", dashBoardSceneData);
                primaryStage.setScene(dashBoardScene);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("IO Exception: Couldn't load 'DashBoard' FXML file");
            }
        } else {
            System.out.println("Loaded Existing Scene");
            SceneData dashBoardSceneData = scenes.get("dashboard");
            Scene signupScene = dashBoardSceneData.getScene();
            primaryStage.setScene(signupScene);
        }
    }



}
