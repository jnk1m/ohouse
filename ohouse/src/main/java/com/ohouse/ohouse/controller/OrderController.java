package com.ohouse.ohouse.controller;

import com.ohouse.ohouse.domain.CartItemDTO;
import com.ohouse.ohouse.domain.OrderDetailDTO;
import com.ohouse.ohouse.domain.OrderedItemDTO;
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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

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

    validateIsNull(firstName, lastName, bldgNo, roomNo);
    validateLength(firstName, lastName, bldgNo, roomNo);
    if (instructions != null && !instructions.isEmpty()) {
      validateInstructionLength(instructions);
    }
    validateRegex(firstName, lastName, bldgNo, roomNo);

    int userId = userDTO.getUserId();

    Order newOrder = Order.builder()
            .user(userService.getUserById(userId))
            .price(new BigDecimal(totalPrice))
            .paymentMethod(PaymentMethod.valueOf(paymentMethod.toUpperCase()))
            .deliveryAddress(bldgNo + ", " + roomNo)
            .deliveryContact(userService.getPhoneById(userId))
            .specialInstruction(instructions)
            .orderStatus(OrderStatus.ACCEPTING)
            .name(firstName + " " + lastName)
            .orderNumber(generateOrderNumber(ZonedDateTime.now())).build();

    List<Integer> usersCartId = cartService.getUsersCartId(userId);
    orderService.placeOrder(newOrder, usersCartId);

    return "index";
  }

  @GetMapping("/details/{orderNumber}")
  public String getOrder(@PathVariable("orderNumber") String orderNumber, Model model) {
    OrderDetailDTO orderWithOrderNumber = orderService.getOrderWithOrderNumber(orderNumber);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm");
    ZonedDateTime customerDateTime = orderWithOrderNumber.getOrderDate().withZoneSameInstant(ZoneId.of("Asia/Seoul"));
    String formattedOrderDate = customerDateTime.format(formatter);

    int orderId = orderService.getOrderIdWithOrderNumber(orderNumber);
    List<OrderedItemDTO> orderItemList = orderService.getOrderItemList(orderId);

    model.addAttribute("order", orderWithOrderNumber);
    model.addAttribute("orderDate", formattedOrderDate);
    model.addAttribute("orderItemList", orderItemList);

    return "order-detail";

  }

  protected void validateIsNull(String firstName, String lastName, String bldgNo, String roomNo) {
    if (firstName == null || firstName.isEmpty()) {
      throw new IllegalArgumentException("First name must not be empty");
    }
    if (lastName == null || lastName.isEmpty()) {
      throw new IllegalArgumentException("Last name must not be empty");
    }
    if (bldgNo == null || bldgNo.isEmpty()) {
      throw new IllegalArgumentException("Building number must not be empty");
    }
    if (roomNo == null || roomNo.isEmpty()) {
      throw new IllegalArgumentException("Room number must not be empty");
    }
  }

  protected void validateLength(String firstName, String lastName, String bldgNo, String roomNo) {
    StringBuilder fullName = new StringBuilder();
    fullName.append(firstName).append(" ").append(lastName);

    if (fullName.length() > 50) {
      throw new IllegalArgumentException("Full name must be 50 characters or less");
    }

    StringBuilder fullAddress = new StringBuilder();
    fullAddress.append(bldgNo).append(" ").append(roomNo);

    if (fullAddress.length() > 200) {
      throw new IllegalArgumentException("Delivery address must be 200 characters or less");
    }
  }

  protected void validateRegex(String firstName, String lastName, String bldgNo, String roomNo) {
    String namePattern = "^[\\p{L} .'-]+$";
    String bldgNoPattern = "^[\\p{L}0-9 .'-]+$";
    String roomNoPattern = "^[0-9]+$";

    if (!firstName.matches(namePattern) || !lastName.matches(namePattern)) {
      throw new IllegalArgumentException("Name must contain only alphabet characters");
    }

    if (!bldgNo.matches(bldgNoPattern)) {
      throw new IllegalArgumentException("Building number must contain only alphanumeric characters");
    }

    if (!roomNo.matches(roomNoPattern)) {
      throw new IllegalArgumentException("Room number must contain only numeric characters");
    }
  }

  protected void validateInstructionLength(String instruction) {
    if (instruction.length() > 200) {
      throw new IllegalArgumentException("Special Instruction must be 200 characters or less");
    }
  }


  protected String getCurrentTimeZone(ZonedDateTime now) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ssSSS");
    return now.format(formatter);
  }

  protected String generateRandomAlphaNumeric() {
    final int LENGTH = 5;
    String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    StringBuilder generatedString = new StringBuilder(LENGTH);
    Random random = new Random();
    for (int i = 0; i < LENGTH; i++) {
      int index = random.nextInt(alphaNumericString.length());
      char randomChar = alphaNumericString.charAt(index);
      generatedString.append(randomChar);
    }
    return generatedString.toString();
  }

  protected String generateOrderNumber(ZonedDateTime zonedId) {
    String time = getCurrentTimeZone(zonedId);
    String randomAlphaNumeric = generateRandomAlphaNumeric();
    return time + randomAlphaNumeric;
  }


}

