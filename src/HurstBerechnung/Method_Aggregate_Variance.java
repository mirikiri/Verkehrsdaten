/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HurstBerechnung;

import Polyfitting.Polyfit;
import Polyfitting.Polyval;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class Method_Aggregate_Variance implements BerechnungsMethodenInterface{

    private String name;
    
    public Method_Aggregate_Variance(String name) {
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
//        long startTime = System.nanoTime();
        double[][] sequenceAsArray = new double[1][sequence.size()];
        for (int i = 0; i < sequenceAsArray[0].length; i++) {
            sequenceAsArray[0][i] = sequence.get(i);
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
        
        double[] V = new double[(int) n];
        
        for (int i = 0; i < n; i++) {
            double m = M_uniques.get(i);
            double k = Math.floor(N / m);
            double[][] matrix_sequence = MatlabFunctions.reshape(sequenceAsArray, (int) m, (int) k);
            V[i] = MatlabFunctions.var(Matrix_Calc.scalarDivision(MatlabFunctions.sum(matrix_sequence), m));
        }
        
        //Fit and calculate H
        double[] x = new double[M_uniques.size()];
        for (int i = 0; i < x.length; i++) {
            x[i] = Math.log10(M_uniques.get(i));
        }
        double[] y = MatlabFunctions.log10(V);

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
        
        double beta = -(YFit[YFit.length - 1] - YFit[0]) / (X[X.length - 1] - X[0]);
        
        double hurstFaktor = Math.round((1.0 - beta/2) * 10000d) / 10000d;
//        Matrix_Calc.measureTime("end:", startTime);
        return hurstFaktor;
    }
    
}
