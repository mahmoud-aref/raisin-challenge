package com.raisin.backend.app;

import com.raisin.backend.infrastructure.http.client.FixtureClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableFeignClients(clients = FixtureClient.class)
public class RaisinBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(RaisinBackendApplication.class, args);
  }

}
