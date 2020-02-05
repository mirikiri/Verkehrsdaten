/*
 *  Original Matlab-Code Author: Chu Chen
 *  Version 1.0,  03/10/2008
 *  chen-chu@163.com
 *  https://de.mathworks.com/matlabcentral/fileexchange/19148-hurst-parameter-estimate
 *
 *  code translation to Java: Florian Mederer
 */
package HurstBerechnung;

import Polyfitting.Polyfit;
import Polyfitting.Polyval;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jtransforms.fft.DoubleFFT_1D;


public class Method_Modified_Periodogram implements BerechnungsMethodenInterface {

    private String name;

    public Method_Modified_Periodogram(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double berechne(List<Integer> sequence) {
        if (sequence.size() < 30) {
            return -1.0;
        }
        int boxnumber = 50;

        double[] sequenceAsArray = new double[sequence.size()];
        for (int i = 0; i < sequenceAsArray.length; i++) {
            sequenceAsArray[i] = sequence.get(i);
        }

        double n = sequenceAsArray.length;
        DoubleFFT_1D fft = new DoubleFFT_1D((int) n);
        fft.realForward(sequenceAsArray);

        double[] P = Matrix_Calc.elementPower(MatlabFunctions.fft_abs(sequenceAsArray), 2);
        for (int i = 0; i < P.length; i++) {
            P[i] = P[i] / (2 * Math.PI * n);
        }

        double cut_min = Math.ceil(0.0001 * n / 2);

        double[] M = MatlabFunctions.logspace(Math.log10(cut_min), Math.log10(0.1 * n - cut_min), boxnumber + 1, 10);
        for (int i = 0; i < M.length; i++) {
            M[i] = Math.floor(M[i]);
        }

        List<Double> M_uniques = MatlabFunctions.unique(M);

        double N = M_uniques.size() - 1;
        if (N < 2.0) {
            return - 1.0;
        }

        double[] x = new double[(int) N];
        double[] y = new double[(int) N];

        for (int i = 0; i < N; i++) {
            double m1 = M_uniques.get(i) + cut_min;
            double m2 = M_uniques.get(i + 1) + cut_min;
            x[i] = Math.log10((Math.PI * (m2 - m1)) / n);
            double[] y_help = new double[(int) (m2 - m1 + 1)];
            int count = 0;
            for (int j = (int) m1 - 1; j < m2; j++) {
                y_help[count++] = P[j];
            }
            y[i] = Math.log10(MatlabFunctions.sum(y_help) / (m2 - m1 + 1));
        }
        Polyfit polyfit;
        Polyval polyval_X = null;
        
        int cheapTestForSingular = 0;
        for (int i = 1; i < x.length; i++) {
            if (x[i] == x[i-1]) {
                cheapTestForSingular++;
            }
        }
        if (cheapTestForSingular == x.length-1) {
            return -2.0;
        }

        try {
            polyfit = new Polyfit(x, y, 1);
            polyval_X = new Polyval(x, polyfit);
        } catch (Exception ex) {
            Logger.getLogger(Method_RS.class.getName()).log(Level.SEVERE, null, ex);
        }
        double[] YFit = polyval_X.getYout();

        double hurstFaktor = Math.round((1 - (YFit[YFit.length - 1] - YFit[0]) / (x[x.length - 1] - x[0])) / 2 * 10000d) / 10000d;
        return hurstFaktor;
    }

}
