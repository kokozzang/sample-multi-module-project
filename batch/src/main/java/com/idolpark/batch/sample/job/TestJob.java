package com.idolpark.batch.sample.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class TestJob {

    public static final String JOB_NAME = "TestJob";

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;


    @Bean(JOB_NAME)
    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
            .start(emptyStep())
            .build();
    }

    @Bean(JOB_NAME + "_emptyStep")
    @JobScope
    public Step emptyStep() {
        return stepBuilderFactory.get(JOB_NAME + "_jobEmptyStep")
            .tasklet((stepContribution, chunkContext) -> {
                log.info("emptyStep");
                return RepeatStatus.FINISHED;
            })
            .build();
    }

}
