package com.ohouse.ohouse.service;

import com.ohouse.ohouse.domain.CartItemDTO;
import com.ohouse.ohouse.entity.Cart;
import com.ohouse.ohouse.entity.CartOption;

import java.util.List;

public interface CartService {

  Cart createCart(Cart cart);

  Cart createCartAndCartOptions(Cart cart, List<Integer> optionIdList);

//  List<CartOption> createCartOption(List<CartOption> cartOptionList);

  List<CartItemDTO> getCartItemList(int userId);

  void deleteCartItem(int cartId);
}
