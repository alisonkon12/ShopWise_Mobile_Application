package com.example.shopwise.promotion;

import io.reactivex.annotations.NonNull;

public class PromotionItemId {
    public String promotionItemId;

    public <T extends PromotionItemId> T withId(@NonNull final String id) {
        this.promotionItemId = id;
        return (T) this;
    }
}