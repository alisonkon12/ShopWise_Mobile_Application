package com.example.shopwise;

public class FavouriteList {

    private String ShopName , LotNumber ,CreatedDate;

    public FavouriteList(String shopName, String lotNumber,String createdDate) {
        ShopName = shopName;
        LotNumber = lotNumber;
        CreatedDate=createdDate;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getLotNumber() {
        return LotNumber;
    }

    public void setLotNumber(String lotNumber) {
        LotNumber = lotNumber;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }
}
