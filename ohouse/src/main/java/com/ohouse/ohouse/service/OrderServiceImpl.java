package com.ohouse.ohouse.service;

import com.ohouse.ohouse.entity.Order;
import com.ohouse.ohouse.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
  OrderRepository orderRepository;

  @Override
  public Order createOrder(Order order) {
    return orderRepository.save(order);
  }
}
