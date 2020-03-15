/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_Window;

import Profile.Profil;
import Profile.tableProfil;
import datennetz_simulation.Datennetz_Simulation;
import static datennetz_simulation.Datennetz_Simulation.menucontrol;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Miriam
 */
public class ProfileWindowVC implements Initializable {

    
    private Stage homeWindow = Datennetz_Simulation.parentWindow;
    private FileChooser fileChooser_profil;
    private String path;
    private String fileName;
    private String newSignal;
    private Profil Order;
    private int Anzahl;
    private int i;
    
    private List<String> newProfileOrder = new ArrayList<String>();    
    private List<String> newProfileDuration = new ArrayList<String>();
    
    @FXML
    private BorderPane rootPane;
    @FXML
    private Text savedinfo_Text;
    @FXML
    private ImageView hilfeImage;

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
    private ChoiceBox<String> choice_profil;
    @FXML
    private TextField input_name;
    @FXML
    private ChoiceBox<String> choice_signal;
    @FXML
    private TextField input_dauer;
    @FXML
    private TextField input_anzahl;
    @FXML
    private Button button_datei;
    @FXML
    private Pane pane;
    @FXML
    private TableView<tableProfil> table_inhalt;
    @FXML
    private TableColumn<tableProfil, String> column_signal;
    @FXML
    private TableColumn<tableProfil, String> column_Anzahl;
    @FXML
    private TableColumn<tableProfil, ImageView> column_entfernen;
    
    /**
     * Initializes the controller class.
     */
    private tableProfil test1 = new tableProfil("VoIP", "2");
    private tableProfil test2 = new tableProfil("Youtube720p", "1");
    private tableProfil test3 = new tableProfil("gaming", "1");
    
            

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //temp tabelle
        column_signal.setCellValueFactory( new PropertyValueFactory<tableProfil, String>("signal"));
        column_entfernen.setCellValueFactory( new PropertyValueFactory<tableProfil, ImageView>("entf"));
        column_Anzahl.setCellValueFactory( new PropertyValueFactory<tableProfil, String>("anzahl"));
        
        table_inhalt.setItems(getData());
        
        choice_signal.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            newSignal = newValue;
            System.out.println("MIRI: Signal hinzufügen? " + newSignal);  
        });
        choice_profil.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            newSignal = newValue;
            System.out.println("MIRI: " + newSignal);
            if(newSignal.equals("Aus Messung generieren")){
                button_datei.setVisible(true);
                pane.setVisible(false);
            }
            else{
                button_datei.setVisible(false);
                pane.setVisible(true);
            }
                
        });
        pane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        

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
    }

    @FXML
    private void handle_ausDatei(MouseEvent event) throws IOException {
        Stage fileChooseStage = new Stage();
        if (fileChooser_profil == null) {
            fileChooser_profil = new FileChooser();
            fileChooser_profil.setInitialDirectory(new File(System.getProperty("user.home")));
            fileChooser_profil.getExtensionFilters().add(new FileChooser.ExtensionFilter("File", "*.csv", "*.pcapng"));
            fileChooser_profil.getExtensionFilters().add(new FileChooser.ExtensionFilter("pcapng", "*.pcapng"));
        }

        File file = fileChooser_profil.showOpenDialog(fileChooseStage);
        
        if (file != null) {
            path = file.getAbsolutePath();
            fileName = file.getName();
        } else {
            //MainWindowVC.actiontarget.setText("keine Datei ausgewählt");
            return;
        }
            //MainWindowVC.actiontarget.setText("Ausgewertet: " + fileName);
        
        
        System.out.println("MIRI: " + path + fileName);
        
        if (fileChooser_profil != null) {
            String dir = path.replace(fileName, "");
            fileChooser_profil.setInitialDirectory(new File(dir));
        }
        
        //TOBI: generiere profil
    }

    @FXML
    private void handle_hinzufuegen(MouseEvent event) {
        //alle Parameter einlesen
        Anzahl = Integer.parseInt(input_anzahl.getCharacters().toString().replaceAll("[^\\d]", ""));    
        for(i = 0; i <= Anzahl; i++){
            newProfileOrder.add(newSignal);
            newProfileDuration.add(input_dauer.getCharacters().toString().replaceAll("[^\\d]", ""));
        }
        //neues Objekt Profil erstellen und zuweisen
        //speichere neues Profil
    }


    @FXML
    private void handle_Speichern(ActionEvent event) {
       
    }

    private ObservableList<tableProfil> getData() {
        ObservableList<tableProfil> huch = FXCollections.observableArrayList();
        huch.add(test1);
        huch.add(test2);
        huch.add(test3);
        
        return huch;
    }
    
}
