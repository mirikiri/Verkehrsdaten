/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_Window;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Miriam
 */
public class HilfeWindowController implements Initializable {

    @FXML
    private TextArea AnleitungArea;
    @FXML
    private TextArea InfoArea;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        AnleitungArea.setText("\n1. Wählen Sie im ersten Aufklappmenü den Signaltyp aus.\n\n" +
                "2. Wählen Sie im zweiten den gewünschten Datengenerator aus.\n\n" +
                "3. Bestätigen Sie Ihre Auswahl mit ✓.\n\n" +
                "4. Geben Sie die Parameter ein.\n\n" +
                "5. Schicken Sie das Signal mit dem Button \"Testsignal senden\" ab.\n\n" +
                "6. Das abgeschickte Signal wird ebenfalls in der Datenbank gespeichert." +
                " Diese Einträge können bei längerem Berühren und Betätigen der oben erscheinenden Tonne wieder gelöscht werden.\n\n" +
                "Info: Es können auch Signale abgeschickt werden, solange andere Testsignalabläufe noch im Gange sind.\n");
        
        InfoArea.setText("\nIndustrie 1:\n" +
                        " - 1x Rauschen            \n" +
                        " - 1x Excel               \n" +
                        " - 1x Outlook Start       \n" +
                        " - 1x Print               \n" +
                        " - 1x Skype Shared Screen \n" +
                        " - 1x VoIP                \n" +
                        " - 1x Webseitenaufruf     \n\n" +

                        "Industrie 2:\n" +
                        " - 1x Rauschen            \n" +
                        " - 3x ES_Office           \n" +
                        " - 2x VoIP_Multi          \n\n" +

                        "Industrie 3:\n" +
                        " - 1x Rauschen             \n" +
                        " - 1x ES_Office            \n" +
                        " - 1x Excel                \n" +
                        " - 1x Outlook Start        \n" +
                        " - 1x Print                \n" +
                        " - 1x Remote_Desktop       \n" +
                        " - 1x VoIP_Multi           \n" +
                        " - 1x Webseitenaufruf      \n\n" +

                        "Privat:\n" +
                        " - 1x Rauschen             \n" +
                        " - 1x Outlook Start        \n" +
                        " - 1x Print                \n" +
                        " - 1x Skype Shared Screen  \n" +
                        " - 1x Webseitenaufruf      \n" +
                        " - 1x Youtube2160p         \n\n" +

                        "Multimedia 1:\n" +
                        " - 1x Rauschen             \n" +
                        " - 2x Gaming               \n" +
                        " - 2x VoIP                 \n" +
                        " - 1x Youtube720p         \n\n" +

                        "Multimedia 2:\n" +
                        " - 1x Rauschen             \n" +
                        " - 1x Gaming               \n" +
                        " - 3x Webseitenaufruf      \n" +
                        " - 1x Youtube240p          \n" +
                        " - 1x Youtube720p          \n" +
                        " - 1x Youtube2160p         \n\n");
        
    }    
    
}
