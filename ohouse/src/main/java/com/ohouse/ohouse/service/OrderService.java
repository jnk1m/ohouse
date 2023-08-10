package com.ohouse.ohouse.service;

import com.ohouse.ohouse.domain.OrderDetailDTO;
import com.ohouse.ohouse.domain.OrderSummaryDTO;
import com.ohouse.ohouse.domain.OrderedItemDTO;
import com.ohouse.ohouse.entity.Order;

import java.util.List;

public interface OrderService {

  Order createOrder(Order order);

  Order placeOrder(Order newOrder, List<Integer> usersCartId);

  List<OrderSummaryDTO> getOrders(int userId);

  OrderDetailDTO getOrderWithOrderNumber(String orderNumber);

  int getOrderIdWithOrderNumber(String orderNumber);

  List<OrderedItemDTO> getOrderItemList(int orderId);
}
