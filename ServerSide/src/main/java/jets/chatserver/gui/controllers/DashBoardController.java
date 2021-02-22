package jets.chatserver.gui.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import jets.chatserver.gui.helpers.ServerDashBoardCoordinator;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {

    public Text tPageTitle;
    public JFXButton btnServerAvail;
    public JFXButton btnDashBoard;
    public JFXButton btnUsers;
    public JFXButton btnAnnoncements;
    public BorderPane scieneContainer;

    private ServerDashBoardCoordinator dashBoardCoordinator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO REMOVE After debugging
        System.out.println("Server Dash Board Initialized");

        dashBoardCoordinator= ServerDashBoardCoordinator.getInstance();
        dashBoardCoordinator.initScreen(scieneContainer);

        try {
            dashBoardCoordinator.initAllScreens();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dashBoardCoordinator.switchToAvailScreen();
    }

    @FXML
    void handleDashBoard(ActionEvent event) {
        dashBoardCoordinator.switchToStatisticsScreen();

    }

    @FXML
    void handleAnnoncements(ActionEvent event) {
        dashBoardCoordinator.switchToAnnouncementScreen();
    }

    @FXML
    void handleServerAvail(ActionEvent event) {
        dashBoardCoordinator.switchToAvailScreen();
    }

    @FXML
    void handleUsers(ActionEvent event) {
        dashBoardCoordinator.switchToUsersScreen();
    }


}
