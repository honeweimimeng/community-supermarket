package com.hone.servicesocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = {"com.hone.servicesocket.*","com.hone.localcommons.*"})
@EnableAspectJAutoProxy
public class ServiceSocketApplication{
    public static void main(String[] args) {
        SpringApplication.run(ServiceSocketApplication.class, args);
    }
}
