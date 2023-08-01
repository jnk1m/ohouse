package com.ohouse.ohouse.repository;

import com.ohouse.ohouse.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
