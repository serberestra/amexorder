package com.amex.order.repository;

import com.amex.order.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    Set<ItemEntity> findByOrderId(Long orderId);

}
