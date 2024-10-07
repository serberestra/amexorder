package com.amex.order.mapper;

import com.amex.order.entity.ItemEntity;
import com.amex.order.model.Item;
import org.springframework.stereotype.Component;

@Component
// TODO: Use the Mapper library if possible
public class ItemEntityMapper {

    public ItemEntity toItemEntity(Item item) {
        var itemEntity = new ItemEntity();
        itemEntity.setItemName(item.name());
        itemEntity.setItemPrice(item.price());
        itemEntity.setQuantity(item.quantity());
        itemEntity.setTotal(item.total());

        return itemEntity;
    }

}
