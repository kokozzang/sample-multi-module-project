package com.idolpark.batch.order.job;

import com.idolpark.order.domain.model.Order;
import com.idolpark.order.domain.model.OrderStats;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class OrderItemProcessor implements ItemProcessor<Order, OrderStats> {

    @Override
    public OrderStats process(Order item) throws Exception {
        log.info(item.toString());
        return OrderStats.of(item.getId(), item.getTitle(), item.getQuantity());
    }
}
