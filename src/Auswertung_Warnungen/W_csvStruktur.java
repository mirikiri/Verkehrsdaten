/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Auswertung_Warnungen;

import model.Messung;
import model.Paket;
import java.util.List;

/**
 *
 * @author Admin
 */
public class W_csvStruktur extends Warnung{

    public W_csvStruktur(String titel, String beschreibung) {
        super(titel, beschreibung);
    }

    @Override
    public void check_Trigger(Messung messung) {
        List<Paket> pakets = messung.getPakets();
        for (Paket paket : pakets) {
            if(paket.getProtocol() == Paket.Protocol.UNIDENTIFIED) {
                triggered = Boolean.TRUE;
                triggerText = "Die Struktur der eingelesenen csv-Datei ist fehlerhaft. Überprüfen Sie die Struktur und betrachten Sie bis dahin alle Daten mit größter Vorsicht";
                break;
            }
        }
        if (!triggered) {
            triggerText = "Keine Unregelmäßigkeiten in der Struktur der csv-Datei gefunden";
        }
    }

}
