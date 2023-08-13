package com.ohouse.ohouse.repository;

import com.ohouse.ohouse.domain.OrderedItemOptionDTO;
import com.ohouse.ohouse.entity.OrderItemOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemOptionRepository extends JpaRepository<OrderItemOption, Integer> {

  @Query("SELECT new com.ohouse.ohouse.domain.OrderedItemOptionDTO(" +
          "oio.orderItem.orderItemId, o.optionName, o.optionPrice) " +
          "FROM OrderItemOption oio " +
          "JOIN  Options o on oio.option.optionId = o.optionId " +
          "WHERE oio.orderItem.orderItemId = :parentOrderItemId")
  List<OrderedItemOptionDTO> findOptionByParentOrderItemId(int parentOrderItemId);
}
