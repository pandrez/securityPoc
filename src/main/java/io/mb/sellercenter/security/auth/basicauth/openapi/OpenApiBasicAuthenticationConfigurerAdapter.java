package io.mb.sellercenter.security.auth.basicauth.openapi;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import io.mb.sellercenter.security.auth.basicauth.AbstractBasicAuthenticationConfigurerAdapter;

@Configuration
@Order(100)
public class OpenApiBasicAuthenticationConfigurerAdapter extends AbstractBasicAuthenticationConfigurerAdapter {


  public OpenApiBasicAuthenticationConfigurerAdapter(OpenApiBasicAuthenticationProperties properties, PasswordEncoder passwordEncoder) {
    super(properties, passwordEncoder);
  }


  @Override
  protected void configure(HttpSecurity http) throws Exception {
    configureHttp(http, "OpenAPI");
    http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }


}
