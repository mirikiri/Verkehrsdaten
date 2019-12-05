/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Auswertung_Warnungen;

import model.Messung;
import model.Zeitintervall;

/**
 *
 * @author Admin
 */
public class W_peakAnkunftsrate extends Warnung {
    
    private double threshold = 1000.0;

    public W_peakAnkunftsrate(String titel, String beschreibung) {
        super(titel, beschreibung);
    }

    @Override
    public void check_Trigger(Messung messung) {
        double maxPeak = Double.MIN_VALUE;
        for (Zeitintervall zeitintervall : messung.getZeitintervalle()) {
            if (zeitintervall.getMaxPeak() > maxPeak) {
                maxPeak = zeitintervall.getMaxPeak();
            }
        }
        if (maxPeak >= threshold) {
            triggered = Boolean.TRUE;
            triggerText = "Ein Peak in der Ankunftsrate ist " + maxPeak + "% höher als die mittlere Ankunftsrate";
        } else {
            triggerText = "Der größte Peak ist " + maxPeak + "% höher als die mittlere Ankunftsrate";
        }
    }
    
}
