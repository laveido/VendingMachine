package com.elder.Entity;

import java.util.Map;

public class Vending {

    private int capacity;

    private String[] coinType;

    private double totalAmount = 0.0;

    private Map<String, Product> productMap;
    private Map<Double, Coin> coinMap;

    public Vending(int capacity, String[] coinType) {
        this.capacity = capacity;
        this.coinType = coinType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String[] getCoinType() {
        return coinType;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setCoinType(String[] coinType) {
        this.coinType = coinType;
    }

    public Map<String, Product> getProductMap() {
        return productMap;
    }

    public void setProductMap(Map<String, Product> productMap) {
        this.productMap = productMap;
    }

    public Map<Double, Coin> getCoinMap() {
        return coinMap;
    }

    public void setCoinMap(Map<Double, Coin> coinMap) {
        this.coinMap = coinMap;
    }
}
