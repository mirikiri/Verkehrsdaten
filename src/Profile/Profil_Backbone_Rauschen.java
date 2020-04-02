/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Profile;

import java.util.Collections;
import java.util.Scanner;
import model.PaketToSend;

/**
 *
 * @author Tobias
 */
public class Profil_Backbone_Rauschen extends Profil{
    
    //System.out.println("Geben Sie einen Skalierungsfaktor ein! ");
    Scanner sc = new Scanner(System.in);
    int skalierung = sc.nextInt();
    
     public Profil_Backbone_Rauschen(String name, Profil.profil_Type type, int dauer, int ankunftsrate) {
       super(name, type);
       final int contentLength[] = {60-42, 64-42, 1506-42, 1510-42};
       final int packageAmount[] = {720, 400, 100, 780};        //nochmal nachfragen, wie viele Pakete gesendet werden sollen, 
                                                                //ist nicht direkt aus marius bericht rauszulesen
        
       
       for (int i = 0; i < packageAmount.length; i++) {
          for (int j = 0; j < packageAmount[i]; j++) {
                packetslist.add(new PaketToSend(contentLength[i]));
            }
        }
       sleeptime = 100/skalierung;
       Collections.shuffle(packetslist);
    }
}
