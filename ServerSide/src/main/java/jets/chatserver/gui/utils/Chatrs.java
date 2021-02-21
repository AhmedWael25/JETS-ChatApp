package jets.chatserver.gui.utils;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.chart.ChartData;
import eu.hansolo.tilesfx.skins.BarChartItem;
import javafx.scene.chart.XYChart;

public class Chatrs {
    static ChartData chartData1;
    static ChartData chartData2;
    static Tile donutChartTile;
    static XYChart.Series<String, Number> lineChartData;


    public static Tile getDonutChartTile(String title,String msg, String name1,int value1,String name2,int value2) {
        chartData1 = new ChartData(name1, value1, Tile.BLUE);
        chartData2 = new ChartData(name2, value2, Tile.RED);

        donutChartTile = TileBuilder.create()
                .skinType(Tile.SkinType.DONUT_CHART)
                .prefSize(300, 200)
                .title(title)
                .text(msg)
                .textVisible(true)
                .chartData(chartData1,chartData2)
                .build();
        return donutChartTile;
    }

    public static Tile getLineChartTile(String title,XYChart.Data ... xValues) {
        lineChartData = new XYChart.Series();
        lineChartData.setName("number of users");
        for (XYChart.Data xValue :xValues)
        lineChartData.getData().add(xValue);

        Tile lineChartTile = TileBuilder.create()
                .skinType(Tile.SkinType.SMOOTHED_CHART)
                .prefSize(300, 300)
                .title(title)
                .animated(true)
                .smoothing(false)
                .series(lineChartData)
                .backgroundColor(Tile.GRAY)
                .build();

        return lineChartTile;

    }
}
