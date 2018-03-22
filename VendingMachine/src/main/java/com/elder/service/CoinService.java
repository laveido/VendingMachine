package com.elder.service;

import com.elder.Entity.Coin;
import com.elder.Entity.Vending;
import com.elder.access.CoinOperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CoinService implements CoinOperation {

    private Vending machine;

    public void loadCoins (Vending vending) {

        setMachine(vending);

        Map<Double, Coin> coinMap = new HashMap<Double, Coin>();
        String[] coins = vending.getCoinType();
        for (String coin: coins) {
            coinMap.put(Double.parseDouble(coin), new Coin(coin, Double.parseDouble(coin), 100));
        }
        this.machine.setCoinMap(coinMap);
        displayAllCoins();
    }

    public void addNewCoins (Coin coin) {
        boolean flag = false;
        for (Map.Entry<Double, Coin> entity: this.machine.getCoinMap().entrySet()) {
            if (coin.getValue() == entity.getValue().getValue()) {
                addCoin(coin.getValue(), coin.getCount());
                flag = true;
            }
        }
        if (!flag) {
            this.machine.getCoinMap().put(coin.getValue(), coin);
        }
        displayAllCoins();
    }

    public String[] amendCoinStock (double price, String[] coins, double amount) {
        this.machine.setTotalAmount(this.machine.getTotalAmount() - amount);
        for (String coin: coins) {
            addCoin(Double.parseDouble(coin), 1);
        }
        return changeLeft(price, amount);
    }

    public void displayAllCoins() {
        System.out.println("\nCoins loaded to machine:");

        double total = 0.0;
        for (Map.Entry<Double, Coin> entity: this.machine.getCoinMap().entrySet()) {
            System.out.println(entity.getKey() + " - " +
                    "" + entity.getValue().getType() + " / " +
                    "" + entity.getValue().getValue() + " / " +
                    "" + entity.getValue().getCount() + " coins");
            total = total + (entity.getKey() * entity.getValue().getCount());
        }
        this.machine.setTotalAmount(total);
        System.out.println("Total amount in machine : " + this.machine.getTotalAmount());
    }

    private void addCoin(double coinType, int num) {
        for (Map.Entry<Double, Coin> entity: this.machine.getCoinMap().entrySet() ){
            if (coinType == entity.getKey()) {
                this.machine.getCoinMap().put(coinType,
                        new Coin(entity.getValue().getType(), entity.getValue().getValue(),
                                entity.getValue().getCount() + num));
            }
        }
    }

    private String[] changeLeft(double price, double amount) {
        int intChange = (int) ((amount*10) - (price*10));
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<Double, Coin> entity: this.machine.getCoinMap().entrySet()) {
            if (intChange != 0) {
                int val = (int) (entity.getValue().getValue() * 10);
                if (intChange == val) {
                    list.add(Integer.toString(val/10));
                } else if (intChange > val) {
                    int count = intChange / val;
                    for (int i = 0; i < count; i++) {
                        list.add(entity.getValue().getType());
                        this.machine.getCoinMap().put(entity.getKey(),
                                new Coin(entity.getValue().getType(), entity.getValue().getValue(),
                                        entity.getValue().getCount() - 1));
                    }
                    intChange = intChange - (val * count);
                }
            }
        }

        String[] changes = new String[list.size()];
        return list.toArray(changes);
    }

    public double getTotalCoin() {
        return this.machine.getTotalAmount();
    }

    private void setMachine(Vending machine) {
        this.machine = machine;
    }
}
