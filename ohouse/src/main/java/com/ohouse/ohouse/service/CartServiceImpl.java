package com.ohouse.ohouse.service;

import com.ohouse.ohouse.domain.UserCartDTO;
import com.ohouse.ohouse.entity.Cart;
import com.ohouse.ohouse.entity.CartOption;
import com.ohouse.ohouse.repository.CartOptionRepository;
import com.ohouse.ohouse.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
  private final CartRepository cartRepository;
  private final CartOptionRepository cartOptionRepository;

  @Override
  public Cart createCart(Cart cart) {
    return cartRepository.save(cart);
  }

  @Override
  public List<CartOption> createCartOption(List<CartOption> cartOptionList) {
    List<CartOption> savedCartOptions = new ArrayList<>();
    for (CartOption cartOption : cartOptionList) {
      savedCartOptions.add(cartOptionRepository.save(cartOption));
    }
    return savedCartOptions;
  }

  @Override
  public List<UserCartDTO> getCartList(int userId) {
    return cartRepository.findCartByUser(userId);
  }


}
