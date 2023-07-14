package com.ohouse.ohouse.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CartItemDTO {
  private final int cartId;
  private final int quantity;
  private final CartMenuDTO cartMenuDTO;
  private List<CartOptionDTO> options = new ArrayList<>();

  public CartItemDTO(Integer cartId, Integer quantity, Integer menuId, String menuNameEng, BigDecimal menuPrice) {
    this.cartId = cartId;
    this.quantity = quantity;
    this.cartMenuDTO = new CartMenuDTO(menuId, menuNameEng, menuPrice);
  }
}
