package com.ohouse.ohouse.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
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

  @PostMapping
  public String addItemToCart(@RequestParam(value = "menuOption", required = false) List<Integer> optionId,
                              @RequestParam("quantity") int quantity,
                              @RequestParam("menuId") int menuId,
                              HttpSession session
  ) throws MenuNotFoundException {
    UserDTO userDTO = (UserDTO) session.getAttribute("user");

    if (optionId != null) {
      Cart cart = Cart.builder()
              .quantity(quantity)
              .menu(menuService.getMenu(menuId))
              .user(userService.getUserById(userDTO.getUserId()))
              .build();

      Cart createdCart = cartService.createCart(cart);

      List<CartOption> cartOptionList = optionId.stream().map(id -> CartOption.builder().cart(createdCart)
              .option(optionService.getById(id)).build()).collect(Collectors.toList());

      List<CartOption> cartOption = cartService.createCartOption(cartOptionList);

    } else {
      Cart cart = Cart.builder()
              .quantity(quantity)
              .menu(menuService.getMenu(menuId))
              .user(userService.getUserById(userDTO.getUserId()))
              .build();

      Cart createdCart = cartService.createCart(cart);
    }

    //TODO: responseEntity로 리턴하도록 수정하기. 전화번호 인증처럼 특정 메세지 받으면 장바구니 담기 완료했다는 팝업 띄워주기
    return "index";

  }


}
