/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_CellFactory;

import Auswertung_Warnungen.Warnung;
import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TitledPane;


public class WarnungenListViewC extends ListCell<Warnung> {

    @FXML
    private Label warnungen_TriggerText;
    @FXML
    private Label warnungen_beschreibung;
    @FXML
    private TitledPane titlePane;

    private FXMLLoader mLLoader;
 
    @Override
    protected void updateItem(Warnung warnung, boolean empty) {
        super.updateItem(warnung, empty);

        if (empty || warnung == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/Cells/WarnungenListCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }            
            if (Objects.equals(warnung.getTriggered(), Boolean.TRUE)) {
                titlePane.setText("Warnung: " + warnung.getTitel());
            } else {
                titlePane.setText("Information: " + warnung.getTitel());
            }
            warnungen_TriggerText.setText(warnung.getTriggerText());
            warnungen_beschreibung.setText(warnung.getBeschreibung());
     
            setText(null);
            setGraphic(titlePane);
        }
    }

}
