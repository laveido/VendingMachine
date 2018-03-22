package com.elder.service;

import com.elder.Entity.Product;
import com.elder.Entity.Vending;
import com.elder.access.ProductOperation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ProductService implements ProductOperation{

    private Vending machine;

    public void loadProducts(Vending vending) {

        Map<String, Product> productMap = new HashMap<String, Product>();
        setMachine(vending);

        productMap.put("KitKat", new Product("KitKat", 0.80, 10));
        productMap.put("Sneaker", new Product("Sneaker", 0.60, 10));
        productMap.put("Bounty", new Product("Bounty", 0.50, 10));
        productMap.put("Cashew", new Product("Cashew", 0.40, 10));
        productMap.put("Walkers", new Product("Walkers", 0.70, 10));

        this.machine.setCapacity(vending.getCapacity());
        this.machine.setProductMap(productMap);

        displayAllProducts();
    }

    public boolean addNewProduct (Product product) {

        boolean flag = false;
        if (this.machine.getProductMap().size() < this.machine.getCapacity()) {
            for (Map.Entry<String, Product> entity: this.machine.getProductMap().entrySet()) {
                if (entity.getValue().getName().equalsIgnoreCase(product.getName())) {
                    this.machine.getProductMap().remove(product.getName());
                    this.machine.getProductMap().put(product.getName(),
                            new Product(product.getName(), product.getPrice(),
                                    entity.getValue().getCount() - product.getCount()));
                }
            }
            this.machine.getProductMap().put(product.getName(), product);
            flag = true;
        } else {
            System.out.println("Already have maximum, cannot enter a new product");
        }
        displayAllProducts();
        return flag;
    }

    public boolean searchProduct(String name, int count) {

        boolean available = false;
        for (Map.Entry<String, Product> entity: this.machine.getProductMap().entrySet()) {
            if (name.equalsIgnoreCase(entity.getKey()) && count < entity.getValue().getCount()) {
                available = true;
            }
        }
        return available;
    }

    public double purchaseProduct(String productName, int productCount) {
        return removeProductFromMachine(productName, productCount);
    }

    public void displayAllProducts() {
        System.out.println("\nProducts loaded in machine are:");
        for (Map.Entry<String, Product> entity: this.machine.getProductMap().entrySet()) {
            System.out.println(entity.getKey() + " - " +
                    "" + entity.getValue().getName() +" / " +
                    "" + entity.getValue().getCount() +" pieces / " +
                    "" + entity.getValue().getPrice() +" GBP");
        }
    }

    public double removeProductFromMachine(String name, int count) {
        double price = 0.0;
        for (Map.Entry<String, Product> entity: this.machine.getProductMap().entrySet() ){
            if (name.equalsIgnoreCase(entity.getKey())) {
                price = entity.getValue().getPrice();
                this.machine.getProductMap().put(name, new Product(entity.getValue().getName(),
                        entity.getValue().getPrice(), entity.getValue().getCount() - count));
            }
        }
        return price * count;
    }

    private void setMachine(Vending machine) {
        this.machine = machine;
    }
}
