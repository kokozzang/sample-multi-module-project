package com.idolpark.order.presentation;

import com.idolpark.order.application.command.OrderCreateCommand;
import lombok.Getter;

@Getter
public class OrderCreateRequest {

    private String title;

    private String description;

    private Integer quantity;


    public OrderCreateCommand toCommand() {
        return OrderCreateCommand.of(title, description, quantity);
    }

}
