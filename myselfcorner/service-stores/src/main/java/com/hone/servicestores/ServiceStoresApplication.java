package com.hone.servicestores;

import com.hone.servicesocket.ServiceSocketApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableEurekaClient
@EnableTransactionManagement
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.hone.servicestores.*")
@SpringBootApplication(scanBasePackages = {"com.hone.servicestores.*","com.hone.localcommons.*"})
public class ServiceStoresApplication{
    public static void main(String[] args) {
        SpringApplication.run(ServiceStoresApplication.class, args);
    }
}
