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
public class PaketsPerTimeInterval {

    private final String time;
    private final Integer count;

    public PaketsPerTimeInterval(Double time, Integer count) {

        this.time = Double.toString(Math.round(time * 100d) / 100d);
        this.count = count;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @return the count
     */
    public Integer getCount() {
        return count;
    }

}
