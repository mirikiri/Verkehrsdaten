/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_Window;

import filereader.ReadWireSharkCSV;
import filereader.Read_pcapng;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Messung;
import model.Paket;
import org.apache.commons.io.FilenameUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 *
 * @author Miriam
 */

public class Temp_StarteMessungVC implements Initializable {
            
    private String signalType;
    private String generator;
    private String numGenerator;
    private String duration;
    
    private String minSize;
    private String maxSize;
    private String piOrder;
    
 
    @FXML
    private String skype;
    @FXML
    private String web;
    @FXML
    private String voIP;
    @FXML
    private String yt240;
    @FXML
    private String yt720;
    @FXML
    private String yt2160;
    @FXML
    private String exc;
    @FXML
    private String office;
    @FXML
    private String lsat;
    @FXML
    private String outlook;
    @FXML
    private String print;
    @FXML
    private String rauschen;
    @FXML
    private String remote;
    @FXML
    private String game;
    @FXML
    private String voIPmulti;
    @FXML
    private String Gen1;
    @FXML
    private String Gen2;
    @FXML
    private String Gen3;
    @FXML
    private String Gen4;
    @FXML
    private String Gen5;
    @FXML
    private String Gen6;
    @FXML
    private String Gen7;
    @FXML
    private String Gen8;
    @FXML
    private Button Button_Start;
    @FXML
    private ImageView Image_Verbindung;
    @FXML
    private ChoiceBox<String> choice_Gen;
    @FXML
    private ChoiceBox<String> choice_Signal;
    @FXML
    private TextField input_Dauer;
    @FXML
    private TextField input_min;
    @FXML
    private TextField input_max;
    @FXML
    private ChoiceBox<String> choice_AnzahlGen;
      
    
    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        choice_Gen.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            generator = newValue;
            System.out.println("MIRI: Generator = " + generator);
            
        });
        choice_Signal.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            signalType = newValue;
            System.out.println("MIRI: Signal = " + signalType);
            
        });
        choice_AnzahlGen.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            numGenerator = newValue;
            System.out.println("MIRI: Anzahl = " + numGenerator);
        });
        /*
        if(PI==verbunden){
            Image_Verbindung.setVisible(true);
        }
        else
            Image_Verbindung.setVisible(false);
        */
    }
    
    
    
    
    
    @FXML
    protected void handleStartButtonAction(ActionEvent event) throws IOException {

        //Miri
        //*************Neue Messung starten********************
        System.out.println("MIRI: Starte Messung Button");
        
        //alle Parameter einlesen
        duration = input_Dauer.getCharacters().toString();
        duration = duration.replaceAll("[^\\d]", "");
        minSize = input_min.getCharacters().toString().replaceAll("[^\\d]", "");
        maxSize = input_max.getCharacters().toString().replaceAll("[^\\d]", "");
        //Datenliste erstellen
        
        //Art der Anwendung in Befehl für Pi-Skript umwandeln
        switch(signalType){
            case "Skype-Shared-Screen": piOrder = "./skypesc ";
            case "Web": piOrder = "./web ";
            case "VoIP": piOrder = "./voip ";
            case "YouTube 240p": piOrder = "./youtube240p ";
            case "YouTube 720p": piOrder = "./youtube720p ";
            case "YouTube 2160p": piOrder = "./youtube2160p ";
            case "Excel": piOrder = "./excel ";
            case "ES Office": piOrder = "./esoffice_udp ";
            case "Lasttest": piOrder = "./lasttest_udp ";
            case "Outlook Start": piOrder = "./outlook_start_udp ";
            case "Print": piOrder = "./print ";
            case "Rauschen": piOrder = "./rauschen ";
            case "Remote Desktop": piOrder = "./remote_desktop_udp ";
            case "Gaming": piOrder = "./gaming ";
            case "VoIP_Multi": piOrder = "./voip_multi ";
        }
        
        //liste an Pi senden
        System.out.println("MIRI: Parameters are Type: "+ signalType+ ", Generator: "+ generator+ ", Number of Gens: "+numGenerator+" ,time: "+ duration+", Size: "+minSize+"-"+maxSize);
        //orderForPi(piOrder + duration, nutzername1, passwort1, ip1, port);
        
    }
   
    /*
    public static ArrayList<String> sendToPi(String befehl, String nutzername, String passwort, String ip, int port) throws Exception {

        // Erstelle das benötigte Objekt, um eine SSH-Verbindung aufbauen zu können.
        JSch jsch = new JSch();

        // Bereite ein paar Variablen vor, um Ausgaben der Konsole auslesen zu können.
        byte[] buffer = new byte[1024];
        ArrayList<String> lines = new ArrayList<>();

        // Füttere das Objekt mit allen nötigen Informationen, um eine Verbindung aufbauen zu können.
        Session session = jsch.getSession(nutzername, ip, port);
        session.setPassword(passwort);

        // Umgehe das Abgleichen nach dem richtigen Key. (Es wird nun eine Man-In-Middle Attacke nicht mehr abgefangen)
        Properties prop = new Properties();
        prop.put("StrictHostKeyChecking", "no");
        session.setConfig(prop);

        // Stelle eine Verbindung her.
        session.connect();

        // Erstelle ein neues Objekt, für einen neuen SSH Channel.
        ChannelExec channelssh = (ChannelExec) session.openChannel("exec");

        // Füttere den Channel mit dem Befehl und schicke den Befehl ab.
        channelssh.setCommand(befehl);
        channelssh.connect();

        // Fange an die Ausgaben der Konsole auszulesen.
        try {
            InputStream in = channelssh.getInputStream();
            String line = "";
            // Lese alle Ausgaben aus, bis der Befehl beendet wurde oder die Verbindung abbricht.
            while (true) {
                // Schreibe jede Zeile der Konsolenausgabe in unser Array.
                while (in.available() > 0) {
                    int i = in.read(buffer, 0, 1024);
                    // Brich die Protokollierung der Ausgabe, für diese Zeile, ab, wenn die Ausgabe leer sein sollte.
                    if (i < 0) {
                        break;
                    }
                    line = new String(buffer, 0, i);
                    lines.add(line);
                }
                // Wir wurden ausgeloggt.
                if (line.contains("logout")) {
                    break;
                }
                // Befehl beendet oder Verbindung abgebrochen.
                if (channelssh.isClosed()) {
                    break;
                }
                // Warte einen kleinen Augenblick mit der nächsten Zeile.
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {
                }
            }
        } catch (Exception e) {
        }

        // Beende alle Verbindungen.
        channelssh.disconnect();
        session.disconnect();

        // Gib die Ausgabe zurück
        return lines;
    }
    */
    protected void orderForPi(final String befehl, final String nutzername, final String passwort, final String ip, final int port){
        
        //Hier werden die genauen Befehle für das Raspi erstellt und anschließend mit sendToPi gesendet
        System.out.println("MIRI: orderForPi");
        /*try {

            // Funktion ausführen und Konsolenausgabe in "lines" speichern.
            ArrayList<String> lines = sshBefehl(befehl, nutzername, passwort, ip, port);
            // Alle Zeilen der Konsolenausgabe in den Android Logs ausgeben.
            while (!lines.isEmpty()) {
                Log.e("Rückgabe", lines.get(0));
                lines.remove(0);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;*/
    }
    
    
/*
    public void openAuswertungsWindow(Messung messung) throws IOException {
        //open auswertungswindow
        URL url_auswerung = getClass().getResource("/View/MessungWindow.fxml");

        FXMLLoader fxmlloader_auswertung = new FXMLLoader();
        fxmlloader_auswertung.setLocation(url_auswerung);

        Parent auswertung = fxmlloader_auswertung.load(url_auswerung.openStream());
        ((MessungWindowVC) fxmlloader_auswertung.getController()).setCurrentMessung(messung);
        ((MessungWindowVC) fxmlloader_auswertung.getController()).startUp();

        Stage auswertung_stage = new Stage();
        auswertung_stage.setMaximized(true);
        Scene auswertung_scene = new Scene(auswertung);

        auswertung_stage.setTitle(fileName + "  -  Auswertung");
        auswertung_stage.setScene(auswertung_scene);
        auswertung_stage.show();
    }
   */
      
    
}
