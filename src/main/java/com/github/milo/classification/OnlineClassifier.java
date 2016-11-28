package com.github.milo.classification;

/**
 * Created by Miaoke on 2016/11/28.
 */
public interface OnlineClassifier<T> extends Classifier<T> {
    /**
     * 实时的学习新的样本
     * @param x
     * @param y
     */

    public void learn(T x, int y);
}
