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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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
import javafx.scene.control.TableCell;
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
    private String saveName;
    private String newSignal;
    private int anzahlInt;
    private String anzahlString;
    private int maxAnzahl;
    private int i;
    private ObservableList<tableProfil> list = FXCollections.observableArrayList();
    private tableProfil newprof;
    private List<String> newProfileOrder = new ArrayList<String>();    
    
    @FXML
    private BorderPane rootPane;
    @FXML
    private Text savedinfo_Text;
    @FXML
    private ImageView hilfeImage;
    @FXML
    private GridPane grid_menu;
    @FXML
    private ChoiceBox<String> choice_profil;
    @FXML
    private TextField input_name;
    @FXML
    private ChoiceBox<String> choice_signal;
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
    @FXML
    private ImageView image_menu_off;
    @FXML
    private ImageView image_icon;
    
            

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //temp tabelle
        column_signal.setCellValueFactory( new PropertyValueFactory<tableProfil, String>("signal"));
        column_entfernen.setCellValueFactory( new PropertyValueFactory<tableProfil, ImageView>("entf"));
        column_Anzahl.setCellValueFactory( new PropertyValueFactory<tableProfil, String>("anzahl"));
        
        list.clear();
        maxAnzahl = 0;
        
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
        anzahlString = input_anzahl.getCharacters().toString().replaceAll("[^\\d]", "");
        anzahlInt = Integer.parseInt(anzahlString);
        maxAnzahl += anzahlInt;
        if(maxAnzahl>11){
            System.out.println("maximale Anzahl bereits erreicht");
            savedinfo_Text.setText("Zu viele Signale! Maximale Anzahl für das Raspberrysystem beträgt 11");
            maxAnzahl -= anzahlInt;
        }
        else{
            for(i = 0; i <= anzahlInt; i++){
                newProfileOrder.add(newSignal);
            }
            //neues Objekt Profil erstellen und zuweisen
            newprof = new tableProfil(newSignal, anzahlString);
            list.add(newprof);
            table_inhalt.setItems(list);
        }
    }


    @FXML
    private void handle_Speichern(ActionEvent event) throws FileNotFoundException{
        //speichere neues Profil
        saveName = input_name.getCharacters().toString();
        createList();
        savedinfo_Text.setText("Neues Profil " + saveName + " gespeichert!");
        //bei startemessungen hinzufügen
        //choice_Signal_Profil.add(saveName);
        
        //eingaben zurücksetzen
        list.clear();
        maxAnzahl = 0;
        table_inhalt.setItems(list);
    }

    
    @FXML
    public void entfItem(MouseEvent event)
    {
        int row=12;
        int column=4;   
        /*
        if (event.getClickCount() == 2) //Checking double click
        {
            System.out.println(table_inhalt.getSelectionModel().getSelectedItem().getSignal());
            System.out.println(table_inhalt.getSelectionModel().getSelectedItem().getAnzahl());
            System.out.println(table_inhalt.getSelectionModel().getSelectedItem().getEntf());
        }*/
        //reihe und spalte einlesen
        TableView test = (TableView) event.getSource();
        //row = ;
        //column = ;
        //spalte auf image vergleichen
        if(column == 2){
            //reihe löschen
            list.remove(row);
            //neu anzeigen
            table_inhalt.setItems(list);
            System.out.println("Reihe " + row + " gelöscht");
        }
    }

    public void createList() throws FileNotFoundException{
        PrintWriter save = new PrintWriter(saveName+".txt");
        for(int i =0; i< list.size(); i++){
            save.println(list.get(i).getSignal());
        }
        save.close();
    }
}
