/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filereader;

import model.Paket;
import fr.bmartel.pcapdecoder.PcapDecoder;
import fr.bmartel.pcapdecoder.structure.types.IPcapngType;
import fr.bmartel.pcapdecoder.structure.types.inter.IDescriptionBlock;
import fr.bmartel.pcapdecoder.structure.types.inter.IEnhancedPacketBLock;
import fr.bmartel.pcapdecoder.structure.types.inter.INameResolutionBlock;
import fr.bmartel.pcapdecoder.structure.types.inter.ISectionHeaderBlock;
import fr.bmartel.pcapdecoder.structure.types.inter.IStatisticsBlock;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Admin
 */
public class Read_pcapng {

    public List<Paket> readFile(String path) {
        int[] POWERS_OF_10 = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};
        int timeStampResolution = 1;
        List<Paket> pakets = null;
        File file = new File(path);

        //check the file size. if file is big, display confirmation dialog to offer cancellation
        int fileLength = (int) file.length() / 1000000; //round file size to MB
        if (fileLength > 1000) { //file bigger than 1000 MB
            if (showAlert(fileLength, fileLength / 1000) == ButtonType.CANCEL) { //user pressed cancel
                return pakets;
            }
        }
        
        pakets = new ArrayList<>();

        PcapDecoder decoder = new PcapDecoder(path);
        decoder.decode();
        ArrayList<IPcapngType> sectionList = decoder.getSectionList();

        for (IPcapngType element : sectionList) {
            if (element instanceof ISectionHeaderBlock) {
                ISectionHeaderBlock section = (ISectionHeaderBlock) element;
                //do what you want with Section Header Block frame type
            } else if (element instanceof IDescriptionBlock) {
                IDescriptionBlock section = (IDescriptionBlock) element;
                timeStampResolution = section.getOptions().getTimeStampResolution();
            } else if (element instanceof IEnhancedPacketBLock) {
                IEnhancedPacketBLock section = (IEnhancedPacketBLock) element;
                Double timestamp;
                if (pakets.size() > 0) {
                    timestamp = (double) (section.getTimeStamp() - pakets.get(0).getTimestamp_absolute()) / POWERS_OF_10[timeStampResolution];
                } else {
                    timestamp = 0.0;
                }
                int protcolNR = section.getPacketData()[23];
                Paket.Protocol protocol;
                switch (protcolNR) {
                    case 17:
                        protocol = Paket.Protocol.UDP;
                        break;
                    case 6:
                        protocol = Paket.Protocol.TCP;
                        break;
                    default:
                        protocol = Paket.Protocol.OTHER;
                        break;
                }
                pakets.add(new Paket(timestamp, section.getPacketLength(), protocol, section.getTimeStamp()));
            } else if (element instanceof IStatisticsBlock) {
                IStatisticsBlock section = (IStatisticsBlock) element;
                //do what you want with Statistics Block frame type 
            } else if (element instanceof INameResolutionBlock) {
                INameResolutionBlock section = (INameResolutionBlock) element;
                //do what you want with Name Resolution Block frame type
            }
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
