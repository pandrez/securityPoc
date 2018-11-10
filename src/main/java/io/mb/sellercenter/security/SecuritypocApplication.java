package io.mb.sellercenter.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class SecuritypocApplication {

  public static void main(String[] args) {
    SpringApplication.run(SecuritypocApplication.class, args);
  }
}
