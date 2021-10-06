package com.idolpark.batch.order.job;

import lombok.Getter;
import lombok.ToString;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.stereotype.Component;

@Component
@Getter
@ToString
public class OrderJobParameter {

    private Integer chunkSize;

    private Integer quantity;

    private Integer stepThreadCount;


    public void init(OrderJobParameterCommandLine jobParameter) {
        chunkSize = jobParameter.getChunkSize();
        stepThreadCount = jobParameter.getStepThreadCount();
        quantity = jobParameter.getQuantity();
    }

}
