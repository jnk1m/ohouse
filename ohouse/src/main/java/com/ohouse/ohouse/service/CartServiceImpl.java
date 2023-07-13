package com.ohouse.ohouse.service;

import com.ohouse.ohouse.domain.CartItemDTO;
import com.ohouse.ohouse.domain.CartOptionDTO;
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
  public List<CartItemDTO> getCartItemList(int userId) {
    List<CartItemDTO> cartItemsByUserId = cartRepository.findCartItemsByUserId(userId);
    cartItemsByUserId.forEach(cartItemDTO -> {
      List<CartOptionDTO> optionByCartId = cartOptionRepository.findOptionByCartId(cartItemDTO.getCartId());
      optionByCartId.stream()
              .filter(optionDTO -> optionDTO.getParentCartId() == cartItemDTO.getCartId())
              .forEach(matchingOption -> cartItemDTO.getOptions().add(matchingOption));
    });

    return cartItemsByUserId;
  }


}
