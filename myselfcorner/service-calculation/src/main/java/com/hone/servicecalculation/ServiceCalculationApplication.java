package com.hone.servicecalculation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = {"com.hone.servicecalculation.*","com.hone.localcommons.*"})
public class ServiceCalculationApplication{

    public static void main(String[] args) {
        SpringApplication.run(ServiceCalculationApplication.class, args);
    }

}
