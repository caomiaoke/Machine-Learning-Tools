package com.github.milo.classification;

/**
 * Created by milo on 16-11-23.
 * 分类器接口
 */
public interface Classifier<T> {
    /**
     *
     * @param x
     * @return
     */
    public int predict(T x);
}
