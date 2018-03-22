package com.elder.access;

import com.elder.Entity.Product;
import com.elder.Entity.Vending;

public interface ProductOperation {

    void loadProducts(Vending vending);

    boolean addNewProduct (Product product);

    double purchaseProduct(String productName, int productCount);

    boolean searchProduct(String name, int count);

    double removeProductFromMachine (String name, int count);

    void displayAllProducts();

}
