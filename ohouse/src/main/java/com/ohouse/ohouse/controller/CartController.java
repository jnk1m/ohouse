package com.ohouse.ohouse.controller;

import com.ohouse.ohouse.domain.CartItemDTO;
import com.ohouse.ohouse.domain.UserDTO;
import com.ohouse.ohouse.entity.Cart;
import com.ohouse.ohouse.entity.CartOption;
import com.ohouse.ohouse.exception.MenuNotFoundException;
import com.ohouse.ohouse.service.CartService;
import com.ohouse.ohouse.service.MenuService;
import com.ohouse.ohouse.service.OptionService;
import com.ohouse.ohouse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {
  private final CartService cartService;
  private final MenuService menuService;
  private final UserService userService;
  private final OptionService optionService;

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
  ) throws MenuNotFoundException {
    UserDTO userDTO = (UserDTO) session.getAttribute("user");

    Cart cart = Cart.builder()
            .quantity(quantity)
            .menu(menuService.getMenu(menuId))
            .user(userService.getUserById(userDTO.getUserId()))
            .build();
    Cart createdCart = cartService.createCart(cart);

    if (optionId != null) {
      List<CartOption> cartOptionList = optionId.stream().map(id -> CartOption.builder().cart(createdCart)
              .option(optionService.getById(id)).build()).collect(Collectors.toList());

      List<CartOption> cartOption = cartService.createCartOption(cartOptionList);
    }

    //TODO: responseEntity로 리턴하도록 수정하기. 전화번호 인증처럼 특정 메세지 받으면 장바구니 담기 완료했다는 팝업 띄워주기
    return "index";
  }

  @DeleteMapping
  public String deleteCartItem(@RequestParam("cartId") int cartId) {
    cartService.deleteCartItem(cartId);
    return "redirect:/carts";
  }
}
