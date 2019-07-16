package com.example.shopwise.home;

public class HomeItem extends HomeItemId{
    private String ShopName, DealDetail , ImageAddress, DealTitle;

    public HomeItem(){

    }

    public HomeItem(String shopName, String dealDetail, String imageAddress , String dealTitle) {
        ShopName = shopName;
        DealDetail = dealDetail;
        ImageAddress = imageAddress;
        DealTitle = dealTitle;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getDealDetail() {
        return DealDetail;
    }

    public void setDealDetail(String dealDetail) {
        DealDetail = dealDetail;
    }

    public String getImageAddress() {
        return ImageAddress;
    }

    public void setImageAddress(String imageAddress) {
        ImageAddress = imageAddress;
    }

    public String getDealTitle() {
        return DealTitle;
    }

    public void setDealTitle(String dealTitle) {
        DealTitle = dealTitle;
    }
}
