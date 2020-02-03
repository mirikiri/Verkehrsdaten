/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filereader;

import model.Paket;
import com.opencsv.CSVReader;
import java.io.File;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author Admin
 */
public class ReadWireSharkCSV {

    public List<Paket> readFile(String path) {
        List<Paket> pakets = null;
        File file = new File(path);
        
        //check the file size. if file is big, display confirmation dialog to offer cancellation
        int fileLength = (int)file.length() / 1000000; //round file size to MB
        if (fileLength > 20) { //file bigger than 20 MB
            if(showAlert(fileLength, fileLength/18) == ButtonType.CANCEL) { //user pressed cancel
                return pakets;
            }
        }
        
        pakets = new ArrayList<>();
        CSVReader reader;
        try {
            reader = new CSVReader(new FileReader(file));     
            String[] line;
            int count = 0;
            while ((line = reader.readNext()) != null) {
                count++;
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
            System.out.println("something went wrong while reading the file");
        }
        return pakets;
    }
    
    public ButtonType showAlert(int size, int duration) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/Pictures/icon.jpg").toString()));
        alert.setHeaderText("Große Datei erkannt!");
        alert.setContentText("Ihre ausgewählte Datei ist " + size + " MB groß. Dadurch kann das Einlesen " + duration + " Sekunden oder länger dauern. \n\nFortfahren ?");
        
        Optional<ButtonType> result = alert.showAndWait();
        return result.get();
    }
}
