/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.Paket.Protocol;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

/**
 *
 * @author Admin
 */
public class PaketLengthHistogram {
    
    private List<PaketLength> paketlengths;
    private ObservableList<PaketLength> shown_Paketlengths;
    private final XYChart.Series<Number, Number> paket_length_series;

    public PaketLengthHistogram(List<Paket> pakets, Protocol protocol) {
        paketlengths = new ArrayList<>();
        shown_Paketlengths = FXCollections.observableArrayList();
        for (int i = 0; i < 1562; i++) {
            paketlengths.add(new PaketLength(i));
        }

        Boolean found;
        List<PaketLength> biggerThanThreshold = new ArrayList<>();
        if (protocol == Protocol.TOTAL) {
            for (Paket paket : pakets) {
                if (paket.getPaketlength() > 1561) {
                    paketlengths.get(1561).countUp();
                    found = Boolean.FALSE;
                    for (int i = 0; i < biggerThanThreshold.size(); i++) {
                        if (biggerThanThreshold.get(i).getLength() == paket.getPaketlength()) {
                            biggerThanThreshold.get(i).countUp();
                            found = Boolean.TRUE;
                            break;
                        }
                    }
                    if (!found) {
                        biggerThanThreshold.add(new PaketLength(paket.getPaketlength()));
                        biggerThanThreshold.get(biggerThanThreshold.size() - 1).countUp();
                    }
                } else {
                    paketlengths.get(paket.getPaketlength()).countUp();
                }
            }
        } else {
            for (Paket paket : pakets) {
                if (paket.getProtocol() == protocol) {
                    if (paket.getPaketlength() > 1561) {
                        paketlengths.get(1561).countUp();
                        found = Boolean.FALSE;
                        for (int i = 0; i < biggerThanThreshold.size(); i++) {
                            if (biggerThanThreshold.get(i).getLength() == paket.getPaketlength()) {
                                biggerThanThreshold.get(i).countUp();
                                found = Boolean.TRUE;
                                break;
                            }
                        }
                        if (!found) {
                            biggerThanThreshold.add(new PaketLength(paket.getPaketlength()));
                            biggerThanThreshold.get(biggerThanThreshold.size() - 1).countUp();
                        }
                    } else {
                        paketlengths.get(paket.getPaketlength()).countUp();
                    }
                }

            }
        }
        
        paket_length_series = new XYChart.Series<>();
        for (int i = 0; i < paketlengths.size(); i++) {
            paket_length_series.getData().add(new XYChart.Data<>(i, getPaketlengths().get(i).getCount()));
        }
        if (biggerThanThreshold.size() > 0) {
            Collections.sort(biggerThanThreshold);
            paketlengths.addAll(biggerThanThreshold);
        }
        shown_Paketlengths.addAll(paketlengths);
    }

    /**
     * @return the paket_length_series
     */
    public ObservableList<XYChart.Series<Number, Number>> getPaket_length_series() {
        return FXCollections.observableArrayList(Collections.singleton(paket_length_series));
    }

    /**
     * @return the paketlengths
     */
    public List<PaketLength> getPaketlengths() {
        return paketlengths;
    }

    public ObservableList<PaketLength> getShown_Paketlengths() {
        return shown_Paketlengths;
    }
    
    
}
