package com.example.shopwise;

public class ShopList extends ShopListID {
    private String shopName , shopLotNumber , imageUrl;

    public ShopList() {
    }

    public ShopList(String shopName, String shopLotNumber, String imageUrl) {
        this.shopName = shopName;
        this.shopLotNumber = shopLotNumber;
        this.imageUrl = imageUrl;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopLotNumber() {
        return shopLotNumber;
    }

    public void setShopLotNumber(String shopLotNumber) {
        this.shopLotNumber = shopLotNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
