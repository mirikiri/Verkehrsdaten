/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_Window;

import datennetz_simulation.Datennetz_Simulation;
import static datennetz_simulation.Datennetz_Simulation.menucontrol;
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
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.AnonymizePCAPNG;
import model.CreateVKjson;
import model.Zeitintervall;
import org.apache.commons.io.FilenameUtils;


public class MainWindowVC implements Initializable {

    private final CreateVKjson vk = new CreateVKjson();
    private final AnonymizePCAPNG anonymizePCAPNG = new AnonymizePCAPNG();
    private final Read_pcapng pcapngReader = new Read_pcapng();

    private String path;
    private String fileName;
    private FileChooser fileChooser_auswerten;
    private FileChooser fileChooser_anonymisieren;

    private Stage homeWindow = Datennetz_Simulation.parentWindow;
    private Stage menu = new Stage();
    
    @FXML
    public static Text actiontarget;
    @FXML
    private BorderPane rootPane;
    @FXML
    private ImageView hilfeImage;
    @FXML
    private GridPane grid_menu;
    @FXML
    private ImageView image_icon;
    @FXML
    private ImageView image_menu_off;

    
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
                        menucontrol.readFile(db.getFiles().get(0), "Auswertung");
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
        //setAnimation(testi);
    }
    
    @FXML
    protected void handleProfilButtonAction(ActionEvent event) throws IOException {
        menucontrol.handlemenuprofil();

    }

    @FXML
    protected void handleMessungButtonAction(ActionEvent event) throws IOException {
        menucontrol.handlemenumessung();
    }
    
    @FXML
    protected void handleAuswertungButtonAction(ActionEvent event) throws IOException {
        menucontrol.handlemenuauswertung();
    }


    //restore verkehrsprofile.json
    @FXML
    private void restoreVkpJSON(ActionEvent event) {
        //starts confirmation dialog. If Ok pressed, try to create new verkehrsprofile.json and show success/fail dialog
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("verkehrsprofile.json wiederherstellen");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/Pictures/Cloud_Icon.jpg").toString()));
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
                stage.getIcons().add(new Image(this.getClass().getResource("/Pictures/Cloud_Icon.jpg").toString()));
                success.setHeaderText(null);
                success.setContentText("verkehrsprofile.json erfolgreich wiederhergestellt");
                success.showAndWait();
            } else {
                Alert fail = new Alert(AlertType.WARNING);
                fail.setTitle("Warnung - Fehler");
                stage = (Stage) fail.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(this.getClass().getResource("/Pictures/Cloud_Icon.jpg").toString()));
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

    
    protected void setAnimation(Stage stage, boolean dir){
              
        KeyValue startKeyValue;
        KeyValue endKeyValue;
        
        // Create the Scene
        Scene scene = new Scene(grid_menu);
        
        // Add the Scene to the Stage
        stage.setScene(scene);
                
        // Display the Stage
        stage.show();
        
        // Get the Width of the Text
        double textWidth = grid_menu.getLayoutBounds().getWidth();
        
        // Define the Durations
        Duration startDuration = Duration.ZERO;
        Duration endDuration = Duration.millis(50000);
        
        // Create the start and end Key Frames
        if(dir = true){
            startKeyValue = new KeyValue(grid_menu.translateXProperty(), 0);
            endKeyValue = new KeyValue(grid_menu.translateXProperty(), textWidth);
        }
        else{
            startKeyValue = new KeyValue(grid_menu.translateXProperty(), textWidth);
            endKeyValue = new KeyValue(grid_menu.translateXProperty(), 0);
        }
            KeyFrame startKeyFrame = new KeyFrame(startDuration, startKeyValue);
            KeyFrame endKeyFrame = new KeyFrame(endDuration, endKeyValue);
        
        // Create a Timeline
        Timeline timeline = new Timeline(startKeyFrame, endKeyFrame);
        
        // Run the animation
        timeline.play();
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

    @FXML
    private void handleSimulationButton(ActionEvent event) throws IOException {
        menucontrol.setSimulation();
    }
    
}

