/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Profile;

import java.util.Arrays;
import java.util.Collections;
import model.PaketToSend;

/**
 *
 * @author Tobias
 */
public class Profil_Gleichverteilung extends Profil {
    
    public Profil_Gleichverteilung(String name, Profil.profil_Type type, int dauer, int ankunftsrate) {
       super(name, type);
       final int contentLength[] = {100-42, 200-42, 300-42, 400-42, 500-42, 600-42, 700-42, 800-42, 900-42, 1000-42, 1100-42, 1200-42, 1300-42, 1400-42, 1500-42};
       final int packageAmount[] = {133, 133, 133, 133, 133, 133, 133, 133, 133, 133, 133, 133, 133, 133, 133};
        
       
       for (int i = 0; i < packageAmount.length; i++) {
          for (int j = 0; j < packageAmount[i]; j++) {
                packetslist.add(new PaketToSend(contentLength[i]));
            }
        }
       sleeptime = 30;
       Collections.shuffle(packetslist);
    }
}
