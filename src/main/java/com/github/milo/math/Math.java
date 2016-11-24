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
}
