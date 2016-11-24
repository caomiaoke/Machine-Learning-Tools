package com.github.milo.classification;

import com.github.milo.data.Attribute;

/**
 * Created by milo on 16-11-24.
 * 分类器训练器
 */
public abstract  class ClassifierTrainer <T> {
    Attribute[] attributes;


    public ClassifierTrainer() {
    }

    public ClassifierTrainer(Attribute[] attributes) {
        this.attributes = attributes;
    }

    public void setAttributes(Attribute[] attributes) {
        this.attributes = attributes;
    }

    public abstract Classifier<T> train(T[] x,  int[] y);
}
