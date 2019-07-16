package com.example.shopwise.model;

import java.util.Date;

public class ShoppingListModel {

    private  String createdBy, shoppingListID,shoppingListName , imageUrl , shoppingListDescription , shopName;
    private Date createdDate;
    public ShoppingListModel() {
    }

    public ShoppingListModel(String createdBy, String shoppingListID, String shoppingListName, String imageUrl, Date createdDate , String shoppingListDescription , String shopName) {
        this.createdBy = createdBy;
        this.shoppingListID = shoppingListID;
        this.shoppingListName = shoppingListName;
        this.imageUrl = imageUrl;
        this.createdDate = createdDate;
        this.shoppingListDescription=shoppingListDescription;
        this.shopName=shopName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getShoppingListID() {
        return shoppingListID;
    }

    public void setShoppingListID(String shoppingListID) {
        this.shoppingListID = shoppingListID;
    }

    public String getShoppingListName() {
        return shoppingListName;
    }

    public void setShoppingListName(String shoppingListName) {
        this.shoppingListName = shoppingListName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getShoppingListDescription() {
        return shoppingListDescription;
    }

    public void setShoppingListDescription(String shoppingListDescription) {
        this.shoppingListDescription = shoppingListDescription;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
