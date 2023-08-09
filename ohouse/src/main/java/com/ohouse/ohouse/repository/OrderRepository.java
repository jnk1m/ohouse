package com.ohouse.ohouse.repository;

import com.ohouse.ohouse.domain.OrderDetailDTO;
import com.ohouse.ohouse.domain.OrderSummaryDTO;
import com.ohouse.ohouse.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
  @Query("SELECT new com.ohouse.ohouse.domain.OrderSummaryDTO(" +
          "order.orderNumber, order.orderDate, order.orderTimezone, order.orderStatus, order.price) " +
          "FROM Order order " +
          "WHERE order.user.userId = :userId")
  List<OrderSummaryDTO> findOrdersByUser(int userId);

  @Query("SELECT new com.ohouse.ohouse.domain.OrderDetailDTO("+
  "order.orderId, order.price, order.paymentMethod, order.deliveryAddress, order.deliveryContact, order.specialInstruction, order.orderStatus, order.name, order.orderDate)"+
          "FROM Order order " +
          "WHERE order.orderNumber = :orderNumber")
  Optional<OrderDetailDTO> findOrderDetailDTOByOrderNumber(String orderNumber);
}
