/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Admin
 */
public class AnonymizePCAPNG {
    
    public void anonymize(List<Paket> pakets, String path) {
        File file = new File(path + ".csv");
        FileWriter fr = null;
        BufferedWriter br = null;
        try{
            fr = new FileWriter(file);
            br = new BufferedWriter(fr);
            for (Paket paket : pakets) {
                //Each line should look like: Timestamp,paketlength,protocol,protocol,protocol,protocol
                String strToWrite = Double.toString(paket.getTimestamp()) + "," + Integer.toString(paket.getPaketlength()) + ",";
                switch(paket.getProtocol()) {
                    //just adding a 1 into the right place. no idea what protocol numbers from tshark mean, since only location seems to be important
                    //therefore better only use anonymized files with this software
                    case UDP:
                        strToWrite += "1,,,";
                        break;
                    case TCP:
                        strToWrite += ",,,1";
                        break;
                    case OTHER:
                        strToWrite += ",,,";
                        break;
                }
                //add end-of-line after each paket
                strToWrite += System.getProperty("line.separator");
                br.write(strToWrite);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
