package com.idolpark.batch.order.job;

import lombok.Getter;
import lombok.ToString;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@JobScope
@Getter
@ToString
public class OrderJobParameterCommandLine {

    private static final Integer DEFAULT_CHUNK_SIZE = 50;
    private static final Integer DEFAULT_STEP_THREAD_COUNT = 2;
    private static final Integer DEFAULT_QUANTITY = 0;

    private Integer chunkSize;

    private Integer stepThreadCount;

    private Integer quantity;



    @Value("#{jobParameters[chunk_size]}")
    public void setChunkSize(Integer chunkSize) {
        this.chunkSize = Optional.ofNullable(chunkSize)
            .orElse(DEFAULT_CHUNK_SIZE);
    }

    @Value("#{jobParameters[step_thread_count]}")
    public void setStepThreadCount(Integer stepThreadCount) {
        this.stepThreadCount = Optional.ofNullable(stepThreadCount)
            .orElse(DEFAULT_STEP_THREAD_COUNT);
    }

    @Value("#{jobParameters[quantity]}")
    public void setQuantity(Integer quantity) {
        this.quantity = Optional.ofNullable(quantity)
            .orElse(DEFAULT_QUANTITY);
    }

}
