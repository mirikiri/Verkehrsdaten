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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Miriam
 */
public class HilfeWindowVC implements Initializable {

    @FXML
    private Label label_Anleitung_Profil;
    @FXML
    private Label label_Anleitung_Messung;
    @FXML
    private Label label_Anleitung_Simulation;
    @FXML
    private Label label_Info;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        label_Anleitung_Profil.setText("" +
                "Wählen Sie im ersten Aufklappmenü aus, ob Sie das neue Profil selbst zusammenstellen oder aus einer Messung generieren lassen wollen.\n" +
                "A) Aus Messung generieren: \n" +
                "    1. Klicken Sie auf den Button \"Aus Datei generieren\"\n" +
                "    2. Es öffnet sich ein Explorer, indem Sie ihre gewünschte Vorlage suchen und bestätigen.\n" +
                "    3. Geben Sie ihrem neuen Profil einen Namen und drücken auf \"Speichern\".\n" +
                "    4. Nun wird im Hintergrund ein neues Profil aus ihrer Vorlage erstellt, welches Sie für ihre Messungen nutzen können.\n" +
                "B) Selbst zusammenstellen:\n" +
                "    1. Wählen Sie im zweiten Aufklappmenü Ihren gewünschten Signaltyp aus.\n" +
                "    2. Geben Sie die Anzahl an, wie oft dieser Signaltyp vorhanden sein soll.\n" +
                "    3. Bestätigen Sie dies mit \"Hinzufügen\".\n" +
                "    4. Wenn Sie verschiedene Signaltypen hinzufügen möchten, wiederholen Sie die Schritte 1, 2 und 3 bis Ihr Profil ihren Wünschen entspricht, oder die maximale Anzahl (11) an Signalen erreicht.\n" +
                "    5. In der Tabelle können Sie überprüfen, welche Signale im neuen Profil bereits vorhanden sind.\n" +
                "    6. Falls ein Signal fälschlicherweise eingegeben wurde oder nicht mehr benötigt wird, kann es durch einen Klick auf das danebenstehende Mülltonnen-Symbol aus der Liste gelöscht werden.\n" +
                "    7. Geben Sie abschließend ihrem neuen Profil einen Namen und drücken auf \"Speichern\".\n" +
                "    8. Nun wird im Hintergrund diese Profilzusammenstellung abgelegt, sodass es beim Durchführen von Messungen zur Verfügung steht.\n\n");
        
        label_Anleitung_Messung.setText("" +
                "1. Wählen Sie im ersten Aufklappmenü die Messungsart (Einzel-, Multisignal oder Verkehrsprofil) aus.\n" +
                "2. Wählen Sie im zweiten den gewünschten Signaltyp aus.\n" +
                "3. Wählen Sie im dritten den/die Generatoren aus. (Bei Verkehrsprofilen nicht benötigt)\n" +
                "4. Geben Sie Dauer in Minuten ein. (bei Web und Rauschen fallen zusätzliche Parameter an)\n" +
                "5. Schicken Sie das Signal mit dem Button \"Starten\" ab.\n\n");
        
        label_Anleitung_Simulation.setText("" +
                "Wählen Sie eine bestehende Messung als Basis aus.\n" +
                "Nun können Sie auf der linken Seite mit Ziffern die Anzahl der zu addierenden Siganle angeben.\n" + 
                "das Fenster zeigt ihnen an, wie der Originalverlauf im Vergleich zu dem mit zusätzlicher Last sich verändert.\n\n");
        
        label_Info.setText("Industrie 1:\n" +
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
