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
 * @author Admin
 */
public class W_meanPaketLength extends Warnung {

    double threshold = 100.0;

    public W_meanPaketLength(String titel, String beschreibung) {
        super(titel, beschreibung);
    }

    @Override
    public void check_Trigger(Messung messung) {
        double meanLengthTotal = messung.getMeanPaketLength(Paket.Protocol.TOTAL);
        int mostUsedLengthTotal = messung.getMostUsedPaket(Paket.Protocol.TOTAL).getLength();
        double differenceTotal = Math.abs(meanLengthTotal - mostUsedLengthTotal);

        double meanLengthUDP = messung.getMeanPaketLength(Paket.Protocol.UDP);
        int mostUsedLengthUDP = messung.getMostUsedPaket(Paket.Protocol.UDP).getLength();
        double differenceUDP = Math.abs(meanLengthUDP - mostUsedLengthUDP);

        double meanLengthTCP = messung.getMeanPaketLength(Paket.Protocol.TCP);
        int mostUsedLengthTCP = messung.getMostUsedPaket(Paket.Protocol.TCP).getLength();
        double differenceTCP = Math.abs(meanLengthTCP - mostUsedLengthTCP);

        double meanLengthOther = messung.getMeanPaketLength(Paket.Protocol.OTHER);
        int mostUsedLengthOther = messung.getMostUsedPaket(Paket.Protocol.OTHER).getLength();
        double differenceOther = Math.abs(meanLengthOther - mostUsedLengthOther);

        if (differenceTotal > threshold || differenceUDP > threshold || differenceTCP > threshold || differenceUDP > threshold) {
            triggered = Boolean.TRUE;
            triggerText = "Mittlere Paketlänge und häufigste Paketlänge liegen über " + threshold + " Byte auseinander bei:";
            if (differenceUDP > threshold) {
                triggerText += " UDP,";
            }
            if (differenceTCP > threshold) {
                triggerText += " TCP,";
            }
            if (differenceOther > threshold) {
                triggerText += " Andere,";
            }
            if (differenceTotal > threshold) {
                triggerText += " Total,";
            }
            if (triggerText != null && triggerText.length() > 0) {
                triggerText = triggerText.substring(0, triggerText.length() - 1);
                triggerText += ".";
            }
        } else {
            triggerText = "Mittlere Paketlänge und häufigste Paketlänge liegen für alle Protokolle nahe beieinander";
        }
    }

}
