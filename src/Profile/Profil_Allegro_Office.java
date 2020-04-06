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
public class Profil_Allegro_Office extends Profil {
 
     public Profil_Allegro_Office(String name, Profil.profil_Type type, int dauer, int ankunftsrate) {
       super(name, type);
       final int contentLength[] = {86, 214, 66, 105, 60, 62, 75, 170, 285, 385, 500, 690, 1415};
       final int packageAmount[] = {1233, 1477, 420, 367, 133, 187, 233, 380, 67, 67, 67, 67, 220};
        
       
       for (int i = 0; i < packageAmount.length; i++) {
          for (int j = 0; j < packageAmount[i]; j++) {
                packetslist.add(new PaketToSend(contentLength[i]));
            }
        }
       sleeptime = 3;
       Collections.shuffle(packetslist);
    }
}
