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
public class CDF {

    private List<PaketLength> paketlengths;
    private ObservableList<PaketLength> shown_Paketlengths;
    private XYChart.Series<Number, Number> cdf_series;

    public CDF(List<Paket> pakets, Protocol protocol) {
        int count = 0, paketCount = 0;
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
            paketCount = pakets.size();
        } else {
            for (Paket paket : pakets) {
                if (paket.getProtocol() == protocol) {
                    if (paket.getPaketlength() > 1561) {
                        paketlengths.get(1561).countUp();
                        count++;
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
                        count++;
                    }
                }
            }
            paketCount = count;
        }

        paketlengths.get(0).setPercent(new Double(paketlengths.get(0).getCount()) / paketCount);
        for (int i = 1; i < paketlengths.size(); i++) {
            paketlengths.get(i).setPercent(new Double(paketlengths.get(i).getCount()) / paketCount + paketlengths.get(i - 1).getPercent());
        }

        cdf_series = new XYChart.Series<>();
        for (int i = 0; i < paketlengths.size(); i++) {
            cdf_series.getData().add(new XYChart.Data<>(i, paketlengths.get(i).getPercent()));
        }

        if (biggerThanThreshold.size() > 0) {
            Collections.sort(biggerThanThreshold);
            paketlengths.addAll(biggerThanThreshold);
            paketlengths.get(paketlengths.size() - biggerThanThreshold.size()).setPercent(new Double(paketlengths.get(paketlengths.size() - biggerThanThreshold.size()).getCount()) / paketCount + paketlengths.get(paketlengths.size() - biggerThanThreshold.size() - 2).getPercent());
            for (int i = (paketlengths.size() - biggerThanThreshold.size() + 1); i < paketlengths.size(); i++) {
                paketlengths.get(i).setPercent(new Double(paketlengths.get(i).getCount()) / paketCount + paketlengths.get(i - 1).getPercent());
            }
        }
        shown_Paketlengths.addAll(paketlengths);
    }

    /**
     * @return the paket_length_series
     */
    public ObservableList<XYChart.Series<Number, Number>> get_cdf_series() {
        return FXCollections.observableArrayList(Collections.singleton(cdf_series));
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
