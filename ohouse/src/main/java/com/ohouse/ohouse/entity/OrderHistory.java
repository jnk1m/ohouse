package com.ohouse.ohouse.entity;

import com.ohouse.ohouse.enums.OrderStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderHistory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_history_id")
  private int orderHistoryId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "google_id")
  @NotNull
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
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
  public OrderHistory(User user, Order order, OrderStatus orderStatus) {
    this.user = user;
    this.order = order;
    this.orderStatus = orderStatus;
  }
}


/*Builder Example
OrderHistory.builder()
        .user(userInstance)
        .order(orderInstance)
        .orderStatus(OrderStatus.CREATED)
        .build();

*/