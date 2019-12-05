/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Auswertung_Warnungen;

import model.Messung;
import model.Paket;

/**
 *
 * @author Florian
 */
public class W_jumboPakete extends Warnung {

    public W_jumboPakete(String titel, String beschreibung) {
        super(titel, beschreibung);
    }

    @Override
    public void check_Trigger(Messung messung) {
        int biggerThan1560Byte = messung.getNumberPaketsBigger1560Byte(Paket.Protocol.TOTAL);
        if (biggerThan1560Byte > 0) {
            triggered = Boolean.TRUE;
            triggerText = "In der Messung sind " + Integer.toString(biggerThan1560Byte) + " Pakete größer als 1560 Byte (= Jumbo-Pakete) enthalten.";
        } else {
            triggerText = "In der Messung wurden keine Jumbo-Pakete gefunden.";
        }
        
    }

}
