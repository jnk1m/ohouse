package com.ohouse.ohouse.service;

import com.ohouse.ohouse.domain.OrderListDTO;
import com.ohouse.ohouse.entity.Order;

import java.util.List;

public interface OrderService {

  Order createOrder(Order order);

  Order placeOrder(Order newOrder, List<Integer> usersCartId);

  List<OrderListDTO> getOrders(int userId);
}
