package com.amex.order.model;

import com.amex.order.utils.OrderConstants;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class AppleItem extends Item {

    @Override
    public Double price() {
        return 60.0;
    }

    @Override
    public Integer offer() {
        if (this.quantity() > 1) {
            return Math.floorDiv(this.quantity(), 2);
        }
        return this.quantity();
    }

    @Override
    public String name(){
        return OrderConstants.APPLE;
    }

}
