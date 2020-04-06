/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Profile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.PaketToSend;

/**
 *
 * @author Tobias
 */
public class Profil_Gauss extends Profil{
    
     public Profil_Gauss(String name, Profil.profil_Type type, int dauer, int ankunftsrate) {
       super(name, type);
       
        double mittel = 750-42;				//Erwartungswert
        double stdabw = 600;				//Standardabweichung					
        double Normierungsfaktor = 4000;	
        double wurzprod = Math.sqrt(2*3.14);
        double Multiplikator = (1/(stdabw*wurzprod)) * Normierungsfaktor;
        double gaussian = 0;
	      
        List<PaketToSend> packetslist = new ArrayList<>();
        
       
        System.out.println("Start");

        for(int i = 0; i < Normierungsfaktor; i++){
            gaussian = Multiplikator * Math.exp(-0.5*Math.pow(((i-mittel)/stdabw),2));
            gaussian *= 10;

            //int gaussian_round = (int) Math.round(gaussian);
            
            //gaussliste.add(new Double(gaussian));
            
             for(int j = 0; j < gaussian; j++){
               
                 if(i >= 18 && i <= 1472){
                    packetslist.add(new PaketToSend(i));  
                 }
             }
            //packetslist.add(new PacketToSend(gaussian_round));
        }       
       sleeptime = 1;
       
    }
}

