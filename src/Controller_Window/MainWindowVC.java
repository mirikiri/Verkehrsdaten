/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_Window;

import model.Messung;
import model.Paket;
import filereader.ReadWireSharkCSV;
import filereader.Read_pcapng;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.AnonymizePCAPNG;
import model.CreateVKjson;
import org.apache.commons.io.FilenameUtils;

public class MainWindowVC implements Initializable {

    private final CreateVKjson vk = new CreateVKjson();
    private final ReadWireSharkCSV csvReader = new ReadWireSharkCSV();
    private final Read_pcapng pcapngReader = new Read_pcapng();
    private final AnonymizePCAPNG anonymizePCAPNG = new AnonymizePCAPNG();

    private String path;
    private String fileName;
    private FileChooser fileChooser_auswerten;
    private FileChooser fileChooser_anonymisieren;

    @FXML
    private Text actiontarget;
    @FXML
    private GridPane rootPane;

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) throws IOException {

        Stage fileChooseStage = new Stage();
        if (fileChooser_auswerten == null) {
            fileChooser_auswerten = new FileChooser();
            fileChooser_auswerten.setInitialDirectory(new File(System.getProperty("user.home")));
            fileChooser_auswerten.getExtensionFilters().add(new FileChooser.ExtensionFilter("File", "*.csv", "*.pcapng"));
            fileChooser_auswerten.getExtensionFilters().add(new FileChooser.ExtensionFilter("pcapng", "*.pcapng"));
        }

        File file = fileChooser_auswerten.showOpenDialog(fileChooseStage);
        readFile(file);
    }

    public void readFile(File file) throws IOException {
        if (file != null) {
            path = file.getAbsolutePath();
            fileName = file.getName();
        } else {
            actiontarget.setText("keine Datei ausgewählt");
            return;
        }
        actiontarget.setText("Ausgewertet: " + fileName);

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
        
        //if user cancelled the reading of a big file, pakets will be null. function stops here
        if (pakets == null) {
            return;
        }
        
        Messung messung = new Messung(path, pakets);
        
        if (messung.getBigFile()) {
            showBigFileAlert(messung);
        }
        
        openAuswertungsWindow(messung);

        openWarnungenWindow(messung);

        openSimulationWindow(messung);
    }

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
        auswertung_stage.getIcons().add(new Image(getClass().getResourceAsStream("/Pictures/icon.jpg")));
        Scene auswertung_scene = new Scene(auswertung);

        auswertung_stage.setTitle(fileName + "  -  Auswertung");
        auswertung_stage.setScene(auswertung_scene);
        auswertung_stage.show();
    }

    public void openWarnungenWindow(Messung messung) throws IOException {
        //open warnungen window
        URL url_warnung = getClass().getResource("/View/WarnungenWindow.fxml");

        FXMLLoader fxmlloader_warnung = new FXMLLoader();
        fxmlloader_warnung.setLocation(url_warnung);

        Parent warnung = fxmlloader_warnung.load(url_warnung.openStream());
        ((WarnungenWindowVC) fxmlloader_warnung.getController()).setCurrentMessung(messung);
        ((WarnungenWindowVC) fxmlloader_warnung.getController()).startUp();

        Stage warnung_newStage = new Stage();
        warnung_newStage.getIcons().add(new Image(getClass().getResourceAsStream("/Pictures/icon.jpg")));
        Scene warnung_scene = new Scene(warnung);

        warnung_newStage.setTitle(fileName + "  -  Warnungen");
        warnung_newStage.setScene(warnung_scene);
        warnung_newStage.show();
    }

    public void openSimulationWindow(Messung messung) throws IOException {
        //open Simulation window. this should be moved / integrated somewhere else in the future. maybe with a button.
        URL url_simulation = getClass().getResource("/View/SimulationWindow.fxml");

        FXMLLoader fxmlloader_simulation = new FXMLLoader();
        fxmlloader_simulation.setLocation(url_simulation);

        Parent simulation = fxmlloader_simulation.load(url_simulation.openStream());
        ((SimulationWindowVC) fxmlloader_simulation.getController()).setOriginalPakets(messung.getZeitintervalle().get(0).getChartSeriesRaw());
        ((SimulationWindowVC) fxmlloader_simulation.getController()).startUp();

        Stage simulation_newStage = new Stage();
        simulation_newStage.getIcons().add(new Image(getClass().getResourceAsStream("/Pictures/icon.jpg")));
        Scene simulation_scene = new Scene(simulation);
        simulation_scene.getStylesheets().add("/CSS/chartLines.css");   //stylesheet only gives chart series distinct colors (blue & red atm)

        simulation_newStage.setTitle("Simulation von zusätzlicher Last");
        simulation_newStage.setScene(simulation_scene);
        simulation_newStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rootPane.setOnDragOver(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != rootPane
                        && event.getDragboard().hasFiles()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

        rootPane.setOnDragDropped(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    try {
                        readFile(db.getFiles().get(0));
                    } catch (IOException ex) {
                        Logger.getLogger(MainWindowVC.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    success = true;
                }
                /* let the source know whether the string was successfully 
                 * transferred and used */
                event.setDropCompleted(success);

                event.consume();
            }
        });
    }

    //restore verkehrsprofile.json
    @FXML
    private void restoreVkpJSON(ActionEvent event) {
        //starts confirmation dialog. If Ok pressed, try to create new verkehrsprofile.json and show success/fail dialog
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("verkehrsprofile.json wiederherstellen");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/Pictures/icon.jpg").toString()));
        alert.setHeaderText("Achtung!");
        alert.setContentText("Wiederherstellen löscht die aktuelle Version und erstellt eine neue verkehrsprofile.json.\nAktuelle Änderungen der verkehrsprofile.json gehen damit verloren.");
        alert.setGraphic(new ImageView(this.getClass().getResource("/Pictures/achtung.jpg").toString()));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            File tempFile = new File("verkehrsprofile.json");

            if (vk.parseVKToJson()) {
                Alert success = new Alert(AlertType.INFORMATION);
                success.setTitle("Erfolg");
                stage = (Stage) success.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(this.getClass().getResource("/Pictures/icon.jpg").toString()));
                success.setHeaderText(null);
                success.setContentText("verkehrsprofile.json erfolgreich wiederhergestellt");
                success.showAndWait();
            } else {
                Alert fail = new Alert(AlertType.WARNING);
                fail.setTitle("Warnung - Fehler");
                stage = (Stage) fail.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(this.getClass().getResource("/Pictures/icon.jpg").toString()));
                fail.setHeaderText(null);
                fail.setContentText("verkehrsprofile.json konnte nicht wiederhergestellt werden");
                fail.showAndWait();
            }
        } else {
            System.out.println("Cancel");
        }
    }

    //anonymize pcapng wireshark file into a csv file only containing timestamp, paketlength, protocol. Only use that anonymized file as input in this software
    @FXML
    private void anonymizePCAPNG(ActionEvent event) throws IOException {
        //create file choose window
        Stage fileChooseStage = new Stage();
        if (fileChooser_anonymisieren == null) {
            fileChooser_anonymisieren = new FileChooser();
            fileChooser_anonymisieren.setInitialDirectory(new File(System.getProperty("user.home")));
            fileChooser_anonymisieren.getExtensionFilters().add(new FileChooser.ExtensionFilter("PCAPNG", "*.pcapng"));
        }
        File file = fileChooser_anonymisieren.showOpenDialog(fileChooseStage);
        //file is null if canceled
        if (file != null) {
            path = file.getAbsolutePath();
            fileName = file.getName();
        } else {
            return;
        }
        //set directory onto file location for future fileChoose windows
        if (fileChooser_anonymisieren != null) {
            String dir = path.replace(fileName, "");
            fileChooser_anonymisieren.setInitialDirectory(new File(dir));
        }
        List<Paket> pakets = pcapngReader.readFile(path);
        anonymizePCAPNG.anonymize(pakets, path);  
    }
    
    public void showBigFileAlert(Messung messung) {
        Alert alert = new Alert(AlertType.WARNING);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/Pictures/icon.jpg").toString()));
        alert.setHeaderText("Sehr lange Messung!");
        alert.setContentText("Die eingelesene Messung ist " + messung.getDuration() + " Sekunden lang. Aus Performance Gründen ist im Zeitintervall 10ms nur jeder 10te Wert erfasst und weiterhin der Burst-Graph deaktiviert.\n\nDie Verarbeitung kann einen Moment dauern. Bitte haben Sie etwas Geduld.");

        alert.showAndWait();
    }
}
