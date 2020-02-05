/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_CellFactory;

import model.PaketsPerTimeInterval;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

/**
 *
 * @author Admin
 */
public class PaketListViewCell extends ListCell<PaketsPerTimeInterval>{
    @FXML
    private Label time;
    @FXML
    private Label count;
    @FXML
    private HBox box;

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(PaketsPerTimeInterval paket, boolean empty) {
        super.updateItem(paket, empty);

        if(empty || paket == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/Cells/PaketListCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            time.setText(paket.getTime());
            count.setText(String.valueOf(paket.getCount()));

            setText(null);
            setGraphic(box);
        }

    }
}
