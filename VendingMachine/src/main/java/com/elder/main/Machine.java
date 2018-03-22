package com.elder.main;

import com.elder.Entity.Coin;
import com.elder.Entity.Product;
import com.elder.Entity.Vending;
import com.elder.access.CoinOperation;
import com.elder.access.ProductOperation;
import com.elder.service.CoinService;
import com.elder.service.ProductService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Machine {

    private InputStreamReader reader = new InputStreamReader(System.in);
    private BufferedReader input = new BufferedReader(reader);

    private static ProductOperation productOperation = new ProductService();
    private static CoinOperation coinOperation = new CoinService();

    public Machine () {
        displayChoice();
        try {
            String choice;
            do {
                choice = input.readLine();
                switch (Integer.parseInt(choice)) {
                    case 1:
                        createNewVendingMachine();
                        break;
                    case 2:
                        addNewProducts();
                        break;
                    case 3:
                        addNewCoins();
                        break;
                    case 4:
                        makePurchase();
                        coinOperation.displayAllCoins();
                        productOperation.displayAllProducts();
                        break;
                    default:
                        System.exit(-1);
                        break;
                }
                displayChoice();
            } while (choice != null);
        } catch (IOException exception) {
            System.out.println("IO Exception");
        }
    }

    private void createNewVendingMachine() {
        try {
            System.out.print("Enter machine capacity or product slots to be allocated:");
            String machineCapacity = input.readLine();

            System.out.print("Enter type of coins machine should accept: ");
            String[] coinTypes = input.readLine().split(" ");

            Vending vending = new Vending(Integer.parseInt(machineCapacity), coinTypes);

            coinOperation.loadCoins(vending);
            productOperation.loadProducts(vending);

        } catch (IOException exception) {
            System.out.println("Exception while creating new vending machine");
        }
    }

    private void addNewProducts() {
        System.out.println("Enter details of product: NAME PRICE COUNT");
        String[] details;
        boolean result = false;
        try {
            String line = input.readLine();
            if (line != null && !line.trim().equalsIgnoreCase(" ")) {
                details = line.trim().split(" ");
                double price = Double.parseDouble(details[1]);
                int count = Integer.parseInt(details[2]);
                if (!Double.isNaN(price)) {
                    result = productOperation.addNewProduct(new Product(details[0], price, count));
                }
            }
        } catch (Exception exception) {
            System.out.println("Exception occurred while adding product, please try again ...");
            addNewProducts();
        }
        if (!result) {
            System.out.println("Product details are not entered correctly");
        }
    }

    private void addNewCoins() {
        System.out.println("Enter details of coin: TYPE VALUE COUNT");
        String[] details;
        try {
            String line = input.readLine();
            if (line != null && !line.trim().equalsIgnoreCase(" ")) {
                details = line.trim().split(" ");
                double value = Double.parseDouble(details[1]);
                int count = Integer.parseInt(details[2]);
                if (!Double.isNaN(value)) {
                    coinOperation.addNewCoins(new Coin(details[0], value, count));
                }
            }
        } catch (Exception exception) {
            System.out.println("Exception occurred while adding coins, please try again ...");
            addNewCoins();
        }
    }

    private void makePurchase() {
        System.out.println("Enter details of purchase: PRODUCT_NAME QUANTITY");
        String[] details;
        try {
            String line = input.readLine();
            details = line.split(" ");
            String[] change = {};
            if (productOperation.searchProduct(details[0], Integer.parseInt(details[1]))) {
                System.out.println("Enter cash for purchase: TYPE OF EACH COIN ENTERED");

                String[] coinsEntered = input.readLine().split(" ");
                double amount = addEnteredCoins(coinsEntered);
                if (amount < coinOperation.getTotalCoin()) {
                    double price = productOperation.purchaseProduct(details[0], Integer.parseInt(details[1]));
                    change = coinOperation.amendCoinStock(price, coinsEntered, amount);
                } else {
                    System.out.println("Machine is short of coins for purchase");
                }
            } else {
                System.out.println("This product is not available for purchase");
                makePurchase();
            }
            System.out.println("Please take change");
            for (String changeCoins: change) {
                System.out.println(changeCoins);
            }
        } catch (IOException exception) {
            System.out.println("Exception while making purchase");
            makePurchase();
        }
    }

    private double addEnteredCoins(String[] coins) {
        double total = 0.0;
        for (String coin: coins) {
            total = total + Double.parseDouble(coin);
        }
        return total;
    }

    private void displayChoice() {
        System.out.println("\nPlease make your choice:\n" +
                "1. Load / Create vending machine\n" +
                "2. Add new products\n" +
                "3. Add coins into stock\n" +
                "4. Make a purchase\n" +
                "0. Exit");
    }

    public static void main (String[] arg) {
        new Machine();
    }
}
