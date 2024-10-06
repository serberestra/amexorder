package com.amex.order.model;

import com.amex.order.utils.OrderConstants;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class OrangeItem extends Item {

    @Override
    public Double price() {
        return 25.0;
    }

    @Override
    public Integer offer() {
        if (this.quantity() > 2) {
            return this.quantity() - Math.floorDiv(this.quantity(),3);
        }
        return this.quantity();
    }

    @Override
    public String name() {
        return OrderConstants.ORANGE;
    }

}
