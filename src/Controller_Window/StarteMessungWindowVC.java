/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_Window;


import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
<<<<<<< HEAD
import datennetz_simulation.Datennetz_Simulation;
import static datennetz_simulation.Datennetz_Simulation.menucontrol;
=======
>>>>>>> master
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
<<<<<<< HEAD
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import Profile.ProfileController;
import static datennetz_simulation.Datennetz_Simulation.targetsystem;
=======
import javafx.scene.layout.Pane;
import javafx.util.Duration;
>>>>>>> master

/**
 *
 * @author Miriam
 */

public class StarteMessungWindowVC implements Initializable {
    //Variablen
    private String signalType;
    private String signalType_einzel;    
    private String signalType_Multi;
    private String signalType_Profil;
    private String generator;
    private int gen;
    private String numGenerator;
    private int num = 1;
    private String duration;
    private int dur;
    private String rauschen_skalierung = "";
    private String web_anzahlSeiten = "";
    private String web_wartezeit = "";
    private String minSize;
    private String maxSize;
    private String typeMess;
    private String befehl_wireshark;
    private String savename;
<<<<<<< HEAD
    
    private Stage homeWindow = Datennetz_Simulation.parentWindow;
        
    //Variablen für Übertragung ans PI
    private List<String> piOrder = new ArrayList<String>();
=======
        
    //Variablen für Übertragung ans PI
    private List<String> piOrder = new ArrayList<String>();; //evtl auch als Liste machen für gemischte Profile
>>>>>>> master
    private int port = 22;
    private List<String> nutzername = new ArrayList<String>();
    private String passwort = "2t4h0-1n3b9g1";
    private List<String> ip = new ArrayList<String>();
    private int i = 0;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy_HH.mm.ss");
    
<<<<<<< HEAD
    private ProfileController profile;
    
    //Variablen für Animation des Menüs
=======
    //Variablen für Animation
>>>>>>> master
    Duration startDuration = Duration.ZERO;
    Duration endDuration = Duration.seconds(10);
    

    @FXML
    private Button Button_Start;
    @FXML
    private ImageView Image_Verbindung;
    @FXML
    private ChoiceBox<String> choice_Gen;
    @FXML
    private ChoiceBox<String> choice_AnzahlGen;
    @FXML
    private ChoiceBox<String> choice_type;
    @FXML
    private Pane pane_Multi;
    @FXML
    private ChoiceBox<String> choice_Signal_Multi;
    @FXML
    private Pane pane_Profil;
    @FXML
    private ChoiceBox<String> choice_Signal_Profil;
    @FXML
    private TextField input_skal_Rauschen;
    @FXML
    private Pane pane_Einzel;
    @FXML
    private ChoiceBox<String> choice_Signal_einzel;
    @FXML
    private TextField input_Dauer_Multi;
    @FXML
    private TextField input_min_Multi;
    @FXML
    private TextField input_max_Multi;
    @FXML
    private TextField input_Dauer_Profil;
    @FXML
    private TextField input_Dauer_einzel;
    @FXML
    private TextField input_min_einzel;
    @FXML
    private TextField input_max_einzel;
   
    @FXML
    private TextField input_anz_Seiten_Multi;
    @FXML
    private TextField input_warte_Multi;
    @FXML
    private TextField input_warte_einzel;
    @FXML
    private TextField input_anz_Seiten_einzel;
<<<<<<< HEAD
    @FXML
    private TextField input_rauschen_einzel;
   
    @FXML
    private GridPane grid_menu;
    @FXML
    private Label label_min_einzel;
    @FXML
    private Label label_max_einzel;
    @FXML
    private Label label_warte_einzel;
    @FXML
    private Label label_anzSeiten_einzel;
    @FXML
    private Label label_Messung;
    @FXML
    private Label label_profil;
    @FXML
    private Label label_auswertung;
    @FXML
    private ImageView image_icon;
    @FXML
    private Label label_einstellung;
    @FXML
    private Label label_home;
    @FXML
    private Label label_min_multi;
    @FXML
    private Label label_anzSeiten_multi;
    @FXML
    private Label label_warte_multi;
    @FXML
    private Label label_rauschen_einzel;
    @FXML
    private Label label_max_multi;
    @FXML
    private Label label_rauschen_multi;
    @FXML
    private TextField input_rauschen_multi;
    @FXML
    private ImageView hilfeImage;
    
   
   
    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        //choice_type.setId("einzel");
=======
      
    
    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
>>>>>>> master
        setListeners();
        setChoices();
        getConnection(); //evtl auch als listener?  
    }
    
    
    @FXML
    protected void handleStartButtonAction(ActionEvent event) throws IOException {

        //Miri
        //*************Neue Messung starten********************
        System.out.println("MIRI: Starte Messung Button");
        
<<<<<<< HEAD
        
=======
>>>>>>> master
        //alle Parameter einlesen
        if(typeMess.equals("Einzelsignal")){
            signalType = signalType_einzel;
            numGenerator = "1 Generator";
            num = 1;
            duration = input_Dauer_einzel.getCharacters().toString().replaceAll("[^\\d]", "");
            minSize = input_min_einzel.getCharacters().toString().replaceAll("[^\\d]", "");
            maxSize = input_max_einzel.getCharacters().toString().replaceAll("[^\\d]", "");
            web_anzahlSeiten = input_anz_Seiten_einzel.getCharacters().toString().replaceAll("[^\\d]", "");
            web_wartezeit = input_warte_einzel.getCharacters().toString().replaceAll("[^\\d]", "");
            rauschen_skalierung = input_skal_Rauschen.getCharacters().toString().replaceAll("[^\\d]", "");
        }
        else if (typeMess.equals("Multisignal")){
            signalType = signalType_Multi;
            duration = input_Dauer_Multi.getCharacters().toString().replaceAll("[^\\d]", "");
            minSize = input_min_Multi.getCharacters().toString().replaceAll("[^\\d]", "");
            maxSize = input_max_Multi.getCharacters().toString().replaceAll("[^\\d]", "");
            web_anzahlSeiten = input_anz_Seiten_Multi.getCharacters().toString().replaceAll("[^\\d]", "");
            web_wartezeit = input_warte_Multi.getCharacters().toString().replaceAll("[^\\d]", "");
            rauschen_skalierung = input_skal_Rauschen.getCharacters().toString().replaceAll("[^\\d]", "");
            switch(numGenerator){
                case "1 Generator": num = 1; break;
                case "2 Generatoren": num = 2; break;
                case "3 Generatoren": num = 3; break;
                case "4 Generatoren": num = 4; break;
                case "5 Generatoren": num = 5; break;
                case "6 Generatoren": num = 6; break;
                case "7 Generatoren": num = 7; break;
                case "8 Generatoren": num = 8; break;
                default: num=1; break;
            }
        }
        else if (typeMess.equals("Fertiges Profil")){
            signalType = signalType_Profil;
            duration = input_Dauer_Profil.getCharacters().toString().replaceAll("[^\\d]", "");
            rauschen_skalierung = input_skal_Rauschen.getCharacters().toString().replaceAll("[^\\d]", "");
        }
       
        piOrder.clear();
        //Art der Anwendung in Befehl für Pi-Skript umwandeln
<<<<<<< HEAD
        piOrder = profile.gsetOrderList(signalType, duration, minSize, maxSize, rauschen_skalierung, web_anzahlSeiten, web_wartezeit);
        
        System.out.println("MIRI: Parameters are Type: "+ signalType + ", Generator: "+ generator+ ", Number of Gens: "+numGenerator+" ,time: "+ duration+", Size: "+minSize+"-"+maxSize+", "+rauschen_skalierung+", "+web_anzahlSeiten+", "+web_wartezeit);
=======
        switch(signalType){
            case "Skype-Shared-Screen": 
                piOrder.add("./skypesc "+ duration);
                break;
            case "Web": 
                piOrder.add("./web "+ web_anzahlSeiten + " " + web_wartezeit);
                break;
            case "VoIP": 
                piOrder.add("./voip "+ minSize + " " + maxSize + " " + duration);
                break;
            case "YouTube 240p":
                piOrder.add("./youtube240p "+ duration);
                break;
            case "YouTube 720p": 
                piOrder.add("./youtube720p "+ duration);
                break;
            case "YouTube 2160p": 
                piOrder.add("./youtube2160p "+ duration);
                break;
            case "Excel": 
                piOrder.add("./excel "+ duration);
                break;
            case "ES Office": 
                piOrder.add("./esoffice_udp "+ duration);
                break;
            case "Lasttest": 
                piOrder.add("./lasttest_udp "+ duration);
                break;
            case "Outlook Start": 
                piOrder.add("./outlook_start_udp "+ duration);
                break;
            case "Print": 
                piOrder.add("./print "+ duration);
                break;
            case "Rauschen": 
                piOrder.add("./rauschen "+ duration+ " " + rauschen_skalierung);
                break; 
            case "Remote Desktop":                               
                piOrder.add("./remote_desktop_udp "+ duration);
                break;
            case "Gaming": 
                piOrder.add("./gaming "+ duration);
                break;
            case "VoIP_Multi": 
                piOrder.add("./voip_multi "+ duration);
                break;
            case "Industrie 1": 
                piOrder.add("./rauschen " + duration + " " + rauschen_skalierung);
                piOrder.add("./excel " + duration);                       
                piOrder.add("./outlook_start_udp " + duration);
                piOrder.add("./print " + duration);
                piOrder.add("./skypesc " + duration);
                piOrder.add("./voip_profil " + duration);
                piOrder.add("./web_profil " + duration);
                break;
            default: 
                System.out.println("Kein Signaltyp ausgewählt!");
                break;
        }
        System.out.println("MIRI: Parameters are Type: "+ signalType + ", Generator: "+ generator+ ", Number of Gens: "+numGenerator+" ,time: "+ duration+", Size: "+minSize+"-"+maxSize+", "+rauschen_skalierung+", "+web_anzahlSeiten+", "+web_wartezeit);
        //wireshark starten mit tshark
>>>>>>> master
        dur = Integer.parseInt(duration.replaceAll("[^\\d]", "")); //plus extra minuten???
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        savename = typeMess + "_" + signalType.replaceAll("\\s+", "") + "_" + sdf.format(timestamp);
        System.out.println("MIRI: "+ savename);
<<<<<<< HEAD
        startWireshark(dur, savename);
=======
        //startWireshark(dur, savename);
>>>>>>> master
        
        //Liste an Pi senden
        for(i=0; i < num; i++){
        /***************************
        * evtl anpassen so dass orderliste geschickt wird und nicht jeder einzelne befehl
        ***************************/
            if(signalType.equals(signalType_Profil)){
                //orderForPi(piOrder.get(i), nutzername.get(i), passwort, ip.get(i), port);
                System.out.println("MIRI: Industrie");
            }
            else if(signalType.equals(signalType_Multi)){
                //orderForPi(piOrder.get(0), nutzername.get(i), passwort, ip.get(i), port);
                System.out.println("MIRI: orderforPi done");
            }
            else{
                //orderForPi(piOrder.get(0), nutzername.get(gen-1), passwort, ip.get(gen-1), port);
                System.out.println("MIRI: orderforPi done");
            }
        }
        System.out.println("MIRI: Orders sent");
        
    }
   
    
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
        System.out.println("JSCH done");

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
        } 
        catch (Exception e) {
        }
        System.out.println("SSH done");

        // Beende alle Verbindungen.
        channelssh.disconnect();
        session.disconnect();

        // Gib die Ausgabe zurück
        return lines;    
    }

    protected void orderForPi(String befehl, String nutzername, String passwort, String ip, int port){

        //Hier werden die genauen Befehle mit sendToPi gesendet
        System.out.println("MIRI: orderForPi");
        // separate non-FX thread
        new Thread() {

            // runnable for that thread
            public void run() {
                try {

                    // Funktion ausführen und Konsolenausgabe in "lines" speichern.
                    ArrayList<String> lines = sendToPi(befehl, nutzername, passwort, ip, port);
                    // Alle Zeilen der Konsolenausgabe in den Android Logs ausgeben.
                    while (!lines.isEmpty()) {
                        System.out.println("Rückgabe"+ lines.get(0));
                        lines.remove(0);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    
    protected void setListeners(){
        choice_Gen.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            generator = newValue;
            gen = Integer.parseInt(generator.replaceAll("[^\\d]", ""));
            System.out.println("MIRI: Generator = " + generator);
<<<<<<< HEAD
=======
            
>>>>>>> master
        });
        choice_Signal_einzel.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            signalType_einzel = newValue;
            System.out.println("MIRI: Signal = " + signalType);
<<<<<<< HEAD
            
            if (signalType_einzel.equals("Web")){
                input_warte_einzel.setVisible(true);
                input_anz_Seiten_einzel.setVisible(true);
                label_warte_einzel.setVisible(true);
                label_anzSeiten_einzel.setVisible(true);
            }
            else {
                input_warte_einzel.setVisible(false);
                input_anz_Seiten_einzel.setVisible(false);
                label_warte_einzel.setVisible(false);
                label_anzSeiten_einzel.setVisible(false);
            }
            if (signalType_einzel.equals("Rauschen")){
                label_rauschen_einzel.setVisible(true);
                input_rauschen_einzel.setVisible(true);
            }
            else{
                label_rauschen_einzel.setVisible(false);
                input_rauschen_einzel.setVisible(false);
            }
            if (signalType_einzel.equals("VoIP")){
                label_min_einzel.setVisible(true);
                label_max_einzel.setVisible(true);
                input_min_einzel.setVisible(true);
                input_max_einzel.setVisible(true);
            }
            else{
                label_min_einzel.setVisible(false);
                label_max_einzel.setVisible(false);
                input_min_einzel.setVisible(false);
                input_max_einzel.setVisible(false);
            }
=======
            //if web or rauschen set extraparameter visible else invisible
>>>>>>> master
        });
        choice_Signal_Multi.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            signalType_Multi = newValue;
            System.out.println("MIRI: Signal = " + signalType);
            //if web or rauschen set extraparameter visible else invisible
<<<<<<< HEAD
            if (signalType_Multi.equals("Web")){
                input_warte_Multi.setVisible(true);
                input_anz_Seiten_Multi.setVisible(true);
                label_warte_multi.setVisible(true);
                label_anzSeiten_multi.setVisible(true);
            }
            else{
                input_warte_Multi.setVisible(false);
                input_anz_Seiten_Multi.setVisible(false);
                label_warte_multi.setVisible(false);
                label_anzSeiten_multi.setVisible(false);
            }
            if (signalType_Multi.equals("Rauschen")){
                label_rauschen_multi.setVisible(true);
                input_rauschen_multi.setVisible(true);
            }
            else{
                label_rauschen_multi.setVisible(false);
                input_rauschen_multi.setVisible(false);
            }
            if (signalType_Multi.equals("VoIP")){
                label_min_multi.setVisible(true);
                label_max_multi.setVisible(true);
                input_min_Multi.setVisible(true);
                input_max_Multi.setVisible(true);
            }
            else{
                label_min_multi.setVisible(false);
                label_max_multi.setVisible(false);
                input_min_Multi.setVisible(false);
                input_max_Multi.setVisible(false);
            }
=======
>>>>>>> master
        });
        choice_Signal_Profil.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            signalType_Profil = newValue;
            System.out.println("MIRI: Signal = " + signalType);
        });
        choice_AnzahlGen.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            numGenerator = newValue;
            System.out.println("MIRI: Anzahl = " + numGenerator);
        });
        choice_type.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            typeMess = newValue;
            System.out.println("MIRI: Art der Messung = " + typeMess);
            if(typeMess.equals("Einzelsignal")){
                pane_Einzel.setVisible(true);
                pane_Multi.setVisible(false);
                pane_Profil.setVisible(false);
            }
            else if (typeMess.equals("Multisignal")){
                pane_Einzel.setVisible(false);
                pane_Multi.setVisible(true);
                pane_Profil.setVisible(false);
            }
            else if (typeMess.equals("Fertiges Profil")){
                pane_Einzel.setVisible(false);
                pane_Multi.setVisible(false);
                pane_Profil.setVisible(true);
            }
        });
        
    }
    
    protected void setChoices(){
        nutzername.add("verkehrsgenerator1");
        nutzername.add("verkehrsgenerator2");
        nutzername.add("verkehrsgenerator3");
        nutzername.add("verkehrsgenerator4");
        nutzername.add("verkehrsgenerator5");
        nutzername.add("verkehrsgenerator6");
        nutzername.add("verkehrsgenerator7");
        nutzername.add("verkehrsgenerator8");
        nutzername.add("Server");
        
        ip.add("192.168.1.131");        
        ip.add("192.168.1.132");        
        ip.add("192.168.1.133");        
        ip.add("192.168.1.134");        
        ip.add("192.168.1.135");        
        ip.add("192.168.1.136");        
        ip.add("192.168.1.137");        
        ip.add("192.168.1.138");     
        ip.add("192.168.1.11");
        
    }
    
    protected void setAnimations(){
        
        

    }
    
    protected void getConnection(){
        /*
        if(PI==verbunden){
            Image_Verbindung.setVisible(true);
        }
        else
            Image_Verbindung.setVisible(false);
        */
    }
    
    protected boolean startWireshark(int duration, String name){
<<<<<<< HEAD
        //Windows?
        /*try {
=======
        try {
>>>>>>> master
            //tshark -i "EVIL" -a duration:10 -w h:\ws\test.pcap
            befehl_wireshark = "Pfad\\tshark -i \"eth0\" -a duration:" + duration + " -w Pfad\\" + name;
            // Funktion ausführen und Konsolenausgabe in "lines" speichern.
            ArrayList<String> lines = sendToPi(befehl_wireshark, nutzername.get(9), passwort, ip.get(9), port);
            // Alle Zeilen der Konsolenausgabe in den Android Logs ausgeben.
            while (!lines.isEmpty()) {
                System.out.println("Rückgabe"+ lines.get(0));
                lines.remove(0);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
<<<<<<< HEAD
        }*/
=======
        }
>>>>>>> master

        return true;
    }
    
    protected void getPcap(){
<<<<<<< HEAD
        /*if(targetsystem.equals("Linux"){
            scp <quelle> user@host:ziel
            scp pfad/datei.pcapng ssh-1234-xxx@localhost:/Pfad/ziel/
        
        }
        else{
        
        }
        */
    }

    @FXML
    private void handleImageHilfe(MouseEvent event) throws IOException {
         menucontrol.handleImageHilfe();
    }

    @FXML
    private void handlemenumessung(MouseEvent event) throws IOException {
        menucontrol.handlemenumessung();
    }

    @FXML
    private void handlemenuprofil(MouseEvent event) throws IOException {
        menucontrol.handlemenuprofil();
    }

    @FXML
    private void handlemenuauswertung(MouseEvent event) throws IOException {
        menucontrol.handlemenuauswertung();
    }

    @FXML
    private void handlemenueinstellungen(MouseEvent event) throws IOException {
        menucontrol.handlemenueinstellungen();
    }

    @FXML
    private void handlemenuHome(MouseEvent event) throws IOException {
        menucontrol.handlemenuHome();
    }
    
        
    @FXML
    private void handleMenuImage(MouseEvent event) {
        grid_menu.setVisible(true);
        //setAnimation(menu, true);
    }
    
    @FXML
    private void handleMenuInvis(MouseEvent event) {
        grid_menu.setVisible(false);
        //setAnimation(homeWindow, false);
=======
        //scp <quelle> user@host:ziel
        //scp pfad/datei.pcapng ssh-1234-xxx@localhost:/Pfad/ziel/
>>>>>>> master
    }
    
    
}