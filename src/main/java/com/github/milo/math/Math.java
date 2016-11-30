package com.github.milo.math;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by milo on 16-11-23.
 */
public class Math {
    private static final Logger logger = LoggerFactory.getLogger(Math.class);

    /**
     * 自然对数的底E
     */
    public static final  double E = java.lang.Math.E;
    /**
     * PI
     */
    public  static final double PI = java.lang.Math.PI;
    /**
     * 机器的精度
     */
    public static double EPSILON = Math.pow(2.0 , -52.0);
    /**
     * 指数基数
     */
    public  static  int RADIX = 2;
    /**
     * 数字位数
     */
    public  static int DIGITS = 53;




    /**
     * private constructor
     */

    private Math(){}

    public static double abs(double a ){
        return java.lang.Math.abs(a);
    }

    /**
     * 自然对数
     * @param a
     * @return
     */
    public static double log(double a){
        return  java.lang.Math.log(a);
    }

    /**
     * 底为10的对数
     * @param a
     * @return
     */
    public static double log10(double a){
        return java.lang.Math.log10(a);
    }

    /**
     * 返回自然对数ln(x+1)
     * @param a
     * @return
     */
    public static double log1p(double a){
        return java.lang.Math.log1p(a);
    }

    /**
     * 求一ｅ为底的指数
     * @param a
     * @return
     */
    public static double exp(double a) {
        return java.lang.Math.exp(a);
    }

    /**
     * minimum of Integer
     */
    public  static Integer min(Integer a, Integer ...arr){
        Integer minVal = a;
        for (Integer i :
                arr) {
            if (minVal > i) minVal = i;
            else continue;;
        }
        return minVal;

    }

    /**
     * a 的b次方
     * @param a
     * @param b
     * @return
     */
    public static double pow(double a, double b) {
        return java.lang.Math.pow(a, b);
    }

    /**
     * 返回矩阵每一列向量的均值
     */
    public static double[] colMean(double[][] data){
        double[] x = data[0].clone();

        for (int i = 1; i < data.length ; i++) {
            for (int j = 0; j < x.length; j++) {
                x[j] += data[i][j];
            }
        }
        scale(1.0/data.length,x);
        return x;
    }

    /**
     * 向量值唯一化
     */
    public static int[] unique(int[] x){
        HashSet<Integer> hash = new HashSet<Integer>();
        for (int i = 0; i < x.length; i++){
            hash.add(x[i]);
        }


        int[] y = new int[hash.size()];

        Iterator<Integer> iterator = hash.iterator();
        for (int i = 0; i < y.length;i++){
            y[i] = iterator.next();
        }
        return y;
    }

    /**
     * 向量每个元素都乘以一个数
     */
    public static void scale(double a, double[] x){
        for (int i = 0; i < x.length; i++) {
            x[i] *= a;
        }
    }

    /**
     *  做 y = A(T)*x
     * @param A
     * @param x
     * @param y
     */
    public static void atx(double[][] A, double[] x, double[] y){
        int n = min(A[0].length, y.length);
        int p = min(A.length, x.length);

        Arrays.fill(y,0.0);
        for (int i = 0; i < n; i++) {
            for (int k = 0; k < p  ; k++) {
                y[i] += x[k] * A[k][i];

            }
        }
    }
}
