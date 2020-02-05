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


public class Method_Absolute_Moment implements BerechnungsMethodenInterface {
    
    private String name;
    
    public Method_Absolute_Moment(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double berechne(List<Integer> sequence) {
        if (sequence.size() < 25) {
            return -1.0;
        }
        
        int moment = 1;
        double[][] sequenceAsArray = new double[1][sequence.size()];
        for (int i = 0; i < sequenceAsArray[0].length; i++) {
            sequenceAsArray[0][i] = sequence.get(i);
        }

        double sequenceMean = MatlabFunctions.mean(sequenceAsArray[0]);
        for (int i = 0; i < sequenceAsArray[0].length; i++) {
            sequenceAsArray[0][i] = sequenceAsArray[0][i] - sequenceMean;
        }

        double N = sequenceAsArray[0].length;
        double mlarge = Math.floor(N / 5);

        double[] M_array = MatlabFunctions.logspace(0, Math.log10(mlarge), 50, 10);
        for (int i = 0; i < M_array.length; i++) {
            M_array[i] = Math.floor(M_array[i]);
        }

        List<Double> M_uniques = MatlabFunctions.unique(M_array);
        M_uniques = Matrix_Calc.removeSmallerThan(M_uniques, 1.0);

        double n = (double) M_uniques.size();

        double cut_min = Math.ceil(n / 10);
        double cut_max = Math.floor(6 * n / 10);

        double[] A = new double[(int) n];

        for (int i = 0; i < n; i++) {
            double m = M_uniques.get(i);
            double k = Math.floor(N / m);
            double[][] matrix_sequence = MatlabFunctions.reshape(sequenceAsArray, (int) m, (int) k);
            A[i] = MatlabFunctions.sum(Matrix_Calc.elementPower(MatlabFunctions.abs(MatlabFunctions.mean(matrix_sequence)), moment)) / k;
        }
        //Fit and calculate H
        double[] x = new double[M_uniques.size()];
        for (int i = 0; i < x.length; i++) {
            x[i] = Math.log10(M_uniques.get(i));
        }
        double[] y = MatlabFunctions.log10(A);

        // variable y1 only needed for plotting, therefore omitted here
        double[] X = new double[(int) (cut_max - cut_min) + 1];
        double[] Y = new double[(int) (cut_max - cut_min) + 1];
        int count = 0;
        for (int i = (int) cut_min - 1; i < (int) cut_max; i++) {
            X[count] = x[i];
            Y[count++] = y[i];
        }

        Polyfit polyfit;
        Polyval polyval_X = null;

        try {
            polyfit = new Polyfit(X, Y, 1);
            polyval_X = new Polyval(X, polyfit);
        } catch (Exception ex) {
            Logger.getLogger(Method_RS.class.getName()).log(Level.SEVERE, null, ex);
        }
        double[] YFit = polyval_X.getYout();

        double alpha = (YFit[YFit.length - 1] - YFit[0]) / (X[X.length - 1] - X[0]);
        double hurstFaktor = Math.round((1 + alpha / moment) * 10000d) / 10000d;
        return hurstFaktor;
    }

}
