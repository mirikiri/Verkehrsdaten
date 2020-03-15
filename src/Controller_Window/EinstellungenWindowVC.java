/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_Window;

import datennetz_simulation.Datennetz_Simulation;
import static datennetz_simulation.Datennetz_Simulation.menucontrol;
import static datennetz_simulation.Datennetz_Simulation.targetsystem;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Miriam
 */
public class EinstellungenWindowVC implements Initializable {

    
    final ToggleGroup group = new ToggleGroup();
    
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
    private TextField input_IP;
    @FXML
    private RadioButton radio_Linux;
    @FXML
    private RadioButton radio_Windows;
    @FXML
    private ImageView hilfeImage;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        radio_Linux.setToggleGroup(group);
        radio_Linux.setSelected(true);
        radio_Windows.setToggleGroup(group);
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
    private void handle_radio_Windows(ActionEvent event) {
        targetsystem = "Windows";
        System.out.println("MIRI: " + targetsystem);
    }

    @FXML
    private void handle_radio_Linux(ActionEvent event) {
        targetsystem = "Linux";
        System.out.println("MIRI: " + targetsystem);
    }
    
    
}
