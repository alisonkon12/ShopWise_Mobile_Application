package com.example.shopwise.model;

public class ProductModel {
    private String productID , productName;
    private Boolean izInShoppingList;
    public ProductModel() {
    }

    public ProductModel(String productID, String productName, Boolean izInShoppingList) {
        this.productID = productID;
        this.productName = productName;
        this.izInShoppingList = izInShoppingList;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Boolean getIzInShoppingList() {
        return izInShoppingList;
    }

    public void setIzInShoppingList(Boolean izInShoppingList) {
        this.izInShoppingList = izInShoppingList;
    }
}
