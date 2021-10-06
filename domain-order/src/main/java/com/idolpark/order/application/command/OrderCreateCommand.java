package com.idolpark.order.application.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderCreateCommand {

    private final String title;

    private final String description;

    private final Integer quantity;


    public static OrderCreateCommand of(String title, String description, Integer quantity) {
        return new OrderCreateCommand(title, description, quantity);
    }

}
