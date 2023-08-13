package com.ohouse.ohouse.controller;

import com.ohouse.ohouse.domain.OrderSummaryDTO;
import com.ohouse.ohouse.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
  private final OrderService orderService;

  @GetMapping("/orders")
  public String getOrdersPage(Model model, Pageable pageable) {
    Page<OrderSummaryDTO> orders = orderService.getAllOrders(pageable);

    model.addAttribute("orders", orders);

    return "admin/admin-orders";
  }
}
