/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Auswertung_Warnungen;

import model.Messung;
import model.PaketLength;
import java.util.List;

/**
 *
 * @author Florian
 */
public class W_kleinePakete extends Warnung {
    
    int threshold_byte = 100;
    double threshold = 0.3;

    public W_kleinePakete(String titel, String beschreibung) {
        super(titel, beschreibung);
    }

    @Override
    public void check_Trigger(Messung messung) {
        List<PaketLength> paketlengths = messung.getPaketlengthHisto_total().getPaketlengths();
        long totalPakets = messung.getNumberOfPakets();
        int smallerthanThreshold = 0;
        for (int i = 0; i < threshold_byte + 1; i++) {
            smallerthanThreshold += paketlengths.get(i).getCount();
        }
        double percent = Math.round((double)smallerthanThreshold / totalPakets *10000d) / 100d;
        if ( percent > threshold*100) {
            triggered = Boolean.TRUE;
            triggerText = Double.toString(percent) + " % aller Pakete (= " + Integer.toString(smallerthanThreshold) + " Pakete) sind kleiner als " + Integer.toString(threshold_byte) + " Byte";
        } else {
            triggerText = "Nur " + Double.toString(percent) + " % aller Pakete (= " + Integer.toString(smallerthanThreshold) + " Pakete) sind kleiner als " + Integer.toString(threshold_byte) + " Byte";
        }
        
    }
}
