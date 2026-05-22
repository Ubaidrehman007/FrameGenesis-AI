package com.framegenesis.framegenesisai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FramegenesisAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FramegenesisAiApplication.class, args);
    }

}
