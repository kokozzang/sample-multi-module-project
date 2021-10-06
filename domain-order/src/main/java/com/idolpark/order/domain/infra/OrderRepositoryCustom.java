package com.idolpark.order.domain.infra;

import com.idolpark.order.domain.model.Order;

import java.util.List;

public interface OrderRepositoryCustom {

    List<Order> findOrders(Integer quantity, int size, Long afterId);

}
