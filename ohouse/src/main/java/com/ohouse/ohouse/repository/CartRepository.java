package com.ohouse.ohouse.repository;

import com.ohouse.ohouse.domain.CartItemDTO;
import com.ohouse.ohouse.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {

  @Query("SELECT new com.ohouse.ohouse.domain.CartItemDTO(" +
          "cart.cartId, cart.quantity, cart.menu.menuId, menu.menuNameEng, menu.menuPrice) " +
          "FROM Cart cart " +
          "JOIN cart.menu menu " +
          "WHERE cart.user.userId = :userId")
  List<CartItemDTO> findCartItemsByUserId(int userId);

  void deleteByCartId(int cartId);

  @Transactional
  @Modifying
  @Query("DELETE FROM Cart cart Where cart.user.userId = :userId")
  void deleteAllByUserId(int userId);

  @Query("SELECT cart.cartId FROM Cart cart where cart.user.userId = :userId")
  List<Integer> findCartIdByUserId(int userId);
}
