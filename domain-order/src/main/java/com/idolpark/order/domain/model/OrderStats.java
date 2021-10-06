package com.idolpark.order.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "order_stats")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "orderId")
    private Long orderId;

    @Column(name = "title")
    private String title;

    @Column(name = "quantity")
    private Integer quantity;


    @Builder(access = AccessLevel.PRIVATE)
    private OrderStats(Long orderId, String title, Integer quantity) {
        this.orderId = orderId;
        this.title = title;
        this.quantity = quantity;
    }

    public static OrderStats of(Long orderId, String title, Integer quantity) {
        return OrderStats.builder()
            .orderId(orderId)
            .title(title)
            .quantity(quantity)
            .build();
    }

}
