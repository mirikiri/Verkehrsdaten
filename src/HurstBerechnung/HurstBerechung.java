/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HurstBerechnung;

import model.Zeitintervall;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class HurstBerechung {

    private final List<BerechnungsMethodenInterface> methods = new ArrayList<>();

    public HurstBerechung() {
        methods.add(new Method_RS("R / S"));
        methods.add(new Method_Periodogram("PerGr"));
        methods.add(new Method_Residuals_Of_Regression("ResReg"));
        methods.add(new Method_Difference_Variance("DiffVar"));
        methods.add(new Method_Aggregate_Variance("AggVar"));
        methods.add(new Method_Absolute_Moment("Abs Mom"));
        methods.add(new Method_Modified_Periodogram("M_PerGr"));
    }

    public void berechneHurst(Zeitintervall interval) {
        Hurst_Thread hurst_Thread = new Hurst_Thread(methods, interval);
        Thread hurst = new Thread(hurst_Thread);
        hurst.start();
    }

}
