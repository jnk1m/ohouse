package com.ohouse.ohouse.repository;

import com.ohouse.ohouse.domain.OrderListDTO;
import com.ohouse.ohouse.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
  @Query("SELECT new com.ohouse.ohouse.domain.OrderListDTO(" +
          "order.orderNumber, order.orderDate, order.orderTimezone, order.orderStatus, order.price) " +
          "FROM Order order " +
          "WHERE order.user.userId = :userId")
  List<OrderListDTO> findOrdersByUser(int userId);
}
