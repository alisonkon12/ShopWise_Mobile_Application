package com.example.shopwise.home;

public class RewardItem extends RewardItemId {

    private String RewardDetail, ShopName, ImageAddress;

    public RewardItem() {
    }

    public RewardItem(String rewardDetail, String shopName, String imageAddress) {
        RewardDetail = rewardDetail;
        ShopName = shopName;
        ImageAddress = imageAddress;
    }

    public String getRewardDetail() {
        return RewardDetail;
    }

    public void setRewardDetail(String rewardDetail) {
        RewardDetail = rewardDetail;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getImageAddress() {
        return ImageAddress;
    }

    public void setImageAddress(String imageAddress) {
        ImageAddress = imageAddress;
    }
}