package com.ohouse.ohouse.service;

import com.ohouse.ohouse.entity.Cart;
import com.ohouse.ohouse.entity.CartOption;

import java.util.List;

public interface CartService {

  Cart createCart(Cart cart);

  List<CartOption> createCartOption(List<CartOption> cartOptionList);
}
