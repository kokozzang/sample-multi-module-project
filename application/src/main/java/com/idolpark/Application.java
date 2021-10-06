package com.idolpark;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.TimeZone;

@SpringBootApplication
@Slf4j
public class Application {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        log.info("Set default time zone = " + TimeZone.getDefault());

        SpringApplication application = new SpringApplicationBuilder()
            .sources(Application.class)
            .web(WebApplicationType.SERVLET)
            .bannerMode(Banner.Mode.OFF)
            .build();

        application.run(args);
    }
}
