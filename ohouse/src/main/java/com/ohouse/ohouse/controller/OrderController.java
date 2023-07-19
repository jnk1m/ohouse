package com.ohouse.ohouse.controller;

import com.ohouse.ohouse.domain.CartItemDTO;
import com.ohouse.ohouse.domain.UserDTO;
import com.ohouse.ohouse.entity.Order;
import com.ohouse.ohouse.enums.OrderStatus;
import com.ohouse.ohouse.enums.PaymentMethod;
import com.ohouse.ohouse.service.CartService;
import com.ohouse.ohouse.service.OrderService;
import com.ohouse.ohouse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
  private final OrderService orderService;


  @ModelAttribute("userDTO")
  public UserDTO getUserDTO(HttpSession session) {
    return (UserDTO) session.getAttribute("user");
  }

  @GetMapping("/checkout")
  public String getCheckOutPage(@ModelAttribute("userDTO") UserDTO userDTO, Model model, RedirectAttributes redirectAttrs) {

    /*Performing verification for phone number authentication*/
    boolean phoneVerificationStatus = userService.checkPhoneVerificationStatus(userDTO.getUserId());
    if (!phoneVerificationStatus) {
      redirectAttrs.addFlashAttribute("alertMessage", "To proceed with the order, please verify your phone number first.");
      return "redirect:/accounts";
    }

    /*Performing validation to check if there are items in the cart*/
    List<CartItemDTO> cartList = cartService.getCartItemList(userDTO.getUserId());
    if (cartList.isEmpty()) {
      redirectAttrs.addFlashAttribute("alertMessage", "Your cart is empty at the moment. Please add items to your cart to proceed with the order.");
      return "redirect:/carts";
    }

    BigDecimal subTotal = getTotalPrice(cartList);

    model.addAttribute("telephone", userService.getPhoneById(userDTO.getUserId()));
    model.addAttribute("subTotal", subTotal);
    model.addAttribute("total", subTotal.add(BigDecimal.valueOf(2.50)));

    return "checkout";
  }

  @PostMapping
  public String placeOrder(@ModelAttribute("userDTO") UserDTO userDTO,
                           @RequestParam("firstName") String firstName,
                           @RequestParam("lastName") String lastName,
                           @RequestParam("bldgNo") String bldgNo,
                           @RequestParam("roomNo") String roomNo,
                           @RequestParam(name = "instructions", required = false) String instructions,
                           @RequestParam(name = "totalPrice") String totalPrice,
                           @RequestParam("payment") String paymentMethod) {

    Order newOrder = Order.builder()
            .user(userService.getUserById(userDTO.getUserId()))
            .price(new BigDecimal(totalPrice))
            .paymentMethod(PaymentMethod.valueOf(paymentMethod.toUpperCase()))
            .deliveryAddress(bldgNo.concat(" " + roomNo))
            .deliveryContact(userService.getPhoneById(userDTO.getUserId()))
            .specialInstruction(instructions)
            .orderStatus(OrderStatus.PROCESSING)
            .name(firstName.concat(" " + lastName)).build();

    orderService.createOrder(newOrder);


    return "index";

  }
}

