package com.idolpark.order.presentation;

import com.idolpark.order.application.OrderService;
import com.idolpark.order.domain.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/orders")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping("")
    public Order createOrder(@RequestBody OrderCreateRequest request) {

        return orderService.createOrder(request.toCommand());

    }

    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable(name = "orderId") Long orderId) {

        return orderService.getOrder(orderId);
    }

    @GetMapping("")
    public List<Order> getOrders(@RequestParam("quantity") Integer quantity,
                                 @RequestParam("size") Integer size,
                                 @RequestParam("afterId") Long afterId) {

        return orderService.getOrders(quantity, size, afterId);
    }

}
