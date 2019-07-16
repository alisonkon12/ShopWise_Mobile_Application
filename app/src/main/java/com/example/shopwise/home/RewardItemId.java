package com.example.shopwise.home;

import io.reactivex.annotations.NonNull;

public class RewardItemId {
    public String rewardItemId;

    public <T extends RewardItemId> T withId(@NonNull final String id) {
        this.rewardItemId = id;
        return (T) this;
    }
}
