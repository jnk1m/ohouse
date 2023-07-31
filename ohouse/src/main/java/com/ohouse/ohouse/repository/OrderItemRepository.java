package com.ohouse.ohouse.repository;

import com.ohouse.ohouse.entity.OrderItemOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItemOption, Integer> {
}
