package com.github.milo.math.matrix;

import com.github.milo.math.Math;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Miaoke on 2016/11/29.
 */
public class EigenValueDecomposition {
    private static final Logger logger = LoggerFactory.getLogger(EigenValueDecomposition.class);

    /**
     * 特征值（实部）
     */
    private double[] eigenValue_r;
    /**
     * 特征值 （虚部）
     */
    private double[] eigenValue_i;
    /**
     * 特征向量矩阵
     */
    private double[][] eigenVector;

    /**
     *
     * @param eigenVector
     * @param eigenValue_r
     */
    private EigenValueDecomposition(double[][] eigenVector, double[] eigenValue_r ){
        this.eigenVector = eigenVector;
        this.eigenValue_r = eigenValue_r;
    }

    private EigenValueDecomposition(double[][] eigenVector, double[] eigenValue_r,double[] eigenValue_i){
        this.eigenValue_r = eigenValue_r;
        this.eigenVector = eigenVector;
        this.eigenValue_i = eigenValue_i;
    }

    public double[][] getEigenVectors(){
        return eigenVector;
    }
    public double[] getEigenValues(){
        return eigenValue_r;
    }
    public double[] getRealEigenValues(){
        return eigenValue_r;
    }
    public double[] getImageEigenValues(){
        return eigenValue_r;
    }

    public static EigenValueDecomposition decompose(double[][] A){
        if(A.length != A[0].length){
            throw new IllegalArgumentException(java.lang.String.format("Matrix is not square: %d x %d", A.length, A[0].length));
        }

        int n = A.length;
        double tol = 100 * Math.EPSILON;
        boolean symetric = true;
        for (int i = 0; i < n && symetric; i++) {
            for (int j = 0; j < n && symetric; j++) {
                symetric = Math.abs(A[i][j] - A[j][i]) < tol;

            }

        }
        return decompose(A,symetric);
    }

    public static EigenValueDecomposition decompose(double[][] A, boolean symetric){
        return decompose(A,symetric,false);

    }

    public static EigenValueDecomposition decompose(double[][] A, boolean symetric,boolean onlyValue){
        if(A.length != A[0].length){
            throw new IllegalArgumentException(java.lang.String.format("Matrix is not square: %d x %d",A.length,A[0].length));
        }

        int n = A.length;
        double[] eigenValue_r = new double[n];
        double[] eigenValue_i = new double[n];

        if(symetric){
            double[][] eigrnVector = A;
            if (onlyValue){

            }
        }

    }

    private static void tred(double[][] V, double[] d, double[] e){
        int n = V.length;
        System.arraycopy(V[n-1],0,d,0,n);

        for (int i = n-1; i > 0 ; i--) {
            double scale = 0.0;
            double h = 0.0;
            for(int k = 0; k < i; k++){
                scale = scale + Math.abs(d[k]);
            }


        }

    }


}
