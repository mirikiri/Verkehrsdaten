/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_CellFactory;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import model.Verkehrsprofil;
import Controller_Window.SimulationWindowVC;
import java.util.function.UnaryOperator;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Admin
 */
public class VerkehrsprofilLVC extends ListCell<Verkehrsprofil> {

    SimulationWindowVC window_controller;

    public VerkehrsprofilLVC(SimulationWindowVC controller) {
        this.window_controller = controller;
    }

    @FXML
    private Label name;
    @FXML
    private TextField input;
    @FXML
    private HBox box;

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(Verkehrsprofil profil, boolean empty) {
        super.updateItem(profil, empty);
        if (empty || profil == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/Cells/VerkehrsprofileCell.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            name.setText(profil.getName());

            //////////////////////////////////////////////////////
            // before keystroke is sent to textfield, textformatter checks if it is a number
            UnaryOperator<Change> filter = change -> {
                String text = change.getText();

                if (text.matches("[0-9]*")) {
                    return change;
                }

                return null;
            };
            TextFormatter<String> textFormatter = new TextFormatter<>(filter);
            input.setTextFormatter(textFormatter);
            ///////////////////////////////////////////////////////

            input.textProperty().addListener((observable, oldValue, newValue) -> {
                if ("".equals(input.getText())) {
                    window_controller.updateSimulation(profil,0);
//                    profil.setTimesAdded(0);
                } else {
                    window_controller.updateSimulation(profil, Integer.parseInt(input.getText()));
//                    profil.setTimesAdded(Integer.parseInt(input.getText()));
                }
//                window_controller.updateSimulation();

            });
            setText(null);
            setGraphic(box);
        }
    }
}
