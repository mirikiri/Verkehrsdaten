/*
 *  Original Matlab-Code Author: Chu Chen
 *  Version 1.0,  03/10/2008
 *  chen-chu@163.com
 *  https://de.mathworks.com/matlabcentral/fileexchange/19148-hurst-parameter-estimate
 *
 *  code translation to Java: Florian Mederer
 */
package HurstBerechnung;

import Jama.Matrix;
import Polyfitting.Polyfit;
import Polyfitting.Polyval;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class Method_Residuals_Of_Regression implements BerechnungsMethodenInterface {

    private String name;
    
    public Method_Residuals_Of_Regression(String name) {
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

        //Calculate aggregate level
        double N = sequenceAsArray[0].length;
        double[][] FBM = MatlabFunctions.cumsum(sequenceAsArray);
        double mlarge = Math.floor(N / 5);
        double msmall;
        double value1 = Math.pow(Math.log10(N), 2);
        if (value1 > 10) {
            msmall = value1;
        } else {
            msmall = 10;
        }
        double[] M = MatlabFunctions.logspace(Math.log10(msmall), Math.log10(mlarge), 50, 10);
        for (int i = 0; i < M.length; i++) {
            M[i] = Math.floor(M[i]);
        }
        List<Double> M_uniques = MatlabFunctions.unique(M);

        double n = M_uniques.size();
        double cut_min = Math.ceil(n / 10);
        double cut_max = Math.floor(7 * n / 10);
        double[] Global_residuals = new double[(int) n];

        //Calculate residuals under different aggregate level
        for (int i = 0; i < n; i++) {
            double m = M_uniques.get(i);
            double k = Math.floor(N / m);
            double[][] matrix_FBM = MatlabFunctions.reshape(FBM, (int) m, (int) k);
            Matrix matrix_FBM_matrix = new Matrix(matrix_FBM);
            double[] Local_residuals = new double[(int) k];

            double[][] vv = new double[(int) m][2];
            for (int p = 0; p < vv.length; p++) {
                vv[p][0] = p + 1;
                vv[p][1] = 1;
            }

            for (int j = 0; j < k; j++) {
                Matrix y_matrix = matrix_FBM_matrix.getMatrix(0, matrix_FBM_matrix.getRowDimension()-1, j, j);
                Matrix vv_matrix = new Matrix(vv);
                Matrix p = vv_matrix.solve(y_matrix);
                double norm_xx = y_matrix.minus(vv_matrix.times(p)).norm2();
                Local_residuals[j] = Math.pow(norm_xx, 2) / m;
            }
            Global_residuals[i] = MatlabFunctions.mean(Local_residuals);
        }

        //Fit and calculate H
        double[] x = new double[M_uniques.size()];
        for (int i = 0; i < x.length; i++) {
            x[i] = Math.log10(M_uniques.get(i));
        }
        double[] y = new double[Global_residuals.length];
        for (int i = 0; i < y.length; i++) {
            y[i] = Math.log10(Global_residuals[i]);
        }

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

        double hurstFaktor = Math.round(((0.5 * (YFit[YFit.length - 1] - YFit[0]) / (X[X.length - 1] - X[0]))) * 10000d) / 10000d;
        return hurstFaktor;
    }

}
