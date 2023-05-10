package com.ohouse.ohouse.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "order_detail")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDetail {
  @EmbeddedId
  private OrderDetailId orderDetailId;

  @MapsId("orderId")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  @NotNull
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "menu_id")
  @NotNull
  private Menu menu;

  @Column(name = "quantity")
  @NotNull
  private int quantity;

  /*Nullable*/
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "option_id")
  private Options options;

  @Builder
  public OrderDetail(OrderDetailId orderDetailId, Order order, Menu menu, int quantity, Options options) {
    this.orderDetailId = orderDetailId;
    this.order = order;
    this.menu = menu;
    this.quantity = quantity;
    this.options = options;
  }
}

@Embeddable
class OrderDetailId implements Serializable {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_detail_id")
  private Long orderDetailId;

  @Column(name = "order_id")
  private Long orderId;

}


/* Builder Example:
OrderDetail orderDetail = OrderDetail.builder()
        .orderDetailId(new OrderDetailId(null, 1L))
        .order(orderInstance)
        .menu(menuInstance)
        .quantity(2)
        .options(optionsInstance)
        .build();
*
* */
