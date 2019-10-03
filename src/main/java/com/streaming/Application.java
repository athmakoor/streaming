package com.streaming;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SuppressWarnings("checkstyle:hideutilityclassconstructor")
public class Application {
    public static void main(final String[] args) {
        BasicConfigurator.configure();
        SpringApplication.run(Application.class, args);
    }
}
