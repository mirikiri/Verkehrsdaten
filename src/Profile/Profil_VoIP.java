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
public class Profil_VoIP extends Profil {
    
    public Profil_VoIP(String name, Profil.profil_Type type, int dauer, int ankunftsrate) {
       super(name, type);
       final int contentLength[] = {220-42};  //220 mit 60 Byte Header (noch überprüfen ob 60 Byte oder 42 Byte Header)
       final int packageAmount[] = {3000};  // 1/(20*10^-3) = 50 Pakete/s -> 3000 Pakete/min
       
       for (int i = 0; i < packageAmount.length; i++) {
          for (int j = 0; j < packageAmount[i]; j++) {
                packetslist.add(new PaketToSend(contentLength[i]));
            }
        }
       sleeptime = 20;
       Collections.shuffle(packetslist);
      
    }
}
