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
public class PaketLength implements Comparable<PaketLength>{

    private Integer length;
    private int count;
    private Double percent;
    
    public PaketLength(int length) {
        this.length = length;
        this.count = 0;
        percent = 0.0;
    }
    
    public void countUp() {
        this.count++;
    }
    
        /**
     * @return the length
     */
    public Integer getLength() {
        return length;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @return the percent
     */
    public Double getPercent() {
        return percent;
    }

    /**
     * @param percent the percent to set
     */
    public void setPercent(Double percent) {
        this.percent = percent;
    }

    @Override
    public int compareTo(PaketLength o) {
        return this.getLength().compareTo(o.getLength());
    }

    /**
     * @param length the length to set
     */
    public void setLength(Integer length) {
        this.length = length;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

}
