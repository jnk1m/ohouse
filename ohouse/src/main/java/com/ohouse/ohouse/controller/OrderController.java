package com.ohouse.ohouse.controller;

import com.ohouse.ohouse.domain.CartItemDTO;
import com.ohouse.ohouse.domain.UserDTO;
import com.ohouse.ohouse.service.CartService;
import com.ohouse.ohouse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

import static com.ohouse.ohouse.controller.CartController.getTotalPrice;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
  private final UserService userService;
  private final CartService cartService;


  @ModelAttribute("userDTO")
  public UserDTO getUserDTO(HttpSession session) {
    return (UserDTO) session.getAttribute("user");
  }

  @GetMapping("/checkout")
  public String getCheckOutPage(@ModelAttribute("userDTO") UserDTO userDTO, Model model) {
    List<CartItemDTO> cartList = cartService.getCartItemList(userDTO.getUserId());
    BigDecimal subTotal = getTotalPrice(cartList);

    model.addAttribute("subTotal", subTotal);
    model.addAttribute("total", subTotal.add(BigDecimal.valueOf(2.50)));

    return "checkout";
  }

  @PostMapping
  public String placeOrder(@RequestParam("name") String name,
                           @RequestParam("phone") String phone,
                           @RequestParam("bldgNo") String bldgNo,
                           @RequestParam("roomNo") String roomNo,
                           @RequestParam(name = "instruction", required = false) String instruction,
                           @RequestParam("payment") String paymentMethod) {
    return "index";
  }
}
