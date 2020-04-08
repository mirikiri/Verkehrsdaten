
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_Window;

import Profile.Profil;
import Profile.Profil_Web;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import datennetz_simulation.Datennetz_Simulation;
import static datennetz_simulation.Datennetz_Simulation.menucontrol;
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
import com.google.gson.Gson;
import static datennetz_simulation.Datennetz_Simulation.start_IP;
import static datennetz_simulation.Datennetz_Simulation.targetsystem;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private String signalType_einzel_windows;
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

    private Stage homeWindow = Datennetz_Simulation.parentWindow;

    //Variablen für Übertragung ans PI
    private List<String> piOrder = new ArrayList<String>();
    private int port = 22;
    private List<String> nutzername = new ArrayList<String>();
    private String passwort = "2t4h0-1n3b9g1";
    private List<String> ip = new ArrayList<String>();
    private int i = 0;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy_HH.mm.ss");

    private ProfileController profile = new ProfileController();

    //Variablen für Animation des Menüs
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
    private ImageView image_icon;
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
    @FXML
    private ImageView image_menu_off;
    @FXML
    private Pane pane_windows_einzel;
    @FXML
    private ChoiceBox<String> choice_Signal_einzel_windows;
    @FXML
    private TextField input_Dauer_einzel_windows;

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        //choice_type.setId("einzel");
        setListeners();
        setChoices();
        getConnection(); //evtl auch als listener?  
        if (targetsystem == "Windows") {
            typeMess = "Einzelsignal";
            choice_type.setDisable(true);
            pane_windows_einzel.setVisible(true);
        } else {
            choice_type.setDisable(false);
            pane_windows_einzel.setVisible(false);
        }
    }

    @FXML
    protected void handleStartButtonAction(ActionEvent event) throws IOException {

        //Miri
        //*************Neue Messung starten********************
        System.out.println("MIRI: Starte Messung Button");

        if (targetsystem == "Linux") {
            //**************************************************************************************************************************************
            //Arbeit mit den Raspberry Pi Generatoren
            //**************************************************************************************************************************************
            //alle Parameter einlesen
            if (typeMess.equals("Einzelsignal")) {
                signalType = signalType_einzel;
                numGenerator = "1 Generator";
                num = 1;
                duration = input_Dauer_einzel.getCharacters().toString().replaceAll("[^\\d]", "");
                minSize = input_min_einzel.getCharacters().toString().replaceAll("[^\\d]", "");
                maxSize = input_max_einzel.getCharacters().toString().replaceAll("[^\\d]", "");
                web_anzahlSeiten = input_anz_Seiten_einzel.getCharacters().toString().replaceAll("[^\\d]", "");
                web_wartezeit = input_warte_einzel.getCharacters().toString().replaceAll("[^\\d]", "");
                rauschen_skalierung = input_skal_Rauschen.getCharacters().toString().replaceAll("[^\\d]", "");
            } else if (typeMess.equals("Multisignal")) {
                signalType = signalType_Multi;
                duration = input_Dauer_Multi.getCharacters().toString().replaceAll("[^\\d]", "");
                minSize = input_min_Multi.getCharacters().toString().replaceAll("[^\\d]", "");
                maxSize = input_max_Multi.getCharacters().toString().replaceAll("[^\\d]", "");
                web_anzahlSeiten = input_anz_Seiten_Multi.getCharacters().toString().replaceAll("[^\\d]", "");
                web_wartezeit = input_warte_Multi.getCharacters().toString().replaceAll("[^\\d]", "");
                rauschen_skalierung = input_skal_Rauschen.getCharacters().toString().replaceAll("[^\\d]", "");
                switch (numGenerator) {
                    case "1 Generator":
                        num = 1;
                        break;
                    case "2 Generatoren":
                        num = 2;
                        break;
                    case "3 Generatoren":
                        num = 3;
                        break;
                    case "4 Generatoren":
                        num = 4;
                        break;
                    case "5 Generatoren":
                        num = 5;
                        break;
                    case "6 Generatoren":
                        num = 6;
                        break;
                    case "7 Generatoren":
                        num = 7;
                        break;
                    case "8 Generatoren":
                        num = 8;
                        break;
                    case "9 Generatoren":
                        num = 9;
                        break;
                    case "10 Generatoren":
                        num = 10;
                        break;
                    case "11 Generatoren":
                        num = 11;
                        break;
                    default:
                        num = 1;
                        break;
                }
            } else if (typeMess.equals("Fertiges Profil")) {
                signalType = signalType_Profil;
                duration = input_Dauer_Profil.getCharacters().toString().replaceAll("[^\\d]", "");
                rauschen_skalierung = input_skal_Rauschen.getCharacters().toString().replaceAll("[^\\d]", "");
            }
            //Arbeitet mit den Raspberry Pi Generatoren
            piOrder.clear();
            //Art der Anwendung in Befehl für Pi-Skript umwandeln

            System.out.println("MIRI: Parameters are Type: " + signalType + ", Generator: " + generator + ", Number of Gens: " + numGenerator + " ,time: " + duration + ", Size: " + minSize + "-" + maxSize + ", " + rauschen_skalierung + ", " + web_anzahlSeiten + ", " + web_wartezeit);

            piOrder = profile.gsetOrderList(signalType, duration, minSize, maxSize, rauschen_skalierung, web_anzahlSeiten, web_wartezeit);

            System.out.println("MIRI: Parameters are Type: " + signalType + ", Generator: " + generator + ", Number of Gens: " + numGenerator + " ,time: " + duration + ", Size: " + minSize + "-" + maxSize + ", " + rauschen_skalierung + ", " + web_anzahlSeiten + ", " + web_wartezeit);
            dur = Integer.parseInt(duration.replaceAll("[^\\d]", "")); //plus extra minuten???
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            savename = typeMess + "_" + signalType.replaceAll("\\s+", "") + "_" + sdf.format(timestamp);
            System.out.println("MIRI: " + savename);
            startWireshark(dur, savename);

            //Liste an Pi senden
            for (i = 0; i < num; i++) {
                /**
                 * *************************
                 * evtl anpassen so dass orderliste geschickt wird und nicht
                 * jeder einzelne befehl *************************
                 */
                if (signalType.equals(signalType_Profil)) {
                    orderForPi(piOrder.get(i), nutzername.get(i), passwort, ip.get(i), port);
                    System.out.println("MIRI: Industrie");
                } else if (signalType.equals(signalType_Multi)) {
                    orderForPi(piOrder.get(0), nutzername.get(i), passwort, ip.get(i), port);
                    System.out.println("MIRI: orderforPi done");
                } else {
                    orderForPi(piOrder.get(0), nutzername.get(gen - 1), passwort, ip.get(gen - 1), port);
                    System.out.println("MIRI: orderforPi done");
                }
            }
        } 
        
        else {
            //**************************************************************************************************************************************
            //Arbeitet mit einem weiteren Notebook
            //**************************************************************************************************************************************
            duration = input_Dauer_einzel_windows.getCharacters().toString().replaceAll("[^\\d]", "");
            dur = Integer.parseInt(duration);
            Gson gson = new Gson();
            Profil windows_profil = profile.getProfilOrder(signalType_einzel_windows);
            windows_profil.saveAndWrite(gson);
            System.out.println("MIRI: new profile for windows: " + signalType_einzel_windows);
            
            // Thread um Daten zu senden
            Thread thread = new Thread() {
                public void run() {
                    try {
                        windows_profil.send2();
                    } catch (SocketException ex) {
                        Logger.getLogger(Datennetz_Simulation.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnknownHostException ex) {
                        Logger.getLogger(Datennetz_Simulation.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Datennetz_Simulation.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(StarteMessungWindowVC.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            // Thread für den Timer, der Senden startet und beendet
            new Thread() {
                public void run() {
                    try {
                        thread.start();
                        Thread.sleep(dur*60*1000); //Umwandliung von Miunten in Millisekunden 
                        thread.stop();
                        System.out.println("MIRI: Orders done");
                    } catch (InterruptedException ex) {
                        Logger.getLogger(StarteMessungWindowVC.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }.start();
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
        } catch (Exception e) {
        }
        System.out.println("SSH done");

        // Beende alle Verbindungen.
        channelssh.disconnect();
        session.disconnect();

        // Gib die Ausgabe zurück
        return lines;
    }

    protected void orderForPi(String befehl, String nutzername, String passwort, String ip, int port) {

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
                        System.out.println("Rückgabe" + lines.get(0));
                        lines.remove(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    protected void setListeners() {
        choice_Gen.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            generator = newValue;
            gen = Integer.parseInt(generator.replaceAll("[^\\d]", ""));
            System.out.println("MIRI: Generator = " + generator);
        });
        choice_Signal_einzel.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            signalType_einzel = newValue;
            System.out.println("MIRI: Signal = " + signalType_einzel);

            if (signalType_einzel.equals("Web")) {
                input_warte_einzel.setVisible(true);
                input_anz_Seiten_einzel.setVisible(true);
                label_warte_einzel.setVisible(true);
                label_anzSeiten_einzel.setVisible(true);
            } else {
                input_warte_einzel.setVisible(false);
                input_anz_Seiten_einzel.setVisible(false);
                label_warte_einzel.setVisible(false);
                label_anzSeiten_einzel.setVisible(false);
            }
            if (signalType_einzel.equals("Rauschen")) {
                label_rauschen_einzel.setVisible(true);
                input_rauschen_einzel.setVisible(true);
            } else {
                label_rauschen_einzel.setVisible(false);
                input_rauschen_einzel.setVisible(false);
            }
            if (signalType_einzel.equals("VoIP")) {
                label_min_einzel.setVisible(true);
                label_max_einzel.setVisible(true);
                input_min_einzel.setVisible(true);
                input_max_einzel.setVisible(true);
            } else {
                label_min_einzel.setVisible(false);
                label_max_einzel.setVisible(false);
                input_min_einzel.setVisible(false);
                input_max_einzel.setVisible(false);
            }
        });
        choice_Signal_Multi.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            signalType_Multi = newValue;
            System.out.println("MIRI: Signal = " + signalType_Multi);
            //if web or rauschen set extraparameter visible else invisible
            if (signalType_Multi.equals("Web")) {
                input_warte_Multi.setVisible(true);
                input_anz_Seiten_Multi.setVisible(true);
                label_warte_multi.setVisible(true);
                label_anzSeiten_multi.setVisible(true);
            } else {
                input_warte_Multi.setVisible(false);
                input_anz_Seiten_Multi.setVisible(false);
                label_warte_multi.setVisible(false);
                label_anzSeiten_multi.setVisible(false);
            }
            if (signalType_Multi.equals("Rauschen")) {
                label_rauschen_multi.setVisible(true);
                input_rauschen_multi.setVisible(true);
            } else {
                label_rauschen_multi.setVisible(false);
                input_rauschen_multi.setVisible(false);
            }
            if (signalType_Multi.equals("VoIP")) {
                label_min_multi.setVisible(true);
                label_max_multi.setVisible(true);
                input_min_Multi.setVisible(true);
                input_max_Multi.setVisible(true);
            } else {
                label_min_multi.setVisible(false);
                label_max_multi.setVisible(false);
                input_min_Multi.setVisible(false);
                input_max_Multi.setVisible(false);
            }
        });
        choice_Signal_Profil.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            signalType_Profil = newValue;
            System.out.println("MIRI: Signal = " + signalType_Profil);
        });
        choice_Signal_einzel_windows.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            signalType_einzel_windows = newValue;
            System.out.println("MIRI: Signal = " + signalType_einzel_windows);
        });
        choice_AnzahlGen.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            numGenerator = newValue;
            System.out.println("MIRI: Anzahl = " + numGenerator);
        });
        choice_type.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            typeMess = newValue;
            System.out.println("MIRI: Art der Messung = " + typeMess);
            if (typeMess.equals("Einzelsignal")) {
                pane_Einzel.setVisible(true);
                pane_Multi.setVisible(false);
                pane_Profil.setVisible(false);
            } else if (typeMess.equals("Multisignal")) {
                pane_Einzel.setVisible(false);
                pane_Multi.setVisible(true);
                pane_Profil.setVisible(false);
            } else if (typeMess.equals("Fertiges Profil")) {
                pane_Einzel.setVisible(false);
                pane_Multi.setVisible(false);
                pane_Profil.setVisible(true);
            }
        });

    }

    protected void setChoices() {
        nutzername.add("verkehrsgenerator1");
        nutzername.add("verkehrsgenerator2");
        nutzername.add("verkehrsgenerator3");
        nutzername.add("verkehrsgenerator4");
        nutzername.add("verkehrsgenerator5");
        nutzername.add("verkehrsgenerator6");
        nutzername.add("verkehrsgenerator7");
        nutzername.add("verkehrsgenerator8");
        nutzername.add("verkehrsgenerator9");
        nutzername.add("verkehrsgenerator10");
        nutzername.add("verkehrsgenerator11");
        nutzername.add("Server");

        if (targetsystem == "Linux") {
            ip.add("192.168.1.131");
            ip.add("192.168.1.132");
            ip.add("192.168.1.133");
            ip.add("192.168.1.134");
            ip.add("192.168.1.135");
            ip.add("192.168.1.136");
            ip.add("192.168.1.137");
            ip.add("192.168.1.138");
            ip.add("192.168.1.139");
            ip.add("192.168.1.140");
            ip.add("192.168.1.141");
            ip.add("192.168.1.10");
        }

    }

    protected void setAnimations() {

    }

    protected void getConnection() {
        /*
        if(PI==verbunden){
            Image_Verbindung.setVisible(true);
        }
        else
            Image_Verbindung.setVisible(false);
         */
    }

    protected boolean startWireshark(int duration, String name) {
        //Windows?
        /*try {
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
        }*/

        return true;
    }

    protected void getPcap() {
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
        image_menu_off.setVisible(false);
        //setAnimation(menu, true);
    }

    @FXML
    private void handleMenuInvis(MouseEvent event) {
        grid_menu.setVisible(false);
        image_menu_off.setVisible(true);
        //setAnimation(homeWindow, false);
    }

}
