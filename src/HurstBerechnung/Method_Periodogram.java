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


public class Method_Periodogram implements BerechnungsMethodenInterface {

    private String name;
    
    public Method_Periodogram(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double berechne(List<Integer> sequence) {
        if (sequence.size() < 22) {
            return -1.0;
        }
        double[] sequenceAsArray = new double[sequence.size()];
        for (int i = 0; i < sequenceAsArray.length; i++) {
            sequenceAsArray[i] = sequence.get(i);
        }
        
        double n = sequenceAsArray.length;
        DoubleFFT_1D fft = new DoubleFFT_1D((int)n);
        fft.realForward(sequenceAsArray);
        
        double[] P = Matrix_Calc.elementPower(MatlabFunctions.fft_abs(sequenceAsArray), 2);
        for (int i = 0; i < P.length; i++) {
            P[i] = P[i]/(2*Math.PI*n);
        }
        
        double[] x = new double[(int)n/2 - 1];
        double factor = Math.PI/n;
        for (int i = 0; i < x.length; i++) {
            x[i] = Math.log10(factor*(i+2));
        }
        double[] y = new double[(int)n/2 - 1];
        for (int i = 0; i < y.length; i++) {
            y[i] = Math.log10(P[i+1]);
        }
        
        double[] X = new double[x.length/5];
        System.arraycopy(x, 0, X, 0, X.length);
        double[] Y = new double[y.length/5];
        System.arraycopy(y, 0, Y, 0, Y.length);
        
        Polyfit polyfit;
        Polyval polyval_X = null;

        try {
            polyfit = new Polyfit(X, Y, 1);
            polyval_X = new Polyval(X, polyfit);
        } catch (Exception ex) {
            Logger.getLogger(Method_RS.class.getName()).log(Level.SEVERE, null, ex);
        }
        double[] YFit = polyval_X.getYout();
        
        double hurstFaktor = Math.round((1 - (YFit[YFit.length-1] - YFit[0])/ (X[X.length-1] - X[0]))/2 * 10000d) / 10000d;
        return hurstFaktor;
    }

}
