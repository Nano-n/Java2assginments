package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.net.MalformedURLException;
import java.util.Map;

public class Controller{
    @FXML private LineChart<?, ?> lineChart;
    @FXML private CategoryAxis x;
    @FXML private NumberAxis y;
    private ModelData model;        // Store the cryto data of day, hour, or minute model
    

    public void setBtn_day(ActionEvent event) throws Exception {
        /* Clear the previous data */
        lineChart.getData().clear();

        /* Define a series */
        XYChart.Series series_day = new XYChart.Series<>();

        /* Setup the day model */
        model = new ModelData(ModelData.url_day);
        model.start();
        model.join();

        int list_size = model.getTimePriceMapList().size();
        /* Iterate over the list of map */
        for(int i = 0; i < list_size; i += 1) {
            Map<?,?> dayMap = model.getTimePriceMapList().get(i);
            /* Iterate over each pair in the ith map of the list */
            for(Map.Entry<?,?> entry : dayMap.entrySet()) {
                series_day.getData().add(new XYChart.Data<>( entry.getKey(), entry.getValue() ));
            }
        }
        series_day.setName("Day Data");
        /* Add series to the line chart */
        lineChart.getData().add(series_day);
    }

    public void setBtn_hour(ActionEvent event) throws Exception {
        lineChart.getData().clear();

        XYChart.Series series_hour = new XYChart.Series<>();

        /* Setup the day model */
        model = new ModelData(ModelData.url_hour);
        model.start();
        model.join();

        int list_size = model.getTimePriceMapList().size();

        for(int i = 0; i < list_size; i += 1) {
            Map<?,?> hourMap = model.getTimePriceMapList().get(i);
            for(Map.Entry<?,?> entry : hourMap.entrySet()) {
                series_hour.getData().add(new XYChart.Data<>( entry.getKey(), entry.getValue() ));
            }
        }
        series_hour.setName("Hour Data");
        lineChart.getData().add(series_hour);
    }

    public void setBtn_minute (ActionEvent event) throws Exception {
        lineChart.getData().clear();

        XYChart.Series series_minute = new XYChart.Series<>();

        /* Setup the day model */
        model = new ModelData(ModelData.url_min);
        model.start();
        model.join();

        int list_size = model.getTimePriceMapList().size();

        for(int i = 0; i < list_size; i += 1) {
            Map<?,?> minuteMap = model.getTimePriceMapList().get(i);
            for(Map.Entry<?,?> entry : minuteMap.entrySet()) {
                series_minute.getData().add(new XYChart.Data<>( entry.getKey(), entry.getValue() ));
            }
        }
        series_minute.setName("Minute Data");
        lineChart.getData().add(series_minute);
    }
}
