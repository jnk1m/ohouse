package com.ohouse.ohouse.repository;

import com.ohouse.ohouse.domain.OrderedItemOptionDTO;
import com.ohouse.ohouse.entity.OrderItemOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemOptionRepository extends JpaRepository<OrderItemOption, Integer> {

  @Query("SELECT new com.ohouse.ohouse.domain.CartOptionDTO(" +
          "oio.orderItem.orderItemId, oio.option.optionId, o.optionName, o.optionPrice) " +
          "FROM OrderItemOption oio " +
          "JOIN  oio.option o " +
          "WHERE oio.orderItem.orderItemId = :parentOrderId")
  List<OrderedItemOptionDTO> findOptionByParentOrderId(int parentOrderId);
}
