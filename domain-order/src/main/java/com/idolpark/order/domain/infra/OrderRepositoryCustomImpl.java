package com.idolpark.order.domain.infra;

import com.idolpark.order.domain.model.Order;
import static com.idolpark.order.domain.model.QOrder.order;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class OrderRepositoryCustomImpl extends QuerydslRepositorySupport implements OrderRepositoryCustom {

    public OrderRepositoryCustomImpl() {
        super(Order.class);
    }


    @Override
    public List<Order> findOrders(Integer quantity, int size, Long afterId) {

        return from(order)
            .where(
                order.quantity.gt(quantity),
                order.id.gt(afterId)
            )
            .orderBy(order.id.asc())
            .limit(size)
            .fetch();
    }

}
