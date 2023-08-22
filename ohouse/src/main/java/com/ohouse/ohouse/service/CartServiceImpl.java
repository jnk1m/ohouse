package com.ohouse.ohouse.service;

import com.ohouse.ohouse.domain.CartItemDTO;
import com.ohouse.ohouse.domain.CartOptionDTO;
import com.ohouse.ohouse.entity.Cart;
import com.ohouse.ohouse.entity.CartOption;
import com.ohouse.ohouse.repository.CartOptionRepository;
import com.ohouse.ohouse.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
  private final CartRepository cartRepository;
  private final CartOptionRepository cartOptionRepository;
  private final OptionService optionService;

  @Transactional
  @Override
  public Cart createCart(Cart cart) {
    return cartRepository.save(cart);
  }


  @Transactional
  @Override
  public Cart createCartAndCartOptions(Cart cart, List<Integer> optionIdList) {

    Cart createdCart = cartRepository.save(cart);
    List<CartOption> cartOptionList = optionIdList.stream().map(id ->
                    CartOption.builder()
                            .cart(createdCart)
                            .option(optionService.getById(id))
                            .build())
            .collect(Collectors.toList());

    cartOptionRepository.saveAll(cartOptionList);

    return createdCart;
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

  @Transactional
  @Override
  public void deleteCartItem(int cartId) {
    cartOptionRepository.deleteByCart_CartId(cartId);
    cartRepository.deleteByCartId(cartId);
  }

  @Transactional
  @Override
  public void deleteAllCartItem(List<Integer> usersCartId, int userId) {
    cartOptionRepository.deleteByCartIdIn(usersCartId);
    cartRepository.deleteAllByUserId(userId);
  }

  @Override
  public List<Integer> getUsersCartId(int userId) {
    return cartRepository.findCartIdByUserId(userId);
  }

}
