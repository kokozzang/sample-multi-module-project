package com.idolpark.order.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "orders")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity")
    private Integer quantity;


    @Builder(access = AccessLevel.PRIVATE)
    private Order(String title, String description, Integer quantity) {
        this.title = title;
        this.description = description;
        this.quantity = quantity;
    }

    public static Order of(String title, String description, Integer quantity) {
        return Order.builder()
            .title(title)
            .description(description)
            .quantity(quantity)
            .build();
    }

}
