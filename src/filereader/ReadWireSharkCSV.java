/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filereader;

import model.Paket;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ReadWireSharkCSV {

    public List<Paket> readFile(String path) {
        List<Paket> pakets = new ArrayList<>();
        CSVReader reader;
        try {
            reader = new CSVReader(new FileReader(path));
            String[] line;
            while ((line = reader.readNext()) != null) {
                Paket.Protocol protocol;
                if (line.length != 6) {
                    protocol = Paket.Protocol.UNIDENTIFIED;
                } else if (!"".equals(line[2])) {
                    protocol = Paket.Protocol.UDP;
                } else if (!"".equals(line[5])) {
                    protocol = Paket.Protocol.TCP;
                } else {
                    protocol = Paket.Protocol.OTHER;
                }
                pakets.add(new Paket(Double.parseDouble(line[0]), Integer.parseInt(line[1]), protocol));
            }
        } catch (IOException e) {
        }
        return pakets;
    }
}
