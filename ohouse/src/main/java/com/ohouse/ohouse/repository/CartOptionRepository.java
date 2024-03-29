package com.ohouse.ohouse.repository;

import com.ohouse.ohouse.domain.CartOptionDTO;
import com.ohouse.ohouse.entity.CartOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartOptionRepository extends JpaRepository<CartOption, Integer> {

  @Query("SELECT new com.ohouse.ohouse.domain.CartOptionDTO(" +
          "co.cart.cartId, co.option.optionId, o.optionName, o.optionPrice) " +
          "FROM CartOption co " +
          "JOIN co.option o " +
          "WHERE co.cart.cartId = :parentCartId")
  List<CartOptionDTO> findOptionByCartId(int parentCartId);

  void deleteByCart_CartId(int cartId);

  @Transactional
  @Modifying
  @Query("DELETE FROM CartOption co WHERE co.cart.cartId IN :cartIds")
  void deleteByCartIdIn(List<Integer> cartIds);
}

