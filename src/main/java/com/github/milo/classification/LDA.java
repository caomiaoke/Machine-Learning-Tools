package com.github.milo.classification;

import com.github.milo.math.Math;

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

        p = x[0].length;

//        int [] = new int[k];
//        double[] mena = Math.colMean(x);



    }





}
