package com.amex.order.model;

import lombok.Setter;

@Setter
public abstract class Item {

    private Integer quantity;

    public abstract Double price();
    public abstract Integer offer();
    public abstract String name();

    public Integer quantity() {
        return quantity;
    }

    public Double total() {
        return offer() * price();
    }

}
