package com.github.milo.data;

import java.io.Serializable;
import java.text.ParseException;

/**
 * Created by milo on 16-11-24.
 */
public abstract class Attribute implements Serializable{
    /**
     * 属性的类型
     */
    public  enum Type{
        NUMERIC,
        NOMIMAL,
        STRING,
        DATE
    }

    private Type type;

    private double weight;

    private String name;

    private String description;

    /**
     * Constractor
     * @param type
     * @param weight
     * @param name
     * @param description
     */

    public Attribute(Type type, String name, String description,double weight) {
        this.type = type;
        this.weight = weight;
        this.name = name;
        this.description = description;
    }

    public Attribute(Type type, String name) {
        this(type, name,1.0);
    }

    public Attribute(Type type, String name, double weight) {
        this(type,name,null,weight);
    }

    public Attribute(Type type, String name, String description) {
        this(type, name, description,1.0);
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getWeight() {
        return weight;
    }

    public Attribute setName(String name) {
        this.name = name;
        return this;
    }

    public Attribute setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    public Attribute setDescription(String description) {
        this.description = description;
        return this;
    }

    public abstract String toString(double x);

    public  abstract double valueOf(String s)throws ParseException;

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o instanceof Attribute){
            Attribute a = (Attribute) o;
            if(name.equals(a.name) && type == a.type){
                if(description != null && a.description != null && description.equals(a.description)){
                    return true;
                }else{
                    return  false;
                }
            }else {
                return false;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + type.hashCode();
        hash = 37 * hash + (name!=null ? name.hashCode() : 0);
        hash = 37 * hash + (description != null ? description.hashCode(): 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(type).append('[').append(name).append(']');
        return  sb.toString();
    }
}
