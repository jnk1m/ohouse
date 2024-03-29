package com.ohouse.ohouse.service;

import com.ohouse.ohouse.domain.*;
import com.ohouse.ohouse.entity.Order;
import com.ohouse.ohouse.entity.OrderHistory;
import com.ohouse.ohouse.entity.OrderItem;
import com.ohouse.ohouse.entity.OrderItemOption;
import com.ohouse.ohouse.enums.OrderStatus;
import com.ohouse.ohouse.exception.MenuNotFoundException;
import com.ohouse.ohouse.exception.OrderProcessingException;
import com.ohouse.ohouse.repository.OrderHistoryRepository;
import com.ohouse.ohouse.repository.OrderItemOptionRepository;
import com.ohouse.ohouse.repository.OrderItemRepository;
import com.ohouse.ohouse.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
  private final OrderRepository orderRepository;
  private final OrderHistoryRepository orderHistoryRepository;
  private final OrderItemRepository orderItemRepository;
  private final OrderItemOptionRepository orderItemOptionRepository;
  private final CartService cartService;
  private final MenuService menuService;
  private final OptionService optionService;

  @Transactional
  @Override
  public Order placeOrder(Order newOrder, List<Integer> usersCartId) {
    Order savedOrder = createOrder(newOrder);
    cartService.deleteAllCartItem(usersCartId, newOrder.getUser().getUserId());
    return savedOrder;
  }

  @Override
  public List<OrderSummaryDTO> getOrders(int userId) {
    return orderRepository.findOrdersByUser(userId);
  }

  @Override
  public OrderDetailDTO getOrderWithOrderNumber(String orderNumber) {
    return orderRepository.findOrderDetailDTOByOrderNumber(orderNumber).orElseThrow(() -> new IllegalArgumentException("Order not found with orderNumber" + orderNumber));
  }

  @Override
  public List<OrderedItemDTO> getOrderItemList(int orderId) {
    List<OrderedItemDTO> orderItemList = orderRepository.findOrderItemByOrderId(orderId);
    orderItemList.forEach(orderedItemDTO -> {
      List<OrderedItemOptionDTO> optionsByOrderItemId = orderItemOptionRepository.findOptionByParentOrderItemId(orderedItemDTO.getOrderItemId());
      optionsByOrderItemId.stream()
              .filter(optionDTO -> optionDTO.getParentOrderItemId() == orderedItemDTO.getOrderItemId())
              .forEach(matchingOption -> orderedItemDTO.getOptions().add(matchingOption));
    });

    return orderItemList;
  }

  @Override
  public Page<OrderSummaryDTO> getAllOrders(Pageable pageable) {
    return orderRepository.findAllOrderSummaryDTO(pageable);
  }

  @Override
  @Transactional
  public void updateOrderStatus(String orderNumber, OrderStatus orderStatus) {

    Order order = orderRepository.findByOrderNumber(orderNumber);
    order.updateOrderStatus(orderStatus);
  }

  @Override
  public int getOrderIdWithOrderNumber(String orderNumber) {
    return orderRepository.findOrderIdByOrderNumber(orderNumber);
  }

  @Transactional
  @Override
  public Order createOrder(Order order) {
    Order savedOrder = orderRepository.save(order);

    List<CartItemDTO> cartItemList = cartService.getCartItemList(order.getUser().getUserId());

    List<OrderItem> orderItems = cartItemList.stream()
            .map(cartItemDTO -> {
              try {
                return createOrderItem(savedOrder, cartItemDTO);
              } catch (MenuNotFoundException e) {
                throw new OrderProcessingException("Failed to process order due to menu not found", e);
              }
            })
            .collect(Collectors.toList());

    orderItemRepository.saveAll(orderItems);

    OrderHistory orderHistory = OrderHistory.builder()
            .orderStatus(savedOrder.getOrderStatus())
            .order(savedOrder)
            .user(savedOrder.getUser())
            .build();

    orderHistoryRepository.save(orderHistory);

    return savedOrder;
  }

  private OrderItem createOrderItem(Order savedOrder, CartItemDTO cartItemDTO) throws MenuNotFoundException {
    OrderItem orderItem = OrderItem.builder().order(savedOrder)
            .menu(menuService.getAvailableMenuById(cartItemDTO.getCartMenuDTO().getMenuId()))
            .quantity(cartItemDTO.getQuantity())
            .build();

    orderItem = orderItemRepository.save(orderItem);

    OrderItem finalOrderItem = orderItem;
    List<OrderItemOption> orderItemOptions = cartItemDTO.getOptions().stream()
            .map(cartOptionDTO -> createOrderItemOption(finalOrderItem, cartOptionDTO))
            .collect(Collectors.toList());

    orderItemOptionRepository.saveAll(orderItemOptions);

    return orderItem;
  }

  private OrderItemOption createOrderItemOption(OrderItem orderItem, CartOptionDTO cartOptionDTO) {
    return OrderItemOption.builder()
            .orderItem(orderItem)
            .option(optionService.getById(cartOptionDTO.getOptionId()))
            .build();
  }
}
