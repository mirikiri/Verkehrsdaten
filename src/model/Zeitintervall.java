/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;

/**
 *
 * @author Admin
 */
public class Zeitintervall {

    private final String name;
    private List<Integer> paketsPerInterval;
    private final ObservableList<PaketsPerTimeInterval> xyListe;
    private final XYChart.Series<Number, Number> chartSeries;
    private boolean downsampled = false;
    private final ObservableList<Hurst> hurst_Faktoren = FXCollections.observableArrayList();
    private int minArrivalrate;
    private int maxArrivalrate;
    private final double meanArrivalRate;
    private final double maxPeak;

    public Zeitintervall(String name, List<Integer> values, Integer sumUpFactor, Boolean sumUp) {
        this.name = name + "s";
        paketsPerInterval = new ArrayList<>();
        Double nameAsDouble = Double.parseDouble(name);

        if (sumUp) {
            int counter = 0;
            for (int i = 0; i < values.size(); i++) {
                counter += values.get(i);
                if (i % sumUpFactor == sumUpFactor - 1) {
                    paketsPerInterval.add(counter);
                    counter = 0;
                }
            }
            if (counter > 0) {
                paketsPerInterval.add(counter);
                counter = 0;
            }
        } else {
            paketsPerInterval = values;
        }

        List<XYChart.Data<Number, Number>> chartPoints = new ArrayList<>();
        xyListe = FXCollections.observableArrayList();
        chartPoints.add(new XYChart.Data<>(0.0 * nameAsDouble, 0.0));
        for (int i = 0; i < paketsPerInterval.size(); i++) {
            xyListe.add(new PaketsPerTimeInterval((i + 1) * nameAsDouble, paketsPerInterval.get(i)));
            chartPoints.add(new XYChart.Data<>((i + 1) * nameAsDouble, paketsPerInterval.get(i)));
        }

        chartSeries = new XYChart.Series<>();
        chartSeries.getData().addAll(chartPoints);

        //initialize minArrivalrate, maxArrivalrate, meanArrivalrate
        Integer sum = 0;
        minArrivalrate = Integer.MAX_VALUE;
        maxArrivalrate = Integer.MIN_VALUE;
        for (Integer interval : paketsPerInterval) {
            if (interval < minArrivalrate) {
                minArrivalrate = interval;
            }
            if (interval > maxArrivalrate) {
                maxArrivalrate = interval;
            }
            sum += interval;
        }
        meanArrivalRate = Math.round((double) sum / paketsPerInterval.size() * 100d) / 100d;
        maxPeak = Math.round(((double) maxArrivalrate / meanArrivalRate - 1) * 10000d) / 100d;
    }

    /**
     * @return the _name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the paketsPerInterval
     */
    public List<Integer> getPaketsPerInterval() {
        return paketsPerInterval;
    }

    /**
     * @return the chartSeries
     */
    public ObservableList<XYChart.Series<Number, Number>> getChartSeries() {
        return FXCollections.observableArrayList(Collections.singleton(chartSeries));
    }

    public XYChart.Series<Number, Number> getChartSeriesRaw() {
        return chartSeries;
    }

    /**
     * @return the hurst_Faktoren
     */
    public ObservableList<Hurst> getHurst_Faktoren() {
        return hurst_Faktoren;
    }

    /**
     * @return the xyListe
     */
    public ObservableList<PaketsPerTimeInterval> getXyListe() {
        return xyListe;
    }

    public int getMinArrivalrate() {
        return minArrivalrate;
    }

    public int getMaxArrivalrate() {
        return maxArrivalrate;
    }

    public double getMeanArrivalRate() {
        return meanArrivalRate;
    }

    public double getMeanArrivalRate(int lowerBound, int upperBound) {      
        if (upperBound >= paketsPerInterval.size()) {
            System.out.println("upperBound greater than list size");
            return -1;
        }
        double sum = 0.0;
        for (int i = lowerBound; i <= upperBound; i++) {
            sum += paketsPerInterval.get(i);
        }
        return Math.round(sum / (upperBound - lowerBound +1) *100d) / 100d ;
    }

    public double getMaxPeak() {
        return maxPeak;
    }
    
    public boolean getDownsampled() {
        return downsampled;
    }
    
    //function to downsample only the visual data in order to increase performance
    public void downSample() {
        //since most monitors use resolution of 1920x1080 hardly more points than 2000 are needed
        int desiredNumberOfDataPoints = 2000;
        // if series is shorter than 4000 points, downsampling is not really needed
        if (chartSeries.getData().size() < desiredNumberOfDataPoints*2) {
            return;
        }
        List<XYChart.Data<Number,Number>> data = chartSeries.getData();
        List<XYChart.Data<Number, Number>> downSampledChartPoints = new ArrayList<>();
        downSampledChartPoints.add(new XYChart.Data<>(0.0, 0.0));
        double intervalLength = (double) data.size() / (desiredNumberOfDataPoints / 2);
        double counter = 0.0;
        
        //since in every iteration a min and max are produced, loop only needs to go half the desired points
        for (int i = 0; i < (desiredNumberOfDataPoints/2); i++) {
            int lb = (int) counter;
            int ub = (int) (counter + intervalLength) - 1;
            
            Data<Number, Number> max = new Data<>(0,Integer.MIN_VALUE);
            Data<Number, Number> min = new Data<>(0, Integer.MAX_VALUE);
            //find min and max of current interval
            for (int j = lb; j < ub; j++) {
                if (data.get(j).getYValue().intValue() > max.getYValue().intValue()) {
                    max = data.get(j);
                }
                if (data.get(j).getYValue().intValue() < min.getYValue().intValue()) {
                    min = data.get(j);
                }
            }
            //add min and max in the same order as they appear in the whole data
            if(max.getXValue().doubleValue() < min.getXValue().doubleValue()) {
                downSampledChartPoints.add(new Data<>(max.getXValue(), max.getYValue()));
                downSampledChartPoints.add(new Data<>(min.getXValue(), min.getYValue()));
            } else {
                downSampledChartPoints.add(new Data<>(min.getXValue(), min.getYValue()));
                downSampledChartPoints.add(new Data<>(max.getXValue(), max.getYValue()));
            }
            counter += intervalLength;
        }
        chartSeries.getData().clear();
        chartSeries.getData().addAll(downSampledChartPoints);
        
        downsampled = true;
    }

}
