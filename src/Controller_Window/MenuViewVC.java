/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_Window;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Miriam
 */
public class MenuViewVC implements Initializable{

    @FXML
    private Label label_Messung;
    @FXML
    private Label label_profil;
    @FXML
    private Label label_auswertung;
    @FXML
    private Label label_einstellung;
    @FXML
    private GridPane grid_menu;
    @FXML
    private ImageView image_icon;
    
    private FileChooser fileChooser_auswerten;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    @FXML
    private void handlemenumessung(MouseEvent event) {
        //Miri
        //Neue seite aufrufen um Messung zu Starten
    /*
        URL url_messung = getClass().getResource("/View/Temp_StarteMessung.fxml");
        
        FXMLLoader fxmlloader_messung = new FXMLLoader();
        fxmlloader_messung.setLocation(url_messung);

        Parent messung = fxmlloader_messung.load(url_messung.openStream());

        Stage messung_newStage = new Stage();
        Scene messung_scene = new Scene(messung);

        messung_newStage.setTitle("Messungen starten");
        messung_newStage.setScene(messung_scene);
        messung_newStage.show();*/
    }

    @FXML
    private void handlemenuprofil(MouseEvent event) {
    }

    @FXML
    private void handlemenuauswertung(MouseEvent event) {
        /*System.out.println("MIRI: Button Messung auswerten");
        Stage fileChooseStage = new Stage();
        if (fileChooser_auswerten == null) {
            fileChooser_auswerten = new FileChooser();
            fileChooser_auswerten.setInitialDirectory(new File(System.getProperty("user.home")));
            fileChooser_auswerten.getExtensionFilters().add(new FileChooser.ExtensionFilter("File", "*.csv", "*.pcapng"));
            fileChooser_auswerten.getExtensionFilters().add(new FileChooser.ExtensionFilter("pcapng", "*.pcapng"));
        }

        File file = fileChooser_auswerten.showOpenDialog(fileChooseStage);
        MainWindowVC.readFile(file);*/
    }

    @FXML
    private void handlemenueinstellungen(MouseEvent event) {
    }
    
}
