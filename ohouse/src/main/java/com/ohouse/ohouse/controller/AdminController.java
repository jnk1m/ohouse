package com.ohouse.ohouse.controller;

import com.ohouse.ohouse.domain.AdminMenuListDTO;
import com.ohouse.ohouse.domain.OrderSummaryDTO;
import com.ohouse.ohouse.domain.OrderSummaryDisplayDTO;
import com.ohouse.ohouse.enums.OrderStatus;
import com.ohouse.ohouse.service.MenuService;
import com.ohouse.ohouse.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
  private final OrderService orderService;
  private final MenuService menuService;

  @GetMapping
  public String getAdminPage() {
    return "admin/admin";
  }

  /*Order*/

  @GetMapping("/orders")
  public String getOrdersPage(Model model, Pageable pageable) {
    Page<OrderSummaryDTO> orders = orderService.getAllOrders(pageable);

    List<OrderSummaryDisplayDTO> formattedOrders = orders.stream()
            .map(order -> {
              //TODO: Modify to fetch the timezone ID from the client.
              ZonedDateTime customerDateTime = order.getOrderDate().withZoneSameInstant(ZoneId.of("Asia/Seoul"));
              String formattedDateTime = customerDateTime.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm"));
              return new OrderSummaryDisplayDTO(order.getOrderNumber(), formattedDateTime, order.getOrderStatus(), order.getOrderPrice());
            })
            .collect(Collectors.toList());

    Page<OrderSummaryDisplayDTO> formattedOrdersWithPage = new PageImpl<>(formattedOrders, PageRequest.of(0, formattedOrders.size()), formattedOrders.size());

    model.addAttribute("orders", formattedOrdersWithPage);

    return "admin/admin-orders";
  }

  @PostMapping("/confirmation/{orderNumber}")
  public ResponseEntity<String> confirmOrder(@PathVariable("orderNumber") String orderNumber) {
    try {
      orderService.updateOrderStatus(orderNumber, OrderStatus.PROCESSING);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return null;
    }
  }

  @PostMapping("/cancellation/{orderNumber}")
  public ResponseEntity<String> cancelOrder(@PathVariable("orderNumber") String orderNumber) {
    try {
      orderService.updateOrderStatus(orderNumber, OrderStatus.CANCELLED);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return null;
    }
  }

  @PostMapping("/completion/{orderNumber}")
  public ResponseEntity<String> completeOrder(@PathVariable("orderNumber") String orderNumber) {
    try {
      orderService.updateOrderStatus(orderNumber, OrderStatus.COMPLETED);
      //TODO: UPDATE USER ORDER COUNT +1
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return null;
    }
  }

  /*Menu*/

  @GetMapping("/menus")
  public String getMenuListPage(Model model, Pageable pageable) {
    Page<AdminMenuListDTO> allMenus = menuService.getAllMenus(pageable);

    model.addAttribute("menus", allMenus);

    return "admin/menu-list";
  }

  @GetMapping("/menu/{menuId}")
  public String getMenuDetail(Model model, @PathVariable("menuId") int menuId) {
    return "admin/menu-detail";
  }


}
