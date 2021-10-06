package com.idolpark;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import static org.springframework.boot.WebApplicationType.NONE;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.annotation.PostConstruct;

@EnableBatchProcessing
@SpringBootApplication
@Slf4j
public class BatchApplication {

    @Value("${spring.batch.job.names:NONE}")
    private String jobNames;


    public static void main(String[] args) {

        SpringApplication springApplication = new SpringApplicationBuilder(BatchApplication.class)
            .web(NONE)
            .build();

        System.exit(SpringApplication.exit(springApplication.run(args)));
    }

    @PostConstruct
    public void validateJobNames() {
        log.info("jobNames : {}", jobNames);
        if (jobNames.isEmpty() || jobNames.equals("NONE")) {
            throw new IllegalStateException("spring.batch.job.names=job1,job2 형태로 실행을 원하는 Job을 명시해야만 합니다!");
        }
    }

}
