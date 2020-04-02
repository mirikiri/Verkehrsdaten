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
public class Profil_Spotify extends Profil {
    
     public Profil_Spotify(String name, Profil.profil_Type type, int dauer, int ankunftsrate) {
       super(name, type);
       final int contentLength[] = {24, 35, 45, 53, 73, 1252};
       final int packageAmount[] = {145, 17, 10, 25, 45, 210};
        
       
       for (int i = 0; i < packageAmount.length; i++) {
          for (int j = 0; j < packageAmount[i]; j++) {
                packetslist.add(new PaketToSend(contentLength[i]));
            }
        }
       sleeptime = 45;
       Collections.shuffle(packetslist);
    }
}
