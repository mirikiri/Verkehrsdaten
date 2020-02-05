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
public final class Zeitintervall {

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
    private final int desiredNumberOfChartPoints = 2000; //since most monitors use resolution of 1920x1080 hardly more points than 2000 are needed

    public Zeitintervall(String name, List<Integer> values, Integer sumUpFactor, Boolean sumUp, boolean bigFile, int bigFileReduceFaktor) {
        this.name = name + "s";
        paketsPerInterval = new ArrayList<>();
        Double nameAsDouble = Double.parseDouble(name);
        xyListe = FXCollections.observableArrayList();
        List<XYChart.Data<Number, Number>> chartPoints = new ArrayList<>();
        chartSeries = new XYChart.Series<>();
        List<Integer> bigFileReducedPakets = new ArrayList<>();

        //either sum up the given intervals for new intervals or reuse given intervals
        if (sumUp) {
            sumUpIntervals(values, sumUpFactor);
        } else {
            paketsPerInterval = values;
        }

        if (bigFile) {
            for (int i = 0; i < paketsPerInterval.size(); i+=bigFileReduceFaktor) {
                bigFileReducedPakets.add(paketsPerInterval.get(i));
            }
            paketsPerInterval = bigFileReducedPakets;
        }

        //create objects for display in data list next to graph
        for (int i = 0; i < paketsPerInterval.size(); i++) {
            xyListe.add(new PaketsPerTimeInterval((i + 1) * bigFileReduceFaktor * nameAsDouble, paketsPerInterval.get(i)));
        }

        //create the chart points for the graphs
        chartPoints.add(new XYChart.Data<>(0.0 * nameAsDouble, 0.0)); //origin point
        if (paketsPerInterval.size() <= (desiredNumberOfChartPoints * 2)) { // no downsampling needed when number of data points already low enough
            for (int i = 0; i < paketsPerInterval.size(); i++) {
                chartPoints.add(new XYChart.Data<>((i + 1) * bigFileReduceFaktor * nameAsDouble, paketsPerInterval.get(i)));
            }
        } else { //do downsampling to have fewer chart points for better performance
            downSample(chartPoints, nameAsDouble, bigFileReduceFaktor);
        }
        chartSeries.getData().addAll(chartPoints);

        //calculate minArrivalrate, maxArrivalrate, meanArrivalrate for later use
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

    public void sumUpIntervals(List<Integer> values, Integer sumUpFactor) {
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
        }
    }

    //function to downsample only the visual data in order to increase performance
    public void downSample(List chartPoints, Double interval, int bigFileFactor) {

//        List<XYChart.Data<Number, Number>> data = chartSeries.getData();
//        List<XYChart.Data<Number, Number>> downSampledChartPoints = new ArrayList<>();
//        downSampledChartPoints.add(new XYChart.Data<>(0.0, 0.0));
        double intervalLength = (double) paketsPerInterval.size() / (desiredNumberOfChartPoints / 2);
        double counter = 0.0;

        //since in every iteration a min and max are produced, loop only needs to go half the desired points
        for (int i = 0; i < (desiredNumberOfChartPoints / 2); i++) {
            int lb = (int) counter;
            int ub = (int) (counter + intervalLength) - 1;

//            Data<Number, Number> max = new Data<>(0, Integer.MIN_VALUE);
//            Data<Number, Number> min = new Data<>(0, Integer.MAX_VALUE);
            int max = Integer.MIN_VALUE;
            int maxPos = 0;
            int min = Integer.MAX_VALUE;
            int minPos = 0;
            //find min and max of current interval
            for (int j = lb; j < ub; j++) {
                if (paketsPerInterval.get(j) > max) {
                    max = paketsPerInterval.get(j);
                    maxPos = j;
                }
                if (paketsPerInterval.get(j) < min) {
                    min = paketsPerInterval.get(j);
                    minPos = j;
                }
            }
            //add min and max in the same order as they appear in the whole data
            if (maxPos < minPos) {
                chartPoints.add(new Data<>((maxPos + 1) * bigFileFactor * interval, max));
                chartPoints.add(new Data<>((minPos + 1) * bigFileFactor * interval, min));
            } else {
                chartPoints.add(new Data<>((minPos + 1) * bigFileFactor * interval, min));
                chartPoints.add(new Data<>((maxPos + 1) * bigFileFactor * interval, max));
            }
            counter += intervalLength;
        }

        downsampled = true;
    }

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
        return Math.round(sum / (upperBound - lowerBound + 1) * 100d) / 100d;
    }

    public double getMaxPeak() {
        return maxPeak;
    }

    public boolean getDownsampled() {
        return downsampled;
    }
}
