package com.ohouse.ohouse.repository;

import com.ohouse.ohouse.domain.OrderDetailDTO;
import com.ohouse.ohouse.domain.OrderSummaryDTO;
import com.ohouse.ohouse.domain.OrderedItemDTO;
import com.ohouse.ohouse.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  @Query("SELECT new com.ohouse.ohouse.domain.OrderDetailDTO(" +
          "order.orderId, order.price, order.paymentMethod, order.deliveryAddress, order.deliveryContact, order.specialInstruction, order.orderStatus, order.name, order.orderDate)" +
          "FROM Order order " +
          "WHERE order.orderNumber = :orderNumber")
  Optional<OrderDetailDTO> findOrderDetailDTOByOrderNumber(String orderNumber);

  @Query("SELECT new com.ohouse.ohouse.domain.OrderedItemDTO(" +
          "oi.orderItemId, oi.order.orderId, oi.quantity, " +
          "oi.menu.menuId, oi.menu.menuNameEng, oi.menu.menuPrice) " +
          "FROM OrderItem oi " +
          "WHERE oi.order.orderId = :orderId")
  List<OrderedItemDTO> findOrderItemByOrderId(int orderId);

  @Query("SELECT order.orderId " +
          "FROM Order order " +
          "WHERE order.orderNumber = :orderNumber")
  int findOrderIdByOrderNumber(String orderNumber);

  @Query(value = "SELECT new com.ohouse.ohouse.domain.OrderSummaryDTO(" +
          "o.orderNumber, o.orderDate, o.orderTimezone, o.orderStatus, o.price) " +
          "FROM Order o",
          countQuery = "SELECT count(o) FROM Order o")
  Page<OrderSummaryDTO> findAllOrderSummaryDTO(Pageable pageable);

  Order findByOrderNumber(String orderNumber);
}
