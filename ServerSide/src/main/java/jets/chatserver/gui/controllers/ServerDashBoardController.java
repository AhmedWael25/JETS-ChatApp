package jets.chatserver.gui.controllers;

import com.jfoenix.controls.JFXButton;
import commons.remotes.server.RegisteringClientInt;
import eu.hansolo.tilesfx.Tile;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import jets.chatserver.database.daoImpl.UserDaoImpl;
import jets.chatserver.gui.utils.Chatrs;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class ServerDashBoardController implements Initializable {

    @FXML
    public StackPane mainContainer;
    @FXML
    public BorderPane borderContainer;
    @FXML
    private GridPane sideMenu;
    @FXML
    private Circle userImageCircle;
    @FXML
    private Circle userStatusCircle;
    @FXML
    private JFXButton profileBtn;
    @FXML
    private JFXButton chatBtn;
    @FXML
    private JFXButton groupChatBtn;
    @FXML
    private JFXButton groupsBtn;
    @FXML
    private JFXButton settingsBtn;

    private RegisteringClientInt registeringClientService;

    //
//    @FXML
//    void switchedToGPChatPane(ActionEvent event) {
//        dashBoardCoordinator.switchToGpChatScreen();
//        activateBtn(groupChatBtn);
//    }
//
//    @FXML
//    void switchedToGroupsPane(ActionEvent event) {
//        dashBoardCoordinator.switchToGroupScreen();
//        activateBtn(groupsBtn);
//    }
//
//    @FXML
//    void switchedToP2PChatPane(ActionEvent event) {
//
//        dashBoardCoordinator.switchToChatScreen();
//        activateBtn(chatBtn);
//    }
//
//    @FXML
//    void switchedToSettingsPane(ActionEvent event) {
//
//    }
//
//    @FXML
//    void switchedToUserProfilePane(ActionEvent event) {
//        dashBoardCoordinator.switchToProfileScreen();
//        activateBtn(profileBtn);
//    }
//
//    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        //TODO REMOVE After debugging
//        System.out.println("Chat Dash Board Initialized");
//        ModelsFactory modelsFactory = ModelsFactory.getInstance();
//        CurrentUserModel currentUserModel = modelsFactory.getCurrentUserModel();
//
//        dashBoardCoordinator= DashBoardCoordinator.getInstance();
//        dashBoardCoordinator.initScreen(borderContainer);
//
//        activateBtn(chatBtn);
//
//        //Register Client With Server
//        //First Get Our Registry Through models factory
//        //Then look up the service we require
//        try {
//            ServicesFactory servicesFactory = ServicesFactory.getInstance();
//            registeringClientService = (RegisteringClientInt) servicesFactory.getRegisterClientService();
//            //TODO Remove this ==> U Assumed the current user ID(Phone) is 1 Only For testing purposes
//            //TODO SHOULD be replaced with current user model
//            registeringClientService.registerClient(modelsFactory.getClient(), "3");
////            registeringClientService.registerClient(modelsFactory.getClient(), "3");
//
//         } catch (RemoteException  | NotBoundException e) {
//            e.printStackTrace();
//        }

        try {
            Map<String, Integer> countryusers = UserDaoImpl.getUserDaoInstance().getUsersperCounry();
            int males = UserDaoImpl.getUserDaoInstance().getGenderCount("male");
            int females = UserDaoImpl.getUserDaoInstance().getGenderCount("female");
            int online = UserDaoImpl.getUserDaoInstance().getAvailabilityCount(1);
            int offline = UserDaoImpl.getUserDaoInstance().getAvailabilityCount(0);


            Tile genderTile = Chatrs.getDonutChartTile("Gender", "this is numbr of males/femaless", "male", males, "female", females);
            Tile avaiTile = Chatrs.getDonutChartTile("Availability", "this is numbr of On/Off", "online", online, "female", offline);
//            List<XYChart.Data> dataList= getXYData(countryusers);
//            XYChart.Data[] data=new XYChart.Data[dataList.size()];
//            for(int i=0;i<dataList.size();i++)
//                data[i]=dataList.get(i);

//            Tile chartTile= Chatrs.getLineChartTile("User/Country",data) ;

            HBox hBox = new HBox();
//            hBox.getChildren().add(genderTile);
//            hBox.getChildren().add(avaiTile);
//
//            hBox.getChildren().add(chartTile);

            borderContainer.setCenter(hBox);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    List<XYChart.Data>  getXYData(Map<String, Integer> countryUsers) {
        List<XYChart.Data> data = null;
        int i = 0;
        Integer othersSum = 0;

        Iterator<Map.Entry<String, Integer>> iterator = countryUsers.entrySet().iterator();

        while (iterator.hasNext()) {
//            if (i < 5) {
                data.add(new XYChart.Data(iterator.next().getKey(), iterator.next().getValue()));
//                i++;
//            }
//            else {
//                othersSum += iterator.next().getValue();
//            }
        }
//        data.add(new XYChart.Data("others", othersSum));

        return data;
    }

    private void activateBtn(JFXButton btn) {
        deActivateAllBtns();
        ObservableList<String> btnStyleClasses = btn.getStyleClass();
        if (!btnStyleClasses.contains("sideMenuBtn-active")) {
            btnStyleClasses.add("sideMenuBtn-active");

            if (!btn.equals(profileBtn)) {
                FontIcon n = (FontIcon) btn.getGraphic();
                n.setIconColor(Paint.valueOf("#636b61"));
            }
        }
        if (btnStyleClasses.contains("sideMenuBtn-inactive")) {
            btnStyleClasses.remove("sideMenuBtn-inactive");
        }

    }

    private void deActivateBtn(JFXButton btn) {
        ObservableList<String> btnStyleClasses = btn.getStyleClass();
        if (!btnStyleClasses.contains("sideMenuBtn-inactive")) {
            btnStyleClasses.add("sideMenuBtn-inactive");

            if (!btn.equals(profileBtn)) {
                FontIcon n = (FontIcon) btn.getGraphic();
                n.setIconColor(Paint.valueOf("#ffffff"));
            }
        }
        if (btnStyleClasses.contains("sideMenuBtn-active")) {
            btnStyleClasses.remove("sideMenuBtn-active");
        }
    }

    private void deActivateAllBtns() {

        deActivateBtn(profileBtn);
        deActivateBtn(chatBtn);
        deActivateBtn(groupChatBtn);
        deActivateBtn(groupsBtn);
        deActivateBtn(settingsBtn);
    }

    public void switchedToP2PChatPane(ActionEvent actionEvent) {
    }

    public void switchedToGPChatPane(ActionEvent actionEvent) {
    }

    public void switchedToGroupsPane(ActionEvent actionEvent) {
    }

    public void switchedToSettingsPane(ActionEvent actionEvent) {
    }

    public void switchedToUserProfilePane(ActionEvent actionEvent) {
    }
}
