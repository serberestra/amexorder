package com.amex.order.model;

public enum GoodsEnum {

    APPLE(60.0),
    ORANGE(25.0);

    private Double value;

    GoodsEnum(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

}
