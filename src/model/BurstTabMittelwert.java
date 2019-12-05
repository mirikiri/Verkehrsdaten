/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Admin
 */
public class BurstTabMittelwert {
    private final double value;
    private final int lowerBound;
    private final int upperBound;
    private double lowerBoundInGraph;
    private double upperBoundInGraph;
    private final int lb_inSeries;
    private final int ub_inSeries;

    public BurstTabMittelwert(double value, int lowerBound, int upperBound, String zeitIntervallName) {
        this.value = value;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        if (null != zeitIntervallName) switch (zeitIntervallName) {
            case "0.01s":
                lowerBoundInGraph = lowerBound / 100d;
                upperBoundInGraph = upperBound / 100d;
                break;
            case "0.1s":
                lowerBoundInGraph = lowerBound / 10d;
                upperBoundInGraph = upperBound / 10d;
                break;
            case "1s":
                lowerBoundInGraph = lowerBound;
                upperBoundInGraph = upperBound;
                break;
            case "10s":
                lowerBoundInGraph = lowerBound * 10d;
                upperBoundInGraph = upperBound * 10d;
                break;
            case "60s":
                lowerBoundInGraph = lowerBound * 60d;
                upperBoundInGraph = upperBound *60d;
                break;
            default:
                break;
        }
        this.lb_inSeries = lowerBound;
        this.ub_inSeries = upperBound;
    }
    
    public BurstTabMittelwert(double value, int lowerBound, int upperBound, String zeitIntervallName, int lb_inSeries, int ub_inSeries) {
        this.value = value;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        if (null != zeitIntervallName) switch (zeitIntervallName) {
            case "0.01s":
                lowerBoundInGraph = lowerBound / 100d;
                upperBoundInGraph = upperBound / 100d;
                break;
            case "0.1s":
                lowerBoundInGraph = lowerBound / 10d;
                upperBoundInGraph = upperBound / 10d;
                break;
            case "1s":
                lowerBoundInGraph = lowerBound;
                upperBoundInGraph = upperBound;
                break;
            case "10s":
                lowerBoundInGraph = lowerBound * 10d;
                upperBoundInGraph = upperBound * 10d;
                break;
            case "60s":
                lowerBoundInGraph = lowerBound * 60d;
                upperBoundInGraph = upperBound *60d;
                break;
            default:
                break;
        }
        this.lb_inSeries = lb_inSeries;
        this.ub_inSeries = ub_inSeries;
    }

    public double getValue() {
        return value;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public double getLowerBoundInGraph() {
        return lowerBoundInGraph;
    }

    public double getUpperBoundInGraph() {
        return upperBoundInGraph;
    }

    public int getLb_inSeries() {
        return lb_inSeries;
    }

    public int getUb_inSeries() {
        return ub_inSeries;
    }
    
    
    
}
