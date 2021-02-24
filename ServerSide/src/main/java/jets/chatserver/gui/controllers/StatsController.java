package jets.chatserver.gui.controllers;

import commons.utils.Countries;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.chart.ChartData;
import eu.hansolo.tilesfx.icons.Flag;
import eu.hansolo.tilesfx.tools.FlowGridPane;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import jets.chatserver.database.daoImpl.UserDaoImpl;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

public class StatsController implements Initializable {

    @FXML
    private FlowGridPane container;

    private long lastTimerCall;
    private AnimationTimer timer;
    Map<String, Integer> countryusers;
    int males;
    int females;
    int online;
    int offline;
    Tile genderTile;
    Tile avaiTile;
    Tile lineChartTile;
    Tile countryFlagsTile;

    ChartData malesData;
    ChartData femalesData;
    ChartData onlineUsersData;
    ChartData offlineUsersData;

    LocalDateTime nowTime;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        lastTimerCall = System.nanoTime();
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now > lastTimerCall + 1_000_000_000L) {
                    try {
                        countryusers = UserDaoImpl.getUserDaoInstance().getUsersperCounry();
                        males = UserDaoImpl.getUserDaoInstance().getGenderCount("male");
                        females = UserDaoImpl.getUserDaoInstance().getGenderCount("female");
                        online = UserDaoImpl.getUserDaoInstance().getAvailabilityCount(1);
                        offline = UserDaoImpl.getUserDaoInstance().getAvailabilityCount(0);

                        FillFlagsTieWithData(countryusers, countryFlagsTile);
                        nowTime = LocalDateTime.now();
                        countryFlagsTile.setText("Data Collected at" + (DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")).format(nowTime));
                        FillLineChartWithData(countryusers, lineChartTile);
                        malesData.setValue(males);
                        femalesData.setValue(females);
                        onlineUsersData.setValue(online);
                        offlineUsersData.setValue(offline);

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    lastTimerCall = now;
                }
            }
        };
        timer.start();

        malesData = new ChartData("male", males, Tile.DARK_BLUE);
        femalesData = new ChartData("female", females, Tile.ORANGE);
        onlineUsersData = new ChartData("Online", online, Tile.DARK_BLUE);
        offlineUsersData = new ChartData("Offline", offline, Tile.ORANGE);


        lineChartTile = TileBuilder.create()
                .skinType(Tile.SkinType.SMOOTHED_CHART)
                .prefSize(450, 450)
                .title("User/Country")
                .animated(true)
                .smoothing(false)
                .backgroundColor(Tile.GRAY)
                .foregroundColor(Tile.DARK_BLUE)
                .build();



        countryFlagsTile = TileBuilder.create()
                .skinType(Tile.SkinType.CUSTOM)
                .prefSize(450, 450)
                .title("Users Per Country")
                .backgroundColor(Tile.GRAY)
                .foregroundColor(Tile.DARK_BLUE)
                .animated(true)
                .build();

        genderTile = TileBuilder.create()
                .skinType(Tile.SkinType.DONUT_CHART)
                .prefSize(450, 450)
                .title("Gender")
                .text("this is number of males/females")
                .animated(true)
                .textVisible(true)
                .backgroundColor(Tile.GRAY)
                .foregroundColor(Tile.DARK_BLUE)
                .chartData(malesData, femalesData)
                .build();

        avaiTile = TileBuilder.create()
                .skinType(Tile.SkinType.DONUT_CHART)
                .prefSize(450, 450)
                .title("Availability")
                .text("this is number of online/offline Users.")
                .animated(true)
                .textVisible(true)
                .backgroundColor(Color.GRAY)
                .foregroundColor(Tile.DARK_BLUE)
                .chartData(onlineUsersData, offlineUsersData)
                .build();

        container.getChildren().add(genderTile);
        container.getChildren().add(avaiTile);
        container.getChildren().add(countryFlagsTile);
        container.getChildren().add(lineChartTile);

    }

    private void FillLineChartWithData(Map<String, Integer> data, Tile countrychartTile) {
        XYChart.Series lineChartData = new XYChart.Series();
        lineChartData.setName("number of users");

        Iterator<Map.Entry<String, Integer>> iterator = data.entrySet().iterator();

        XYChart.Data value;
        Map.Entry<String, Integer> curr;
        int i =0;
        int countOthers=0;
        while (iterator.hasNext()) {
            curr = iterator.next();
            if(i<5) {
                value = new XYChart.Data(curr.getKey(), curr.getValue());
                lineChartData.getData().add(value);
                i++;
            }
            else {
                countOthers+=curr.getValue();
            }
        }
        lineChartData.getData().add(new XYChart.Data("othres", countOthers));

        countrychartTile.setSeries(lineChartData);
    }

    private void FillFlagsTieWithData(Map<String, Integer> data, Tile countryFlagsTile) {
        Label name = new Label("Country Name");
        name.setTextFill(Tile.FOREGROUND);
        name.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(name, Priority.NEVER);

        Region spacer = new Region();
        spacer.setPrefSize(5, 5);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label views = new Label("Users");
        views.setTextFill(Tile.FOREGROUND);
        views.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(views, Priority.NEVER);

        HBox header = new HBox(5, name, spacer, views);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setFillHeight(true);


        VBox dataTable = new VBox(0);
        dataTable.setFillWidth(true);
        dataTable.getChildren().add(header);

        Iterator<Map.Entry<String, Integer>> iterator = data.entrySet().iterator();
        String countryISO2;
        HBox countryData;
        Flag countryFlag;
        Map.Entry<String, Integer> curr;
        while (iterator.hasNext()) {
            curr = iterator.next();
            countryISO2 = Countries.getCodebyCountry(curr.getKey());
            countryFlag = Flag.iso2(countryISO2);
            countryData = getCountryItem(countryFlag, curr.getKey(), curr.getValue().toString());
            dataTable.getChildren().add(countryData);
        }
        countryFlagsTile.setGraphic(dataTable);
    }

    private HBox getCountryItem(final Flag flag, final String text, final String data) {

        ImageView imageView = new ImageView(flag.getImage(22));
        HBox.setHgrow(imageView, Priority.NEVER);

        Label name = new Label(text);
        name.setTextFill(Tile.FOREGROUND);
        name.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(name, Priority.NEVER);

        Region spacer = new Region();
        spacer.setPrefSize(5, 5);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label views = new Label(data);
        views.setTextFill(Tile.FOREGROUND);
        views.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(views, Priority.NEVER);

        HBox hBox = new HBox(5,imageView, name, spacer, views);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setFillHeight(true);


        return hBox;
    }

}


