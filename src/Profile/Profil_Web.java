/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Profile;

import java.util.ArrayList;
import java.util.Random;
import model.PaketToSend;

/**
 *
 * @author Admin
 */
public class Profil_Web extends Profil {

    public Profil_Web(String name, Profil.profil_Type type) {
        super(name, type);
        pakets = new ArrayList<>();
        final int contentLength[] = {18, 1458};
        int totalDuration = 60000; //Gesamtdauer = 60 Sekunden
        Random rnd = new Random();
        
        while (totalDuration > 0) { //füge abwechselnd bursts und ruhezeiten hinzu, bis die gesamtdauer aufgebraucht ist
            if (rnd.nextInt(2) == 1) {
                totalDuration -= addBurst(rnd, contentLength);
            } else {
                totalDuration -= addQuietPhase(rnd, contentLength);
            }
        }
        
        while (totalDuration < 0) { //entferne solange das letzte Paket, bis die Gesamtdauer wieder positiv ist (also die Profil Dauer gerade kleiner als 60 Sekunden ist)        
            totalDuration += removeLastPaket();
        }
        pakets.get(pakets.size() - 1).setWaitTime(0); // nach dem letzten Paket muss nicht mehr gewartet werden
        
        System.out.println(pakets.size() + " Pakete");
        System.out.println("");
        for (PaketToSend paket : pakets) {
            System.out.print(paket.getWaitTime() + ", ");
        }
    }

    private int addBurst(Random rnd, int[] contentLengthArray) {
        int ankunftsrate = rnd.nextInt(101) + 50;  // random wert zwischen 50 und 150 (Paketen/s)
        int sleeptime = Math.round((float) 1000 / ankunftsrate);
        int dauer = rnd.nextInt(51) + 50; // random wert zwischen 50 und 100 (Milli-Sekunden)

        int duration = addPakets(dauer, sleeptime, contentLengthArray, rnd);
        return duration;
    }

    private int addQuietPhase(Random rnd, int[] contentLengthArray) {
        int ankunftsrate = rnd.nextInt(11) + 2;  // random wert zwischen 2 und 12 (Paketen/s)
        int sleeptime = Math.round((float) 1000 / ankunftsrate);
        int dauer = rnd.nextInt(4801) + 200; // random wert zwischen 200 und 5000 (Milli-Sekunden)

        int duration = addPakets(dauer, sleeptime, contentLengthArray, rnd);
        return duration;
    }

    private int addPakets(int dauer, int sleeptime, int[] contentLengthArray, Random rnd) {
        int count = 0;
        for (int i = dauer; i > 0; i -= sleeptime) {
            int contentLength = contentLengthArray[rnd.nextInt(contentLengthArray.length)];
            pakets.add(new PaketToSend(contentLength, sleeptime));
            count++;
        }
        return count * sleeptime;
    }
    
    private int removeLastPaket() {
        int time = pakets.get(pakets.size() - 2).getWaitTime();
        pakets.remove(pakets.size() - 1);
        return time;
    }
}
