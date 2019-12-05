/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_CellFactory;

import model.PaketLength;
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
public class HistogramDataListViewCell extends ListCell<PaketLength>{
    @FXML
    private Label length;
    @FXML
    private Label count;
    @FXML
    private HBox box;

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(PaketLength paketlength, boolean empty) {
        super.updateItem(paketlength, empty);

        if(empty || paketlength == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/Cells/HistgramDataListCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            
            if (paketlength.getLength().equals(1561)) {
                length.setText("> 1560");
            } else {
                length.setText(String.valueOf(paketlength.getLength()));
            }
            count.setText(String.valueOf(paketlength.getCount()));
            
            setText(null);
            setGraphic(box);
        }
    }    
}
