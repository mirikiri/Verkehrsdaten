/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Profile;

import filereader.Read_pcapng;
import static java.lang.Thread.sleep;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import model.Paket;
import model.PaketToSend;

/**
 *
 * @author Tobias
 */
public class Profil_Signal_Generator extends Profil {
    
    public Profil_Signal_Generator(String name, Profil.profil_Type type, int dauer, int ankunftsrate) throws InterruptedException {
       super(name, type);
       String path = "Spotify.pcapng";
       Read_pcapng pcapngReader = new Read_pcapng();
       List<Paket> packages = new ArrayList<>();
       packages = pcapngReader.readFile(path);
      
        HashMap<Integer, Integer> packageLengthList = new HashMap(); 
        int chartPaketLength = 0;
        
        for(Paket paket: packages){
            chartPaketLength = paket.getPaketlength();
            if(!packageLengthList.containsKey(chartPaketLength))
                packageLengthList.put(chartPaketLength, 1);          
            else
                packageLengthList.put(chartPaketLength, packageLengthList.get(chartPaketLength) + 1);                        
        }
        
        HashMap<Integer, Integer> arrival = new HashMap(); 
      
        for(Paket timestamps: packages){
            timestamp_current = timestamps.getTimestamp();
            timestamp_difference = timestamp_current - timestamp_old;
            timestamp_difference_all += timestamp_difference;
            timestamp_old = timestamp_current;
            
            package_counter++;
            if(timestamp_difference_all >= 1){
                package_duration++;
                arrival.put(package_duration, package_counter);
                timestamp_difference_all = 0;
                package_counter = 0;
            }   
        }
    }
}


