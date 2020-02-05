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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Method_RS implements BerechnungsMethodenInterface {

    private String name;
    
    public Method_RS(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double berechne(List<Integer> sequence) {
        if (sequence.size() < 60) {
            return -1.0;
        }
        double[][] sequenceAsArray = new double[1][sequence.size()];
        for (int i = 0; i < sequenceAsArray[0].length; i++) {
            sequenceAsArray[0][i] = sequence.get(i);
        }
        double N = sequence.size();
        double dlarge = Math.floor(N / 5);
        double dsmall;
        double value1 = Math.pow(Math.log10(N), 2);
        if (value1 > 10) {
            dsmall = value1;
        } else {
            dsmall = 10;
        }

        double[] D = MatlabFunctions.logspace(Math.log10(dsmall), Math.log10(dlarge), 50, 10);
        for (int i = 0; i < D.length; i++) {
            D[i] = Math.floor(D[i]);
        }

        List<Double> D_uniques = MatlabFunctions.unique(D);

        double n = (double) D_uniques.size();
        double[] x = new double[(int) n];
        double[] y = new double[(int) n];

        //looping
        for (int i = 0; i < n; i++) {
            double d = D_uniques.get(i);
            double m = Math.floor(N / d);

            //reshaping input sequence
            double[][] matrix_sequence = MatlabFunctions.reshape(sequenceAsArray, (int) d, (int) m);
            double[][] Z1 = MatlabFunctions.cumsum(matrix_sequence);
            double[][] Z2 = MatlabFunctions.cumsum(MatlabFunctions.repmat(MatlabFunctions.mean(matrix_sequence), (int) d, 1));
            
            //corresponds to line 40 in RS.m
            double[] tmp1 = Matrix_Calc.subtraction(MatlabFunctions.max(Matrix_Calc.subtraction(Z1, Z2)), MatlabFunctions.min(Matrix_Calc.subtraction(Z1, Z2)));
            for (int j = 0; j < tmp1.length; j++) {
                tmp1[j] = Math.round(tmp1[j] * 10000d) / 10000d;
            }

            //corresponds to line 41 in RS.m
            double[] tmp2 = MatlabFunctions.std(matrix_sequence);
            for (int j = 0; j < tmp2.length; j++) {
                tmp2[j] = Math.round(tmp2[j] * 10000d) / 10000d;
            }

            //line 43 in RS.m, if calculations doesn't include any 0, write into x und y Array
            if (Math.abs(MatlabFunctions.min(tmp1)) > 0.00001 && Math.abs(MatlabFunctions.min(tmp2)) > 0.00001) {
                x[i] = Math.log10(d);
                y[i] = MatlabFunctions.mean(MatlabFunctions.log10(Matrix_Calc.elementDivision(tmp1, tmp2)));
            }
        }
        int countNonZeroInX = 0;
        for (int i = 0; i < x.length; i++) {
            if (Math.abs(x[i]) > 0.0) {
                countNonZeroInX++;
            }
        }
                
        //if countNonZeroInX is smaller than 3, mathematics below won't work
        if (countNonZeroInX < 3) {
            return -2.0;
        }

        double[] x_index = new double[countNonZeroInX];
        double[] y_index = new double[countNonZeroInX];

        int count = 0;
        for (int i = 0; i < x.length; i++) {
            if (Math.abs(x[i]) > 0.0) {
                x_index[count++] = x[i];
            }
        }
        count = 0;
        for (int i = 0; i < y.length; i++) {
            if (Math.abs(y[i]) > 0.0) {
                y_index[count++] = y[i];
            }
        }

        double n2 = x_index.length;
        int cut_min = (int) Math.ceil((3 * n2) / 10);
        int cut_max = (int) Math.floor((9 * n2) / 10);
        double[] X = new double[(cut_max - cut_min) + 1];
        double[] Y = new double[(cut_max - cut_min) + 1];
        count = 0;
        for (int i = cut_min - 1; i < cut_max; i++) {
            X[count] = x_index[i];
            Y[count++] = y_index[i];
        }

        Polyfit polyfit;
        Polyval polyval = null;
        try {
            polyfit = new Polyfit(X, Y, 1);
            polyval = new Polyval(X, polyfit);

        } catch (Exception ex) {
            Logger.getLogger(Method_RS.class.getName()).log(Level.SEVERE, null, ex);
        }
        double[] YFit = polyval.getYout();

        double hurstFaktor = Math.round(((YFit[YFit.length - 1] - YFit[0]) / (X[X.length - 1] - X[0])) * 10000d) / 10000d;
        return hurstFaktor;
    }

}
