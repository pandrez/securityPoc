package io.mb.sellercenter.security.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class HttpSecurityConfig {

  public static final int PASSWORD_HASH_STRENGTH = 4;

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder(PASSWORD_HASH_STRENGTH);
  }

}
