package com.ohouse.ohouse.entity;

import com.ohouse.ohouse.enums.OrderStatus;
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

  @Column(name = "order_status")
  @Enumerated(EnumType.STRING)
  @NotNull
  private OrderStatus orderStatus;

  /*The current time is set as the default value in the database (CURRENT_TIMESTAMP)*/
  @Column(name = "history_date")
  private LocalDateTime historyDate;

  @Builder
  public OrderHistory(OrderHistoryId orderHistoryId, User user, Order order, OrderStatus orderStatus) {
    this.orderHistoryId = orderHistoryId;
    this.user = user;
    this.order = order;
    this.orderStatus = orderStatus;
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
        .orderStatus(OrderStatus.CREATED)
        .build();
        */