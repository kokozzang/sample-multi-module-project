package com.idolpark.batch.order.job;

import com.idolpark.batch.common.UniqueRunIdIncrementer;
import com.idolpark.order.application.OrderService;
import com.idolpark.order.domain.model.Order;
import com.idolpark.order.domain.model.OrderStats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.persistence.EntityManagerFactory;
import java.time.Duration;
import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class OrderJob {

    public static final String JOB_NAME = "OrderJob";
    public static final String ORDER_STEP_NAME = JOB_NAME + "_OrderStep";

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final OrderService orderService;
    private final EntityManagerFactory emf;


    private final OrderJobParameterCommandLine jobParameterCommandLiner;
    private final OrderJobParameter jobParameter;


    @Bean(JOB_NAME)
    public Job job() {
        return jobBuilderFactory.get(JOB_NAME)
            .incrementer(new UniqueRunIdIncrementer())
            .preventRestart()
            .start(emptyStep())
            .next(orderStep())
            .listener(jobExecutionListener())
            .build();
    }

    @Bean(JOB_NAME + "_taskPool")
    @JobScope
    public TaskExecutor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(jobParameter.getStepThreadCount());
        executor.setMaxPoolSize(jobParameter.getStepThreadCount());
        executor.setThreadNamePrefix("multi-thread-");
        executor.setWaitForTasksToCompleteOnShutdown(Boolean.TRUE);
        executor.initialize();

        return executor;
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

    @Bean(ORDER_STEP_NAME)
    @JobScope
    public Step orderStep() {
        return stepBuilderFactory.get(ORDER_STEP_NAME)
            .<Order, OrderStats>chunk(jobParameter.getChunkSize())
            .reader(orderReader())
            .processor(orderItemProcessor())
            .writer(orderWriter())
            .taskExecutor(executor())
            .throttleLimit(jobParameter.getStepThreadCount())
            .build();

    }


    @Bean(ORDER_STEP_NAME + "orderReader")
    @StepScope
    public RepositoryMethodReader<Order, Long> orderReader() {
        return RepositoryMethodReader.<Order, Long>builder()
            .findLastOffsetFunction(Order::getId)
            .readerFunction((afterId) -> orderService.getOrders(jobParameter.getQuantity(), jobParameter.getChunkSize(), afterId))
            .initialOffset(0L)
            .build();
    }

    @Bean(ORDER_STEP_NAME + "orderItemProcessor")
    @StepScope
    public ItemProcessor<Order, OrderStats> orderItemProcessor() {
        return new OrderItemProcessor();
    }

    @Bean(ORDER_STEP_NAME + "orderWriter")
    @StepScope
    public JpaItemWriter<OrderStats> orderWriter() {
        return new JpaItemWriterBuilder<OrderStats>()
            .entityManagerFactory(emf)
            .usePersist(true)
            .build();
    }


    @Bean(JOB_NAME + "_jobExecutionListener")
    @JobScope
    public JobExecutionListener jobExecutionListener(){

        return new JobExecutionListener() {
            LocalDateTime startDt;

            @Override
            public void beforeJob(JobExecution jobExecution) {
                startDt = LocalDateTime.now();

                jobParameter.init(jobParameterCommandLiner);
                log.info("job 파라미터: " + jobParameter);
                log.info("배치 시작");
            }

            @Override
            public void afterJob(JobExecution jobExecution) {
                LocalDateTime endDt = LocalDateTime.now();
                Duration duration = Duration.between(startDt, endDt);
                log.info("배치 종료");
                log.info("총 수행시간: " + duration.getSeconds() + "초");
            }
        };

    }




}
