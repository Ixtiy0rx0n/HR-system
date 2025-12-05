package org.hrsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HrSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrSystemApplication.class, args);
    }

}
