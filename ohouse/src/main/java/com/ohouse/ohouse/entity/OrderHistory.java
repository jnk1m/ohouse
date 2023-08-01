package com.ohouse.ohouse.entity;

import com.ohouse.ohouse.enums.OrderStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@Table(name = "order_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderHistory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_history_id")
  private int orderHistoryId;

  @Column(name = "history_date")
  @NotNull
  private ZonedDateTime historyDate;

  @Column(name = "history_timezone")
  @NotNull
  private String historyTimeZone;

  @Column(name = "order_status")
  @Enumerated(EnumType.STRING)
  @NotNull
  private OrderStatus orderStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  @NotNull
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @NotNull
  private User user;


  @Builder
  public OrderHistory(OrderStatus orderStatus, Order order, User user) {
    this.historyDate = ZonedDateTime.now(ZoneId.of("UTC"));
    this.historyTimeZone = ZonedDateTime.now().getZone().toString();
    this.orderStatus = orderStatus;
    this.order = order;
    this.user = user;
  }
}

/*Builder Example
OrderHistory.builder()
        .user(userInstance)
        .order(orderInstance)
        .orderStatus(OrderStatus.CREATED)
        .build();
*/