package com.ohouse.ohouse.repository;

import com.ohouse.ohouse.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
