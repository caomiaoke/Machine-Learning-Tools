package com.github.milo.math;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by milo on 16-11-23.
 */
public class Math {


    /**
     * private constructor
     */

    private Math(){}

    public static double abs(double a ){
        return java.lang.Math.abs(a);
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
}
