package com.elder.access;

import com.elder.Entity.Coin;
import com.elder.Entity.Vending;

public interface CoinOperation {

    void loadCoins (Vending vending);

    void addNewCoins (Coin coin);

    String[] amendCoinStock (double price, String[] coins, double amount);

    double getTotalCoin();

    void displayAllCoins();
}
