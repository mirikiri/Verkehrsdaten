/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_Window;

import Controller_CellFactory.VerkehrsprofilLVC;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import static datennetz_simulation.Datennetz_Simulation.menucontrol;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import model.Verkehrsprofil;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.CreateVKjson;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class SimulationWindowVC implements Initializable {

    private XYChart.Series<Number, Number> originalPakets;
    private XYChart.Series<Number, Number> copiedOriginalPakets;
    private ObservableList<Verkehrsprofil> profile;
    private XYChart.Series<Number, Number> simulatedPakets;
    private final Random rnd_generator = new Random();
    private final Gson gson = new Gson();
    List<Integer> randomPositions;
    String selectedMode = "";
    Integer worstCasePos = 0;
    @FXML
    private LineChart<Number, Number> lineChart;
    @FXML
    private ListView<Verkehrsprofil> profile_List;
    @FXML
    private ToggleGroup modeSelect;
    @FXML
    private RadioButton standard;
    @FXML
    private RadioButton random;
    @FXML
    private RadioButton worstCase;
    @FXML
    private GridPane grid_menu;
    @FXML
    private Label label_Messung;
    @FXML
    private Label label_profil;
    @FXML
    private Label label_auswertung;
    @FXML
    private Label label_einstellung;
    @FXML
    private Label label_home;
    @FXML
    private ImageView image_menu_off;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        randomPositions = new ArrayList<>();
        profile = FXCollections.observableArrayList();
        loadProfile();
        profile_List.setItems(profile);
        profile_List.setCellFactory(ProfileListView -> new VerkehrsprofilLVC(this));

        simulatedPakets = new XYChart.Series<>();
        copiedOriginalPakets = new XYChart.Series<>();

        //change listener of the mode radio button
        modeSelect.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (oldValue != null && newValue != null) {
                    RadioButton oldVal = (RadioButton) oldValue;
                    RadioButton newVal = (RadioButton) newValue;
                    
                    //for every profile: remove already added instances with old mode, then add again with the new mode
                    for (Verkehrsprofil verkehrsprofil : profile) {
                        Integer timesAdded = verkehrsprofil.getOldTimesAdded();
                        updateSimulation(verkehrsprofil, 0, oldVal.getId());
                        updateSimulation(verkehrsprofil, timesAdded, newVal.getId());
                    }

                    selectedMode = newVal.getId();
                }
            }
        });
        modeSelect.selectToggle(standard);
        selectedMode = "standard";
    }

    public void startUp() {
        //deepcopy original pakets onto simulated pakets in order to have both in the graph
        List<XYChart.Data<Number, Number>> chartPoints = new ArrayList<>();
        List<XYChart.Data<Number, Number>> chartPoints_copy = new ArrayList<>();
        int max = 0;
        for (int i = 0; i < originalPakets.getData().size(); i++) {
            int yValue = originalPakets.getData().get(i).getYValue().intValue();
            if (yValue >= max) {
                max = yValue;
                worstCasePos = i;
            }
            chartPoints.add(new XYChart.Data<>(originalPakets.getData().get(i).getXValue(), yValue));
            chartPoints_copy.add(new XYChart.Data<>(originalPakets.getData().get(i).getXValue(), yValue));
        }
        simulatedPakets.getData().addAll(chartPoints);
        copiedOriginalPakets.getData().addAll(chartPoints_copy);

        copiedOriginalPakets.setName("originale Ankunftsrate");
        simulatedPakets.setName("simulierter Gesamtverkehr");
        lineChart.getData().addAll(copiedOriginalPakets, simulatedPakets);
    }

    public void loadProfile() {
        JsonReader reader = null;
        Verkehrsprofil[] profil_array = new Verkehrsprofil[0];
        try {
            reader = new JsonReader(new FileReader("verkehrsprofile.json"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SimulationWindowVC.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            profil_array = gson.fromJson(reader, Verkehrsprofil[].class);
        } catch (com.google.gson.JsonSyntaxException e) {
            //catch when json syntax is invalid
            try {
                //closing the reader to prepare for moving the file
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(SimulationWindowVC.class.getName()).log(Level.SEVERE, null, ex);
            }
            restoreJSON();
            try {
                reader = new JsonReader(new FileReader("verkehrsprofile.json"));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SimulationWindowVC.class.getName()).log(Level.SEVERE, null, ex);
            }
            profil_array = gson.fromJson(reader, Verkehrsprofil[].class);
        }
        for (Verkehrsprofil verkehrsprofil : profil_array) {
            profile.add(verkehrsprofil);
        }
    }

    public void updateSimulation(Verkehrsprofil profil, int timesAdded) {
        if (null != selectedMode) {
            switch (selectedMode) {
                case "standard":
                    updateWithStandard(profil, timesAdded);
                    break;
                case "random":
                    updateWithRandom(profil, timesAdded);
                    break;
                case "worstCase":
                    updateWithWorstCase(profil, timesAdded);
                    break;
                default:
                    break;
            }
        }
    }

    public void updateSimulation(Verkehrsprofil profil, int timesAdded, String mode) {
        if (null != mode) {
            switch (mode) {
                case "standard":
                    updateWithStandard(profil, timesAdded);
                    break;
                case "random":
                    updateWithRandom(profil, timesAdded);
                    break;
                case "worstCase":
                    updateWithWorstCase(profil, timesAdded);
                    break;
                default:
                    break;
            }
        }
    }

    public void updateWithStandard(Verkehrsprofil profil, int timesAdded) {
        //update each dataPoint: subtract old added pakets, add new pakets. Note each data point is only changed once
        for (int i = 0; i < simulatedPakets.getData().size(); i++) {
            int sum = simulatedPakets.getData().get(i).getYValue().intValue();
            sum -= profil.getPaketsPerInterval().get(i % profil.getLength()) * profil.getOldTimesAdded();
            sum += profil.getPaketsPerInterval().get(i % profil.getLength()) * timesAdded;
            simulatedPakets.getData().get(i).setYValue(sum);
        }
        profil.setOldTimesAdded(timesAdded);
    }

    public void updateWithRandom(Verkehrsprofil profil, int timesAdded) {
        // create new random positions
        for (int i = 0; i < timesAdded; i++) {
            Integer randomPos = rnd_generator.nextInt(profil.getLength());
            randomPositions.add(randomPos);
        }
        //update each dataPoint: subtract old added pakets, add new pakets. Note each data point is only changed once
        for (int i = 0; i < simulatedPakets.getData().size(); i++) {
            int sum = simulatedPakets.getData().get(i).getYValue().intValue();
            sum -= calcNumbersToSubtractRandom(profil, i);
            sum += calcNumbersToAddRandom(profil, i);
            simulatedPakets.getData().get(i).setYValue(sum);
        }

        //delete old random values and write newly used values into profil for next update
        profil.getLastRandomValues().clear();
        for (Integer randomPosition : randomPositions) {
            profil.getLastRandomValues().add(randomPosition);
        }
        randomPositions.clear();
        profil.setOldTimesAdded(timesAdded);
    }

    public int calcNumbersToAddRandom(Verkehrsprofil profil, int position) {
        int numberToAdd = 0;
        for (int j = 0; j < randomPositions.size(); j++) {
            numberToAdd += profil.getPaketsPerInterval().get((position + randomPositions.get(j)) % profil.getLength());
        }
        return numberToAdd;
    }

    public int calcNumbersToSubtractRandom(Verkehrsprofil profil, int position) {
        int numberToSubtract = 0;
        for (int i = 0; i < profil.getLastRandomValues().size(); i++) {
            numberToSubtract += profil.getPaketsPerInterval().get((position + profil.getLastRandomValues().get(i)) % profil.getLength());
        }
        return numberToSubtract;
    }

    public void updateWithWorstCase(Verkehrsprofil profil, int timesAdded) {
        int versatz = calcVersatz(profil);

        //update each dataPoint: subtract old added pakets, add new pakets. Note each data point is only changed once
        for (int i = 0; i < simulatedPakets.getData().size(); i++) {
            int sum = simulatedPakets.getData().get(i).getYValue().intValue();
            sum -= profil.getPaketsPerInterval().get((i + versatz) % profil.getLength()) * profil.getOldTimesAdded();
            sum += profil.getPaketsPerInterval().get((i + versatz) % profil.getLength()) * timesAdded;
            simulatedPakets.getData().get(i).setYValue(sum);
        }
        profil.setOldTimesAdded(timesAdded);
    }

    public int calcVersatz(Verkehrsprofil profil) {
        int versatz = 0;
        int normalizedWorstCasePos = worstCasePos % profil.getLength();
        if (normalizedWorstCasePos > profil.getWorstCasePos()) {
            versatz = normalizedWorstCasePos - profil.getWorstCasePos() + 1;
        } else {
            versatz = Math.abs(normalizedWorstCasePos - profil.getWorstCasePos());
        }
        return versatz;
    }

    public void setOriginalPakets(XYChart.Series<Number, Number> originalPakets) {
        this.originalPakets = originalPakets;
    }

    public void restoreJSON() {
        //informs about invalid json first. then moves invalid json to another folder and creates a new json
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("verkehrsprofile.json fehlerhaft");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/Pictures/icon.jpg").toString()));
        alert.setHeaderText(null);
        alert.setContentText("Ein Fehler in der Syntax von verkehrsprofile.json wurde gefunden.\nDie fehlerhafte json wird in den Ordner Json_Fehler verschoben und eine neue verkehrsprofile.json wird erzeugt.");
        alert.setGraphic(new ImageView(this.getClass().getResource("/Pictures/achtung.jpg").toString()));
        alert.showAndWait();

        //check if folder Json_Fehler exists. if not create it
        String dirPath = "Json_Fehler";
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            //get current time to add to filename
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS");
            Date now = new Date();
            String strDate = sdf.format(now);
            //move file to folder for invalid json, by adding time to filename, user can later know which file was invalid at given time
            Files.move(Paths.get(".\\verkehrsprofile.json"), Paths.get(".\\" + dirPath + "\\verkehrsprofile_fehlerhaft_" + strDate + ".json"));
            
            // create new verkehrsprofile.json and inform user about that
            CreateVKjson vk = new CreateVKjson();
            if (vk.parseVKToJson()) {
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Erfolg");
                stage = (Stage) success.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(this.getClass().getResource("/Pictures/icon.jpg").toString()));
                success.setHeaderText(null);
                success.setContentText("verkehrsprofile.json erfolgreich wiederhergestellt");
                success.showAndWait();
            } else {
                Alert fail = new Alert(Alert.AlertType.WARNING);
                fail.setTitle("Warnung - Fehler");
                stage = (Stage) fail.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(this.getClass().getResource("/Pictures/icon.jpg").toString()));
                fail.setHeaderText(null);
                fail.setContentText("verkehrsprofile.json konnte nicht wiederhergestellt werden");
                fail.showAndWait();
            }
        } catch (IOException ex) {
            Logger.getLogger(SimulationWindowVC.class.getName()).log(Level.SEVERE, null, ex);
        }

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
