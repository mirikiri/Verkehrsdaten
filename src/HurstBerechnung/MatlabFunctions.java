/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HurstBerechnung;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Admin
 */
public class MatlabFunctions {

    //from: http://www.java2s.com/Code/Java/2D-Graphics-GUI/Generatesnlogarithmicallyspacedpointsbetweend1andd2usingtheprovidedbase.htm
    public static strictfp double[] linspace(double d1, double d2, int n) {

        double[] y = new double[n];
        double dy = (d2 - d1) / (n - 1);
        for (int i = 0; i < n; i++) {
            y[i] = d1 + (dy * i);
        }

        return y;

    }

    //from: http://www.java2s.com/Code/Java/2D-Graphics-GUI/Generatesnlogarithmicallyspacedpointsbetweend1andd2usingtheprovidedbase.htm
    public static strictfp double[] logspace(double d1, double d2, int n, double base) {
        double[] y = new double[n];
        double[] p = linspace(d1, d2, n);
        for (int i = 0; i < y.length - 1; i++) {
            y[i] = Math.pow(base, p[i]);
        }
        y[y.length - 1] = Math.pow(base, d2);
        return y;
    }

    public static double[] abs(double[] input) {
        for (int i = 0; i < input.length; i++) {
            input[i] = Math.abs(input[i]);
        }
        return input;
    }

    public static double[][] abs(double[][] input) {
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[j].length; j++) {
                input[i][j] = Math.abs(input[i][j]);
            }
        }
        return input;
    }
    
    public static double complex_abs(double re, double im) {
        return Math.sqrt(re*re + im*im);
    }
    
    public static double[] fft_abs(double[] input) {
        double[] result = new double[input.length/2 +1];
        
        result[0] = complex_abs(input[0], 0);
        if (input.length%2 == 0) {
            result[result.length-1] = complex_abs(input[1], 0);
            int count = 2;
            for (int i = 2; i < input.length; i+=2) {
                result[i/2] = complex_abs(input[i], input[i+1]);
            }
        } else if (input.length%2 == 1) {
            result[result.length-1] = complex_abs(input[1], input[input.length-1]);
            for (int i = 2; i < input.length-1; i+=2) {
                result[i/2] = complex_abs(input[i], input[i+1]);
            }
        }
        return result;
    }

    public static List unique(double[] input) {
        ArrayList<Double> M = new ArrayList<>();
        for (Double d : input) {
            M.add(d);
        }
        //sort D (since MATALB unique() also sorts array) and create D_unique = D without duplicates
        Collections.sort(M);
        return M.stream().distinct().collect(Collectors.toList());
    }

    //with own modification, from: https://stackoverflow.com/questions/2877310/is-the-matlab-function-of-reshape-available-in-any-java-library
    public static double[][] reshape(double[][] inputVektor, int m, int n) {
        double[][] A;
        if (inputVektor.length == 1) {
            A = new double[1][m * n];
            for (int i = 0; i < A[0].length; i++) {
                A[0][i] = inputVektor[0][i];
            }
        } else {
            A = inputVektor;
        }

        int origM = A.length;
        int origN = A[0].length;
        if (origM * origN != m * n) {
            throw new IllegalArgumentException("New matrix must be of same area as matix A");
        }
        double[][] B = new double[m][n];
        double[] A1D = new double[A.length * A[0].length];

        int index = 0;
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                A1D[index++] = A[i][j];
            }
        }

        index = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                B[j][i] = A1D[index++];
            }

        }
        return B;
    }
  
    //cumulative sum columnwide. See matlab wiki cumsum, author: FM
    public static double[][] cumsum(double[][] input) {
        double[][] result = new double[input.length][input[0].length];
        if (input.length == 1) {
            result[0][0] = input[0][0];
            for (int i = 1; i < input[0].length; i++) {
                result[0][i] = input[0][i] + result[0][i-1];
            }
        } else {
            for (int i = 0; i < input[0].length; i++) {
                result[0][i] = input[0][i];
                for (int j = 1; j < input.length; j++) {
                    result[j][i] = input[j][i] + result[j-1][i];
                }
            }
        }
        return result;
    }

    public static double[] mean(double[][] input) {
        double[] result = new double[input[0].length];

        for (int i = 0; i < input[0].length; i++) {
            double sum = 0.0;
            for (int j = 0; j < input.length; j++) {
                sum += input[j][i];
            }
            result[i] = sum / input.length;
        }
        return result;
    }

    public static double mean(double[] input) {
        double sum = 0.0;

        for (int i = 0; i < input.length; i++) {
            sum += input[i];
        }
        return sum / input.length;
    }

    public static double[] sum(double[][] input) {
        double[] result = new double[input[0].length];

        for (int i = 0; i < input[0].length; i++) {
            double sum = 0.0;
            for (int j = 0; j < input.length; j++) {
                sum += input[j][i];
            }
            result[i] = sum;
        }
        return result;
    }

    public static double sum(double[] input) {
        double sum = 0.0;

        for (int i = 0; i < input.length; i++) {
            sum += input[i];
        }
        return sum;
    }

    //matlab repmat, only callable and needed with array, meaning 1x"anything" matrix, author: FM
    public static double[][] repmat(double[] input, int rowMul, int colMul) {
        double[][] result = new double[rowMul][input.length * colMul];

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                result[i][j] = input[j % input.length];
            }
        }
        return result;
    }

    public static double[] max(double[][] input) {
        double[] result = new double[input[0].length];

        for (int i = 0; i < input[0].length; i++) {
            double max = Double.NEGATIVE_INFINITY;
            for (int j = 0; j < input.length; j++) {
                if (input[j][i] > max) {
                    max = input[j][i];
                }
            }
            result[i] = max;
        }
        return result;
    }

    public static double max(double[] input) {
        double max = Double.NEGATIVE_INFINITY;

        for (int i = 0; i < input.length; i++) {
            if (input[i] > max) {
                max = input[i];
            }
        }
        return max;
    }

    public static double[] min(double[][] input) {
        double[] result = new double[input[0].length];

        for (int i = 0; i < input[0].length; i++) {
            double min = Double.POSITIVE_INFINITY;
            for (int j = 0; j < input.length; j++) {
                if (input[j][i] < min) {
                    min = input[j][i];
                }
            }
            result[i] = min;
        }
        return result;
    }

    public static double min(double[] input) {
        double min = Double.POSITIVE_INFINITY;

        for (int i = 0; i < input.length; i++) {
            if (input[i] < min) {
                min = input[i];
            }
        }
        return min;
    }

    //from: https://www.programiz.com/java-programming/examples/standard-deviation
    public static double calculateVar(double[] input) {
        double sum = 0.0, standardDeviation = 0.0;
        int length = input.length;

        for (double num : input) {
            sum += num;
        }

        double mean = sum / length;

        for (double num : input) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return standardDeviation / (length - 1);
    }

    public static double[] std(double[][] input) {
        double[] result = new double[input[0].length];

        for (int i = 0; i < input[0].length; i++) {
            double[] tmp = new double[input.length];
            for (int j = 0; j < input.length; j++) {
                tmp[j] = input[j][i];
            }
            result[i] = Math.sqrt(calculateVar(tmp));
        }
        return result;
    }

    public static double[] var(double[][] input) {
        double[] result = new double[input[0].length];

        for (int i = 0; i < input[0].length; i++) {
            double[] tmp = new double[input.length];
            for (int j = 0; j < input.length; j++) {
                tmp[j] = input[j][i];
            }
            result[i] = calculateVar(tmp);
        }
        return result;
    }

    public static double var(double[] input) {
        double result = calculateVar(input);
        return result;
    }

    public static double[][] diff(double[][] input) {
        double[][] result = new double[input.length - 1][input[0].length];

        for (int i = 0; i < input[0].length; i++) {
            for (int j = 1; j < input.length; j++) {
                result[j - 1][i] = input[j][i] - input[j - 1][i];
            }
        }
        return result;
    }

    public static double[] diff(double[] input) {
        double[] result = new double[input.length - 1];

        for (int i = 1; i < input.length; i++) {
            result[i - 1] = input[i] - input[i - 1];
        }
        return result;
    }

    public static double[] log10(double[] input) {
        for (int i = 0; i < input.length; i++) {
            input[i] = Math.log10(input[i]);
        }
        return input;
    }

}
