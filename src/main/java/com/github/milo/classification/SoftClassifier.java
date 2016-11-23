package com.github.milo.classification;

/**
 * Created by milo on 16-11-23.
 * Soft classifier 用于计算后验概率和判别
 */
public interface SoftClassifier <T> extends Classifier<T>{

    /**
     *
     * @param x  用于判别的实例
     * @param posteriori　用于输出后延概率的数组
     * @return　判别结果
     */
    public int predict(T x, double[] posteriori);
}
