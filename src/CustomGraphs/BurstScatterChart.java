/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CustomGraphs;

import java.util.List;
import java.util.Objects;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.Axis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import model.BurstTabMittelwert;

/**
 *
 * @author Admin
 */
public class BurstScatterChart<X, Y> extends ScatterChart {

    private ObservableList<Data<?, ?>> horizontalMarkers;
    private ObservableList<Data<?, ?>> verticalMarkers;
    private List<BurstTabMittelwert> mittelwerte;
    private int counter = 0;

    public BurstScatterChart(Axis<X> xAxis, Axis<Y> yAxis) {
        super(xAxis, yAxis);
        horizontalMarkers = FXCollections.observableArrayList(data -> new Observable[]{data.YValueProperty()});
        horizontalMarkers.addListener((InvalidationListener) observable -> layoutPlotChildren());
        verticalMarkers = FXCollections.observableArrayList(data -> new Observable[]{data.XValueProperty()});
        verticalMarkers.addListener((InvalidationListener) observable -> layoutPlotChildren());
    }

    //just adding the markers. drawing happens in layoutPlotChildren() down below
    public void addAllHorizontalMarkers(List<BurstTabMittelwert> mittelwerte) {
        this.mittelwerte = mittelwerte;
        for (BurstTabMittelwert burstTabMittelwert : mittelwerte) {
            addHorizontalValueMarker(new Data<>(0, burstTabMittelwert.getValue()));
        }
    }

    public void addHorizontalValueMarker(Data<?, ?> marker) {
        Objects.requireNonNull(marker, "the marker must not be null");
        if (horizontalMarkers.contains(marker)) {
            return;
        }
        Line line = new Line();
        marker.setNode(line);
        getPlotChildren().add(line);
        horizontalMarkers.add(marker);
    }

    public void removeAllHorizontalMarkers() {
        for (Data<?, ?> horizontalMarker : horizontalMarkers) {
            removeHorizontalValueMarker(horizontalMarker);
        }
        horizontalMarkers.clear();
    }

    public void removeHorizontalValueMarker(Data<?, ?> marker) {
        Objects.requireNonNull(marker, "the marker must not be null");
        if (marker.getNode() != null) {
            getPlotChildren().remove(marker.getNode());
            marker.setNode(null);
        }
    }

    //just adding the markers. drawing happens in layoutPlotChildren() down below
    public void addAllVerticalMarkers(XYChart.Series<Number, Number> series) {
        int size = series.getData().size();
        List<Data<Number, Number>> dataInList = series.getData();
        for (int i = 0; i < size; i++) {
            addVerticalValueMarker(new Data<>(dataInList.get(i).getXValue(), dataInList.get(i).getYValue()));
        }
    }

    public void addVerticalValueMarker(Data<?, ?> marker) {
        Objects.requireNonNull(marker, "the marker must not be null");
        if (verticalMarkers.contains(marker)) {
            return;
        }
        Line line = new Line();
        marker.setNode(line);
        getPlotChildren().add(line);
        verticalMarkers.add(marker);
    }

    public void removeAllVerticalMarkers() {
        for (Data<?, ?> verticalMarker : verticalMarkers) {
            removeVerticalValueMarker(verticalMarker);
        }
        verticalMarkers.clear();
    }

    public void removeVerticalValueMarker(Data<?, ?> marker) {
        Objects.requireNonNull(marker, "the marker must not be null");
        if (marker.getNode() != null) {
            getPlotChildren().remove(marker.getNode());
            marker.setNode(null);
        }
    }

    //draws all the lines
    @Override
    protected void layoutPlotChildren() {
        counter++;
        super.layoutPlotChildren();
        for (int i = 0; i < horizontalMarkers.size(); i++) {
            Data<?, ?> horizontalMarker = horizontalMarkers.get(i);
            BurstTabMittelwert mittelwert = mittelwerte.get(i);
            Line line = (Line) horizontalMarker.getNode();
//            line.setStartX(0);
            line.setStartX(getXAxis().getDisplayPosition(mittelwert.getLowerBoundInGraph()));
//            line.setEndX(getBoundsInLocal().getWidth());
            line.setEndX(getXAxis().getDisplayPosition(mittelwert.getUpperBoundInGraph()));
            line.setStartY(getYAxis().getDisplayPosition(horizontalMarker.getYValue()) + 0.5);
            line.setEndY(line.getStartY());
            line.setStrokeWidth(3);
            line.toFront();
        }
        for (Data<?, ?> verticalMarker : verticalMarkers) {
            Line line = (Line) verticalMarker.getNode();
            line.setStartX(getXAxis().getDisplayPosition(verticalMarker.getXValue()));
            line.setEndX(line.getStartX());
            line.setStartY(getYAxis().getDisplayPosition(verticalMarker.getYValue()));
            line.setEndY(getBoundsInLocal().getHeight());
            line.setStrokeWidth(3);
            line.setStroke(Color.web("0xF3622D"));  //bad: hardcoded orange 
//            line.toFront();  //the black lines for mittelwerte are in front while this line is commented
        }
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }


}
