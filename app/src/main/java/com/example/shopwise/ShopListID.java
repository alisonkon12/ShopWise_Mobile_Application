package com.example.shopwise;

import io.reactivex.annotations.NonNull;

public class ShopListID {
    public String shopListID;

    public <T extends ShopListID> T withId(@NonNull final String id) {
        this.shopListID = id;
        return (T) this;
    }
}