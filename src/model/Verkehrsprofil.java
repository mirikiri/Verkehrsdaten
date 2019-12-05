/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class Verkehrsprofil {
    private final String name;
    private final List<Integer> paketsPerInterval;
    private final Integer length;
    private Integer oldTimesAdded;
    private final List<Integer> lastRandomValues;
    Integer worstCasePos;

    public Verkehrsprofil(String name, List<Integer> paketsPerInterval) {
        this.name = name;
        this.paketsPerInterval = paketsPerInterval;
        this.length = paketsPerInterval.size();
        this.oldTimesAdded = 0;
        this.lastRandomValues = new ArrayList<>();
        int max = 0;
        for (int i = 0; i < paketsPerInterval.size(); i++) {
            if (paketsPerInterval.get(i) >= max) {
                max = paketsPerInterval.get(i);
                worstCasePos = i;
            }
        }
    }

    public String getName() {
        return name;
    }

    public Integer getLength() {
        return length;
    }

    public List<Integer> getPaketsPerInterval() {
        return paketsPerInterval;
    }

    public Integer getOldTimesAdded() {
        return oldTimesAdded;
    }
    
    public void setOldTimesAdded(Integer oldTimesAdded) {
        this.oldTimesAdded = oldTimesAdded;
    }

    public List<Integer> getLastRandomValues() {
        return lastRandomValues;
    }

    public Integer getWorstCasePos() {
        return worstCasePos;
    }
    
    
    
}
