/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menu;

import Controller_Window.MainWindowVC;
import Controller_Window.MessungWindowVC;
import Controller_Window.SimulationWindowVC;
import Controller_Window.WarnungenWindowVC;
import datennetz_simulation.Datennetz_Simulation;
import filereader.ReadWireSharkCSV;
import filereader.Read_pcapng;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Messung;
import model.Paket;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Miriam
 */
public class MenuController{

    
    private FileChooser fileChooser_auswerten;
    private String path;
    private String fileName;
    private final ReadWireSharkCSV csvReader = new ReadWireSharkCSV();
    private final Read_pcapng pcapngReader = new Read_pcapng();
       

    public void handlemenuHome() throws IOException {
        URL url_home = getClass().getResource("/View/MainWindow.fxml");
        
        FXMLLoader fxmlloader_home = new FXMLLoader();
        fxmlloader_home.setLocation(url_home);

        Parent home = fxmlloader_home.load(url_home.openStream());

        Scene home_scene = new Scene(home);
        Datennetz_Simulation.parentWindow.getIcons().add(new Image(getClass().getResourceAsStream("/Pictures/PC_Icon.jpg")));

        Datennetz_Simulation.parentWindow.setTitle("Willkommen");
        Datennetz_Simulation.parentWindow.setScene(home_scene);
        Datennetz_Simulation.parentWindow.show();
    }

    public void handlemenumessung() throws IOException {
        //Neue seite aufrufen um Messung zu Starten
    
        URL url_messung = getClass().getResource("/View/StarteMessungWindow.fxml");
        
        FXMLLoader fxmlloader_messung = new FXMLLoader();
        fxmlloader_messung.setLocation(url_messung);

        Parent messung = fxmlloader_messung.load(url_messung.openStream());

        Scene messung_scene = new Scene(messung);
        Datennetz_Simulation.parentWindow.getIcons().add(new Image(getClass().getResourceAsStream("/Pictures/PC_Icon.jpg")));

        Datennetz_Simulation.parentWindow.setTitle("Messungen aufnehmen");
        Datennetz_Simulation.parentWindow.setScene(messung_scene);
        Datennetz_Simulation.parentWindow.show();
    }

    public void handlemenuprofil()  throws IOException {
        //Neue seite aufrufen um Profile zu managen
        URL url_profile = getClass().getResource("/View/ProfileWindow.fxml");
        
        FXMLLoader fxmlloader_profile = new FXMLLoader();
        fxmlloader_profile.setLocation(url_profile);

        Parent profile = fxmlloader_profile.load(url_profile.openStream());

        Scene profile_scene = new Scene(profile);
        Datennetz_Simulation.parentWindow.getIcons().add(new Image(getClass().getResourceAsStream("/Pictures/PC_Icon.jpg")));

        Datennetz_Simulation.parentWindow.setTitle("Neues Profil erstellen");
        Datennetz_Simulation.parentWindow.setScene(profile_scene);
        Datennetz_Simulation.parentWindow.show();
    }

    public void handlemenuauswertung() throws IOException {
        Stage fileChooseStage = new Stage();
        if (fileChooser_auswerten == null) {
            fileChooser_auswerten = new FileChooser();
            fileChooser_auswerten.setInitialDirectory(new File(System.getProperty("user.home")));
            fileChooser_auswerten.getExtensionFilters().add(new FileChooser.ExtensionFilter("File", "*.csv", "*.pcapng"));
            fileChooser_auswerten.getExtensionFilters().add(new FileChooser.ExtensionFilter("pcapng", "*.pcapng"));
        }

        File file = fileChooser_auswerten.showOpenDialog(fileChooseStage);
        System.out.println("MIRI: " + file.getName());
        readFile(file, "Auswertung");
    }

    public void handlemenueinstellungen() throws IOException {
        URL url_einst = getClass().getResource("/View/EinstellungenWindow.fxml");

        FXMLLoader fxmlloader_einst = new FXMLLoader();
        fxmlloader_einst.setLocation(url_einst);

        Parent einst = fxmlloader_einst.load(url_einst.openStream());

        Datennetz_Simulation.parentWindow.getIcons().add(new Image(getClass().getResourceAsStream("/Pictures/Cloud_Icon.jpg")));
        Scene einst_scene = new Scene(einst);

        Datennetz_Simulation.parentWindow.setTitle("Einstellungen");
        Datennetz_Simulation.parentWindow.setScene(einst_scene);
        Datennetz_Simulation.parentWindow.show();
    }
    
    public void readFile(File file, String starter) throws IOException {
//        long startTime = System.nanoTime();

        System.out.println("MIRI: " + file.getName());
        if (file != null) {
            path = file.getAbsolutePath();
            fileName = file.getName();
        } else {
            //MainWindowVC.actiontarget.setText("keine Datei ausgewählt");
            return;
        }
            //MainWindowVC.actiontarget.setText("Ausgewertet: " + fileName);
        
        
        System.out.println("MIRI: " + path + fileName);
        
        if (fileChooser_auswerten != null) {
            String dir = path.replace(fileName, "");
            fileChooser_auswerten.setInitialDirectory(new File(dir));
        }
        List<Paket> pakets = new ArrayList<>();
        switch (FilenameUtils.getExtension(file.getName())) {
            case "csv":
                pakets = csvReader.readFile(path);
                break;
            case "pcapng":
                pakets = pcapngReader.readFile(path);
                break;
        }
        
        System.out.println("MIRI: " + pakets);
        
        //if user cancelled the reading of a big file, pakets will be null. function stops here
        if (pakets == null) {
            return;
        }
        
//        System.out.println("Einlesen: " + Math.round((double)(System.nanoTime() - startTime) / 1000000000 * 10000d) / 10000d  + " s");
//        startTime = System.nanoTime();

        Messung messung = new Messung(path, pakets);
        // do downsampling for all arrival rate graph data. downsampling functions takes care of too short data as well
        if (messung.getBigFile()) {
            showBigFileAlert(messung);
        }
        if (starter.equals("Auswertung")){
            openAuswertungsWindow(messung);
            openWarnungenWindow(messung);
        }
        else
            openSimulationWindow(messung);
    }
    
    public void showBigFileAlert(Messung messung) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/Pictures/icon.jpg").toString()));
        alert.setHeaderText("Sehr lange Messung!");
        alert.setContentText("Die eingelesene Messung ist " + messung.getDuration() + " Sekunden lang. Aus Performance Gründen ist im Zeitintervall 10ms nur jeder 10te Wert erfasst und weiterhin der Burst-Graph deaktiviert.\n\nDie Verarbeitung kann einen Moment dauern. Bitte haben Sie etwas Geduld.");

        alert.showAndWait();
    }
    
    private void openAuswertungsWindow(Messung messung) throws IOException {
        //open auswertungswindow
        URL url_auswerung = getClass().getResource("/View/MessungWindow.fxml");

        FXMLLoader fxmlloader_auswertung = new FXMLLoader();
        fxmlloader_auswertung.setLocation(url_auswerung);

        Parent auswertung = fxmlloader_auswertung.load(url_auswerung.openStream());
        ((MessungWindowVC) fxmlloader_auswertung.getController()).setCurrentMessung(messung);
        ((MessungWindowVC) fxmlloader_auswertung.getController()).startUp();

        Stage auswertung_stage = new Stage();
        auswertung_stage.setMaximized(true);
        auswertung_stage.getIcons().add(new Image(getClass().getResourceAsStream("/Pictures/Cloud_Icon.jpg")));
        Scene auswertung_scene = new Scene(auswertung);

        auswertung_stage.setTitle(fileName + "  -  Auswertung");
        auswertung_stage.setScene(auswertung_scene);
        auswertung_stage.show();
    }

    private void openWarnungenWindow(Messung messung) throws IOException {
        //open warnungen window
        URL url_warnung = getClass().getResource("/View/WarnungenWindow.fxml");

        FXMLLoader fxmlloader_warnung = new FXMLLoader();
        fxmlloader_warnung.setLocation(url_warnung);

        Parent warnung = fxmlloader_warnung.load(url_warnung.openStream());
        ((WarnungenWindowVC) fxmlloader_warnung.getController()).setCurrentMessung(messung);
        ((WarnungenWindowVC) fxmlloader_warnung.getController()).startUp();

        Stage warnung_newStage = new Stage();
        warnung_newStage.getIcons().add(new Image(getClass().getResourceAsStream("/Pictures/Cloud_Icon.jpg")));
        Scene warnung_scene = new Scene(warnung);

        warnung_newStage.setTitle(fileName + "  -  Warnungen");
        warnung_newStage.setScene(warnung_scene);
        warnung_newStage.show();
    }

    public void setSimulation() throws IOException{
        Stage fileChooseStage = new Stage();
        if (fileChooser_auswerten == null) {
            fileChooser_auswerten = new FileChooser();
            fileChooser_auswerten.setInitialDirectory(new File(System.getProperty("user.home")));
            fileChooser_auswerten.getExtensionFilters().add(new FileChooser.ExtensionFilter("File", "*.csv", "*.pcapng"));
            fileChooser_auswerten.getExtensionFilters().add(new FileChooser.ExtensionFilter("pcapng", "*.pcapng"));
        }

        File file = fileChooser_auswerten.showOpenDialog(fileChooseStage);
        System.out.println("MIRI: " + file.getName());
        readFile(file, "Simulation");
    }
    
    private void openSimulationWindow(Messung messung) throws IOException {
        //open Simulation window. this should be moved / integrated somewhere else in the future. maybe with a button.
        URL url_simulation = getClass().getResource("/View/SimulationWindow.fxml");

        FXMLLoader fxmlloader_simulation = new FXMLLoader();
        fxmlloader_simulation.setLocation(url_simulation);

        Parent simulation = fxmlloader_simulation.load(url_simulation.openStream());
        ((SimulationWindowVC) fxmlloader_simulation.getController()).setOriginalPakets(messung.getZeitintervalle().get(0).getChartSeriesRaw());
        ((SimulationWindowVC) fxmlloader_simulation.getController()).startUp();

        Stage simulation_newStage = new Stage();
        simulation_newStage.getIcons().add(new Image(getClass().getResourceAsStream("/Pictures/Cloud_Icon.jpg")));
        Scene simulation_scene = new Scene(simulation);
        simulation_scene.getStylesheets().add("/CSS/chartLines.css");   //stylesheet only gives chart series distinct colors (blue & red atm)

        simulation_newStage.setTitle("Simulation von zusätzlicher Last");
        simulation_newStage.setScene(simulation_scene);
        simulation_newStage.show();
    }
    
    public void handleImageHilfe() throws IOException {
            //Hilfeseite öffnen
        URL url_hilfe = getClass().getResource("/View/HilfeWindow.fxml");

        FXMLLoader fxmlloader_hilfe = new FXMLLoader();
        fxmlloader_hilfe.setLocation(url_hilfe);

        Parent hilfe = fxmlloader_hilfe.load(url_hilfe.openStream());

        Stage hilfe_stage = new Stage();
        hilfe_stage.setMaximized(true);
        hilfe_stage.getIcons().add(new Image(getClass().getResourceAsStream("/Pictures/Cloud_Icon.jpg")));
        Scene hilfe_scene = new Scene(hilfe);

        hilfe_stage.setTitle("Hilfe");
        hilfe_stage.setScene(hilfe_scene);
        hilfe_stage.show();
    
    }
}
