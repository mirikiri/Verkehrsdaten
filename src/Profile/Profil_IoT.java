/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Profile;

import java.util.Collections;
import model.PaketToSend;

/**
 *
 * @author Tobias
 */
public class Profil_IoT extends Profil{
     public Profil_IoT(String name, Profil.profil_Type type, int dauer, int ankunftsrate) {
       super(name, type);
       final int contentLength[] = {80-42, 100-42};   //80 Byte und 100 Byte (Header kommt automatisch dazu)
       final int packageAmount[] = {60, 60};          // 1/(500*10^-3) = 2 Pakete/s -> 120 Pakete/min
        
       
       for (int i = 0; i < packageAmount.length; i++) {
          for (int j = 0; j < packageAmount[i]; j++) {
                packetslist.add(new PaketToSend(contentLength[i]));
            }
        }
       sleeptime = 500;
       Collections.shuffle(packetslist);
    }
}
