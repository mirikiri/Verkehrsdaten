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
public class CDFDataListViewCell extends ListCell<PaketLength>{
    @FXML
    private Label length;
    @FXML
    private Label percent;
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
                mLLoader = new FXMLLoader(getClass().getResource("/Cells/CDFDataCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (paketlength.getLength().equals(1561)) {
                length.setText("> 1560");
                percent.setText("");
            } else {
                length.setText(String.valueOf(paketlength.getLength()));
                percent.setText(String.format("%,.1f", (paketlength.getPercent()*100)));
            }
              
            
            
            setText(null);
            setGraphic(box);
        }
    }    
}
