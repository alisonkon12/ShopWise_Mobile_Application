package com.example.shopwise.home;

import io.reactivex.annotations.NonNull;

public class HomeItemId {
    public String homeitemId;

    public <T extends HomeItemId> T withId(@NonNull final String id) {
        this.homeitemId = id;
        return (T) this;
    }
}