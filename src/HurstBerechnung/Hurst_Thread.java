/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HurstBerechnung;

import model.Hurst;
import model.Zeitintervall;
import java.util.List;
import javafx.application.Platform;

/**
 *
 * @author Admin
 */
public class Hurst_Thread implements Runnable {

    private final Zeitintervall interval;
    private final List<BerechnungsMethodenInterface> methods;

    public Hurst_Thread(List<BerechnungsMethodenInterface> methods, Zeitintervall interval) {
        this.methods = methods;
        this.interval = interval;
    }

    //dirty fix to avoid IllegalStateException: Not on FX application thread
    @Override
    public void run() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                methods.forEach((method) -> {
                    interval.getHurst_Faktoren().add(new Hurst(method.getName(), method.getClass().getSimpleName(), method.berechne(interval.getPaketsPerInterval())));
                });
            }
        });

    }

}
