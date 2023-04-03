package com.ohouse.ohouse.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderHistory {
  @EmbeddedId
  private OrderHistoryId orderHistoryId;

  @MapsId("googleId")
  @ManyToOne
  @JoinColumn(name = "google_id")
  @NotNull
  private User user;

  @MapsId("orderId")
  @ManyToOne
  @JoinColumn(name = "order_id")
  @NotNull
  private Order order;

  @Column(name = "order_state")
  @NotNull
  private String orderState;

  /*The current time is set as the default value in the database (CURRENT_TIMESTAMP)*/
  @Column(name = "history_date")
  private LocalDateTime historyDate;

  @Builder
  public OrderHistory(OrderHistoryId orderHistoryId, User user, Order order, String orderState) {
    this.orderHistoryId = orderHistoryId;
    this.user = user;
    this.order = order;
    this.orderState = orderState;
  }
}

@Embeddable
class OrderHistoryId implements Serializable {
  @Column(name = "google_id")
  private String googleId;

  @Column(name = "order_id")
  private Long orderId;
}

/*Builder Example
OrderHistory.builder()
        .orderHistoryId(new OrderHistoryId("googleId123", 1L))
        .user(userInstance)
        .order(orderInstance)
        .orderState("delivered")
        .build();
        */