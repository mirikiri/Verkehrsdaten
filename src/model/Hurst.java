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
public class Hurst {

    private final String methode;
    private final String full_name;
    private final Double hurst_wert;
    
    public Hurst(String methode, String full_name, Double hurst_wert) {
        this.methode = methode;
        this.full_name = full_name;
        this.hurst_wert = hurst_wert;
    }
    
    
        /**
     * @return the methode
     */
    public String getMethode() {
        return methode;
    }

    /**
     * @return the hurst_wert
     */
    public Double getHurst_wert() {
        return hurst_wert;
    }

    /**
     * @return the full_name
     */
    public String getFull_name() {
        return full_name;
    }
}
