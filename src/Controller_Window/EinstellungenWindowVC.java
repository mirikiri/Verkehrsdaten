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
import static datennetz_simulation.Datennetz_Simulation.start_IP;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javafx.scene.text.Text;

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
    private TextField input_IP;
    @FXML
    private RadioButton radio_Linux;
    @FXML
    private RadioButton radio_Windows;
    @FXML
    private ImageView hilfeImage;
    @FXML
    private Text error_text;
    
    
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

    @FXML
    private void handleSaveButton(MouseEvent event) {
        //Überorüfen ob valid IP
        if(validIP(input_IP.getCharacters().toString())){
            start_IP = input_IP.getCharacters().toString();
            error_text.setText("New IP is: " + start_IP);
        }
        else{
            System.out.println("not a valid IP");
            error_text.setText("not a valid IP");
        }
    }
    
    public static boolean validIP(String ip) {
        if (ip == null || ip.isEmpty()) 
            return false;
        ip = ip.trim();
        if ((ip.length() < 6) & (ip.length() > 15)) 
            return false;

        try {
            Pattern pattern = Pattern.compile("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
            Matcher matcher = pattern.matcher(ip);
            return matcher.matches();
        } catch (PatternSyntaxException ex) {
            return false;
        }
    }
    
}
