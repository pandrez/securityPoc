package io.mb.sellercenter.security.auth.basicauth.internal;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import io.mb.sellercenter.security.auth.basicauth.AbstractBasicAuthenticationConfigurerAdapter;

@Configuration
@Order(101)
public class InternalApiBasicAuthenticationConfigurerAdapter extends AbstractBasicAuthenticationConfigurerAdapter {

  public InternalApiBasicAuthenticationConfigurerAdapter(InternalBasicAuthenticationProperties properties, PasswordEncoder passwordEncoder) {
    super(properties, passwordEncoder);
  }


  @Override
  protected void configure(HttpSecurity http) throws Exception {
    configureHttp(http, "Internal");
    http.formLogin();
  }

}
