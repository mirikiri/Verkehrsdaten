/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HurstBerechnung;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Admin
 */
public class Matrix_Calc {

    public static double[][] subtraction(double[][] A, double[][] B) {
        if (A.length != B.length || A[0].length != B[0].length) {
            throw new IllegalArgumentException("Matrix_Calc::subtraction(double[][] A, double[][] B) Matrix A and B must have same size");
        }
        double[][] result = new double[A.length][A[0].length];

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                result[i][j] = A[i][j] - B[i][j];
            }
        }
        return result;
    }
    
    public static double[] subtraction(double[] A, double[] B) {
        if (A.length != B.length) {
            throw new IllegalArgumentException("Matrix_Calc::subtraction(double[] A, double[] B) Matrix A and B must have same size");
        }
        double[] result = new double[A.length];

        for (int i = 0; i < A.length; i++) {
            result[i] = A[i] - B[i];
        }
        return result;
    }

    public static double[][] elementDivision(double[][] A, double[][] B) {
        if (A.length != B.length || A[0].length != B[0].length) {
            throw new IllegalArgumentException("Matrix_Calc::elementDivision(double[][] A, double[][] B) Matrix A and B must have same size");
        }
        double[][] result = new double[A.length][A[0].length];

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                result[i][j] = A[i][j] / B[i][j];
            }
        }
        return result;
    }

    public static double[] elementDivision(double[] A, double[] B) {
        if (A.length != B.length) {
            throw new IllegalArgumentException("Matrix_Calc::elementDivision(double[] A, double[] B) Matrix A and B must have same size");
        }
        double[] result = new double[A.length];

        for (int i = 0; i < A.length; i++) {
            result[i] = A[i] / B[i];
        }
        return result;
    }

    public static double[][] elementPower(double[][] A, double power) {
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                A[i][j] = Math.pow(A[i][j], power);
            }
        }
        return A;
    }

    public static double[] elementPower(double[] A, double power) {
        for (int i = 0; i < A.length; i++) {
            A[i] = Math.pow(A[i], power);
        }
        return A;
    }
    
    public static double[][] scalarDivision(double[][] A, double divisor) {
        if (divisor == 0.0) {
            throw new IllegalArgumentException("Matrix_Calc::scalarDivision(double[][] A, double divisor) Division through 0");
        }
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                A[i][j] = A[i][j] / divisor;
            }
        }
        return A;
    }

    public static double[] scalarDivision(double[] A, double divisor) {
        if (divisor == 0.0) {
            throw new IllegalArgumentException("Matrix_Calc::scalarDivision(double[] A, double divisor) Division through 0");
        }
        for (int i = 0; i < A.length; i++) {
            A[i] = A[i] / divisor;
        }
        return A;
    }
    
    public static List removeSmallerThan(List<Double> input, double limit) {
        // Remove elements smaller than 10 using 
        // Iterator.remove() 
        Iterator itr = input.iterator();
        while (itr.hasNext()) {
            double x = (Double) itr.next();
            if (x <= limit) {
                itr.remove();
            }
        }
        return input;
    }

}
