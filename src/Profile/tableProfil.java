/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Profile;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Miriam
 */
public class tableProfil{

    private SimpleStringProperty signal;
    private SimpleStringProperty anzahl;
    private ImageView entf;

    public tableProfil(String vName, String alter) {
        this.signal = new SimpleStringProperty(vName);
        this.anzahl = new SimpleStringProperty(alter);
        this.entf = new ImageView(new Image(getClass().getResourceAsStream("/Pictures/müll.png")));
        entf.setFitHeight(20);
        entf.setFitWidth(20);
    }

    public String getSignal() {
        return signal.get();
    }

    public void setSignal(String sig) {
        this.signal = new SimpleStringProperty(sig);
    }
    
    public ImageView getEntf() {
        return entf;
    }

    public void setSignal(ImageView ent) {
        this.entf = ent;
    }


    public String getAnzahl() {
        return anzahl.get();
    }

    public void setAnzahl(String anz) {
        this.anzahl = new SimpleStringProperty(anz);
    }
}
