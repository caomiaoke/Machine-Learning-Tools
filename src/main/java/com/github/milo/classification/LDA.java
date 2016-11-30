package com.github.milo.classification;

import com.github.milo.math.Math;
import com.github.milo.math.matrix.EigenValueDecomposition;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by milo on 16-11-23.
 * Linear discriminant analysis
 */
public class LDA implements SoftClassifier<double[]>,Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 数据维度
     */
    private  final int p;

    /**
     * 种类数
     */
    private  final int k;

    /**
     *判别函数常数项
     */
    private final double[] ct;
    /**
     * 先验概率
     */
    private final double[] priori;
    /**
     * 每一类的均值向量
     */
    private final double[][] mu;

    /**
     *协方差矩阵的的特征向量
     */
    private final double[][] scaling;

    /**
     * 协方差矩阵的特征值
     */
    private final double[] eigen;

    /**
     * 分类器
     */
    public static class Trainer extends ClassifierTrainer<double[]>{
        private double [] priori;
        /**
         * 容差将用于判断协方差矩阵是不是一个奇异矩阵，分类器将忽略方差小于tol^2的值
         */
        private double tol = 1E-4;

        public  Trainer(){

        }

        public Trainer setPriori(double[] priori) {
            this.priori = priori;
            return this;
        }

        /**
         * 设置容差
         * @param tol
         * @return
         */
        public Trainer setTol(double tol) {

            if(tol < 0.0){
                throw  new IllegalArgumentException(" Invalid tol: " + tol);
            }
            this.tol = tol;
            return this;
        }

        @Override
        public LDA train(double[][] x, int[] y) {
            return new LDA(x,y,priori,tol);
        }
    }

    public LDA(double[][] x, int[] y){
        this(x,y,null);
    }

    public LDA(double[][] x, int[] y,double[] priori){
        this(x,y,priori,1E-4);
    }

    public LDA(double[][] x, int[] y, double tol){
        this(x,y,null,tol);
    }

    public LDA(double[][] x, int[] y, double[] priori, double tol){
        if(x.length != y.length){
            throw new IllegalArgumentException("Invalid number of priori probabilities: " + x.length);
        }

        if(priori != null) {
            if(priori.length < 2){
                throw new IllegalArgumentException("Invalid number of priori probabilities: " + priori.length);
            }

            double sum = 0.0;
            for (double pr : priori) {
                if (pr <= 0.0 | pr >= 1.0) {
                    throw new IllegalArgumentException("Invalid priori probability: " + pr);
                }
                sum += pr;
            }

            if (Math.abs(sum - 1.0) > 1E-10) {
                throw new IllegalArgumentException("The sum of priori probabilities is not one：　" + sum);
            }
        }

        int[] labels = Math.unique(y);
        Arrays.sort(labels);


        for(int i = 0; i < labels.length; i++ ){
            if (labels[i] < 0) {
                throw new IllegalArgumentException("Negative class label: " + labels[i]);
            }

            if (i > 0 && labels[i] - labels[i-1] > 1) {
                throw new IllegalArgumentException("Missing class: " + labels[i]+1);
            }
        }

        k = labels.length;
        if(k < 2){
            throw new IllegalArgumentException("only one class.");
        }
        if(priori != null && k != priori.length ){
            throw new IllegalArgumentException("The number of classes and the number of priori probabilities don't match");
        }

        if(tol < 0.0){
            throw new IllegalArgumentException("Invalid tol: " + tol);
        }

        final int n = x.length;

        if(n <= k){
            throw new IllegalArgumentException(String.format("Sample size is too small: %d <= %d",n,k));
        }
        /**
         * 特征数量
         */
        p = x[0].length;
        /**
         * 每一类的样本个数
         */
        int []  ni = new int[k];
        /**
         * 特征均值向量
         */
        double[] mean = Math.colMean(x);
        /**
         * 协方差矩阵
         */
        double[][] cov = new double[p][p];
        /**
         * 类均值向量
         */
        mu = new double[k][p];

        for (int i = 0; i < n; i++) {
            int c = y[i];
            ni[c]++;
            for (int j = 0; j < p; j++) {
                mu[c][j] += x[i][j];
            }
        }
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < p; j++) {
                mu[i][j] /= ni[i];
            }
        }

        if(priori == null){
            priori = new double[k];
            for (int i = 0; i < k; i++) {
                priori[i] = (double)ni[i]/n;
            }
        }

        this.priori = priori;
        ct = new double[k];
        for (int i = 0; i < k; i++) {
            ct[i] = Math.log(priori[i]);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < p; j++) {
                for (int l = 0; l <= j   ; l++) {
                    cov[j][l] += (x[i][j] - mean[j]) * (x[i][l] - mean[l]);
                }
            }
        }
        tol = tol*tol;

        for (int j = 0; j < p; j++) {
            for (int l = 0; l <=j; l++) {
                cov[j][l] /= (n-k);
                cov[l][j] = cov[j][l];
            }
            if(cov[j][j] < tol){
                throw new IllegalArgumentException(String.format("Covariance matrix(variable %d) is close to singular.",j));
            }
        }

        EigenValueDecomposition evd = EigenValueDecomposition.decompose(cov,true);

        for(double s : evd.getEigenValues()){
            if(s < tol){
                throw new IllegalArgumentException("The covariance matrix is close to singular.");
            }
        }
        eigen = evd.getEigenValues();
        scaling = evd.getEigenVectors();
    }

    public double[] getPrior(){
        return priori;
    }

    public int predict(double[] x) {
        return predict(x,null);
    }

    public int predict(double[] x, double[] posteriori) {
        if (x.length != p) {
            throw new IllegalArgumentException(String.format("Invalid input vector size: %d, expected: %d", x.length, p));
        }

        if (posteriori != null && posteriori.length != k) {
            throw new IllegalArgumentException(String.format("Invalid posteriori vector size: %d, expected: %d", posteriori.length, k));
        }

        int y = 0;

        double max = Double.NEGATIVE_INFINITY;

        double[] d = new double[p];
        double[] ux = new double[p];

        for(int i = 0; i < k; i++) {
            for (int j = 0; j < p; j++) {
                d[j] = x[i] - mu[i][j];
            }

            Math.atx(scaling,d,ux);

            double f = 0.0;

            for (int j = 0; j < p; j++) {
                f += ux[j]*ux[j] /eigen[i];
            }

            f = ct[i] - 0.5 *f;

            if(max < f){
                max = f;
                y = i;
            }

        }

        if (posteriori != null) {
            double sum = 0.0;
            for (int i = 0; i < k; i++) {
                posteriori[i] = Math.exp(posteriori[i] - max);
                sum += posteriori[i];

            }


            for (int i = 0; i < k; i++) {
                posteriori[i] /= sum;
            }
        }

        return y;



    }
}

