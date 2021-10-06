package com.idolpark.order.application;

import com.idolpark.order.application.command.OrderCreateCommand;
import com.idolpark.order.domain.infra.OrderRepository;
import com.idolpark.order.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;


    public Order createOrder(OrderCreateCommand command) {
        Order order = Order.of(command.getTitle(), command.getDescription(), command.getQuantity());

        return orderRepository.save(order);
    }

    public Order getOrder(Long orderId) {

        return orderRepository.findById(orderId)
            .orElseThrow(EntityNotFoundException::new);
    }

    public List<Order> getOrders(Integer quantity, int size, Long afterId) {
        return orderRepository.findOrders(quantity, size, afterId);
    }

}
