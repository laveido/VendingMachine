package com.elder.Entity;

public class Coin {

    private String type;
    private double value;
    private int count;

    public Coin(String type, double value, int count) {
        this.type = type;
        this.value = value;
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
