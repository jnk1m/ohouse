package com.ohouse.ohouse.service;

import com.ohouse.ohouse.domain.CartItemDTO;
import com.ohouse.ohouse.entity.Cart;

import java.util.List;

public interface CartService {

  Cart createCart(Cart cart);

  Cart createCartAndCartOptions(Cart cart, List<Integer> optionIdList);

  List<CartItemDTO> getCartItemList(int userId);

  void deleteCartItem(int cartId);

  void deleteAllCartItem(List<Integer> usersCartId, int userId);

  List<Integer> getUsersCartId(int userId);
}
