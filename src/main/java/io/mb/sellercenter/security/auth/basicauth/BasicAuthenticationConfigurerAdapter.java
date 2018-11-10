package io.mb.sellercenter.security.auth.basicauth;

import java.util.Set;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import io.mb.sellercenter.security.auth.basicauth.model.BasicAuthenticationModel;

@Configuration
@Order(3)
public class BasicAuthenticationConfigurerAdapter extends WebSecurityConfigurerAdapter {

  private final BasicAuthenticationSecurityProperties properties;

  public BasicAuthenticationConfigurerAdapter(BasicAuthenticationSecurityProperties properties) {
    this.properties = properties;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    Set<String> apiNames = properties.getBasicAuths()
        .keySet();
    for (String apiName : apiNames) {
      BasicAuthenticationModel basicAuthenticationModel = properties.getBasicAuths()
          .get(apiName);

      http.csrf()
          .disable()
          .authenticationProvider(new BasicAuthenticationProvider(basicAuthenticationModel.getUsername(), basicAuthenticationModel.getPassword(),
              basicAuthenticationModel.getRole()))
          .authorizeRequests()
          .antMatchers(basicAuthenticationModel.getPattern())
          .hasRole(basicAuthenticationModel.getRole())
          .and()
          .httpBasic()
          .realmName(apiName)
          // .authenticationEntryPoint(getBasicAuthEntryPoint())
          .and()
          .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
  }
}
