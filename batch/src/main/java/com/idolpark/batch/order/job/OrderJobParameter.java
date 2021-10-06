package com.idolpark.batch.order.job;

import lombok.Getter;
import lombok.ToString;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.stereotype.Component;

@Component
@JobScope
@Getter
@ToString
public class OrderJobParameter {

    private Integer chunkSize;

    private Integer quantity;


    public void init(OrderJobParameterCommandLine jobParameter) {
        chunkSize = jobParameter.getChunkSize();
        quantity = jobParameter.getQuantity();
    }

}
