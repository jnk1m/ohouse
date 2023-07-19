package com.ohouse.ohouse.controller;

import com.ohouse.ohouse.domain.CartItemDTO;
import com.ohouse.ohouse.domain.UserDTO;
import com.ohouse.ohouse.entity.Cart;
import com.ohouse.ohouse.service.CartService;
import com.ohouse.ohouse.service.MenuService;
import com.ohouse.ohouse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {
  private final CartService cartService;
  private final MenuService menuService;
  private final UserService userService;

  @GetMapping
  public String getCartList(HttpSession session, Model model) {
    UserDTO userDTO = (UserDTO) session.getAttribute("user");
    List<CartItemDTO> cartList = cartService.getCartItemList(userDTO.getUserId());

    BigDecimal totalPrice = getTotalPrice(cartList);

    model.addAttribute("cartList", cartList);
    model.addAttribute("totalPrice", totalPrice);
    return "carts";
  }

  static BigDecimal getTotalPrice(List<CartItemDTO> cartList) {
    return cartList.stream()
            .map(cartItem -> {
              int quantity = cartItem.getQuantity();
              BigDecimal menuPrice = cartItem.getCartMenuDTO().getMenuPrice().multiply(new BigDecimal(quantity));
              BigDecimal optionsPrice = cartItem.getOptions().stream()
                      .map(option -> option.getOptionPrice().multiply(new BigDecimal(quantity)))
                      .reduce(BigDecimal.ZERO, BigDecimal::add);
              return menuPrice.add(optionsPrice);
            })
            .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  @PostMapping
  public String addItemToCart(@RequestParam(value = "menuOption", required = false) List<Integer> optionId,
                              @RequestParam("quantity") int quantity,
                              @RequestParam("menuId") int menuId,
                              HttpSession session
  ) {
    try {
      UserDTO userDTO = (UserDTO) session.getAttribute("user");

      Cart cart = Cart.builder()
              .quantity(quantity)
              .menu(menuService.getMenu(menuId))
              .user(userService.getUserById(userDTO.getUserId()))
              .build();

      if (optionId == null) {
        cartService.createCart(cart);
      } else {
        cartService.createCartAndCartOptions(cart, optionId);
      }

      return "redirect:/carts";
//      return new ResponseEntity<>("{\"message\": \"Item added\"}", HttpStatus.OK);
    } catch (Exception e) {
//      return new ResponseEntity<>("{\"message\": \"Error while adding item to cart\"}", HttpStatus.INTERNAL_SERVER_ERROR);
      return "redirect:/carts";
    }
  }

  @DeleteMapping
  public ResponseEntity<String> deleteCartItem(@RequestParam("cartId") int cartId) {
    try {
      cartService.deleteCartItem(cartId);
      return new ResponseEntity<>("{\"message\": \"Item deleted\"}", HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>("{\"message\": \"Error while deleting item to cart\"}", HttpStatus.OK);
    }
  }
}
