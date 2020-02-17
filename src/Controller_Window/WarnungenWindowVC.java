/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_Window;

import Auswertung_Warnungen.W_csvStruktur;
import Auswertung_Warnungen.W_jumboPakete;
import Auswertung_Warnungen.W_kleinePakete;
import Auswertung_Warnungen.W_meanPaketLength;
import Auswertung_Warnungen.W_peakAnkunftsrate;
import Auswertung_Warnungen.Warnung;
import Controller_CellFactory.WarnungenListViewC;
import com.google.gson.Gson;
import model.Messung;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.util.Callback;
import org.apache.commons.io.IOUtils;

/**
 * FXML Controller class
 *
 * @author Florian
 */
public class WarnungenWindowVC implements Initializable {

    private Messung currentMessung;
    private final List<Warnung> warnungen = new ArrayList<>();
    private ObservableList<Warnung> shownWarnungen;
    private final Gson gson = new Gson();

    @FXML
    private ListView<Warnung> warnungenList;
    @FXML
    private ToggleButton toggleButton;
    @FXML
    private Label warnungenStatus;
    @FXML
    private ScrollPane scrollRoot;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setCurrentMessung(Messung currentMessung) {
        this.currentMessung = currentMessung;
    }

    public void startUp() {
        try {
            addAllWarnungen();
        } catch (IOException ex) {
            System.out.println("Controller_Window.WarnungenWindowVC.startUp() --- catch");
        }
        checkAllWarnungen();

        shownWarnungen = FXCollections.observableArrayList();
        buildShownWarnungenList();
        setStatusText();

        warnungenList.setItems(shownWarnungen);
        warnungenList.setCellFactory(new Callback<ListView<Warnung>, ListCell<Warnung>>() {
            @Override
            public ListCell<Warnung> call(ListView<Warnung> list) {
                return new WarnungenListViewC();
            }
        });
       

    }

    public void addAllWarnungen() throws IOException {       
        warnungen.add(new W_csvStruktur("CSV-Struktur", readFileAsResource("/warnung_texte/w_csvStruktur.txt")));
        warnungen.add(new W_kleinePakete("kleine Pakete", readFileAsResource("/warnung_texte/w_kleinePakete.txt")));
        warnungen.add(new W_jumboPakete("Jumbo-Pakete", readFileAsResource("/warnung_texte/w_jumboPakete.txt")));
        warnungen.add(new W_peakAnkunftsrate("größter Peak", readFileAsResource("/warnung_texte/w_peakAnkunftsrate.txt")));
        warnungen.add(new W_meanPaketLength("mittlere Paketlänge", readFileAsResource("/warnung_texte/w_meanPaketLength.txt")));
    }
    
    public String readFileAsResource(String path) throws IOException {
        InputStream is  = getClass().getResourceAsStream(path); 
        StringWriter sw = new StringWriter();
        IOUtils.copy(is, sw, StandardCharsets.UTF_8);
        return sw.toString();
    }

    static String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public void checkAllWarnungen() {
        for (Warnung warnung : warnungen) {
            warnung.check_Trigger(currentMessung);
        }
    }

    public void buildShownWarnungenList() {
        shownWarnungen.removeAll(warnungen);
        for (Warnung warnung : warnungen) {
            if (Objects.equals(warnung.getTriggered(), Boolean.TRUE)) {
                shownWarnungen.add(warnung);
            }
        }
    }

    public void showAllWarnungen() {
        for (Warnung warnung : warnungen) {
            if (Objects.equals(warnung.getTriggered(), Boolean.FALSE)) {
                shownWarnungen.add(warnung);
            }
        }
    }
    
    public void hideNotTriggeredWarnungen() {
        Iterator itr = shownWarnungen.iterator();
        while(itr.hasNext()) {
            Warnung warnung = (Warnung)itr.next();
            if (Objects.equals(warnung.getTriggered(), Boolean.FALSE)) {
                itr.remove();
            }
        }
    }

    public void setStatusText() {
        if (shownWarnungen.size() > 0) {
            if (shownWarnungen.size() == 1) {
                warnungenStatus.setText(Integer.toString(shownWarnungen.size()) + " Warnung wurde ausgelöst: (Klick auf zusätzliche Informationen zeigt alle Tests)");
            } else {
                warnungenStatus.setText(Integer.toString(shownWarnungen.size()) + " Warnungen wurden ausgelöst: (Klick auf zusätzliche Informationen zeigt alle Tests)");
            }
        } else {
            warnungenStatus.setText("Keine Warnung ausgelöst. (Klick auf zusätzliche Informationen zeigt alle Tests)");
        }
    }

    @FXML
    private void toggleAlleWarnungen(ActionEvent event) {
        Boolean buttonPressed = toggleButton.isSelected();
        if (Objects.equals(buttonPressed, Boolean.TRUE)) {
            showAllWarnungen();
        } else {
            hideNotTriggeredWarnungen();
        }
    }

    public ScrollPane getScrollRoot() {
        return scrollRoot;
    }

}
