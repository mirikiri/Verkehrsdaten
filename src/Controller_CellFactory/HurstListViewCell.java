/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_CellFactory;

import model.Hurst;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

/**
 *
 * @author Admin
 */
public class HurstListViewCell extends ListCell<Hurst>{
    @FXML
    private Label methode;
    @FXML
    private Label hurst_wert;
    @FXML
    private HBox box;

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(Hurst hurst, boolean empty) {
        super.updateItem(hurst, empty);

        if(empty || hurst == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/Cells/HurstListCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            methode.setText(hurst.getMethode());
            Tooltip.install(methode, new Tooltip(hurst.getFull_name()));
            if (hurst.getHurst_wert() == -1.0) {
                hurst_wert.setText("N / A");
                Tooltip.install(hurst_wert, new Tooltip("Failed: Data was too short"));
            } else if (hurst.getHurst_wert() == -2.0) {
                hurst_wert.setText("N / A");
                Tooltip.install(hurst_wert, new Tooltip("Method failed"));
            }  else if(hurst.getHurst_wert() < 0.5){
                hurst_wert.setText("N / A:  < 0.5");
                Tooltip.install(hurst_wert, new Tooltip("value was: " + hurst.getHurst_wert()));
            } else if(hurst.getHurst_wert() > 1.0) {
                hurst_wert.setText("N / A:  > 1.0");
                Tooltip.install(hurst_wert, new Tooltip("value was: " + hurst.getHurst_wert()));
            } else {
                hurst_wert.setText(String.valueOf(hurst.getHurst_wert()));
            }
            
            setText(null);
            setGraphic(box);
        }

    }
    
}
