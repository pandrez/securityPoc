package io.mb.sellercenter.security.auth.basicauth;

import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.mb.sellercenter.security.auth.basicauth.model.RoleModel;
import io.mb.sellercenter.security.auth.basicauth.model.UserModel;

// @Configuration
@Order(100)
public class OpenApiBasicAuthenticationConfigurerAdapter extends WebSecurityConfigurerAdapter {

  private final OpenApiBasicAuthenticationSecurityProperties properties;
  private final PasswordEncoder passwordEncoder;

  public OpenApiBasicAuthenticationConfigurerAdapter(OpenApiBasicAuthenticationSecurityProperties properties, PasswordEncoder passwordEncoder) {
    this.properties = properties;
    this.passwordEncoder = passwordEncoder;
  }


  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> inMemoryAuthentication = auth.inMemoryAuthentication();
    for (UserModel user : properties.getUsers()) {
      inMemoryAuthentication.passwordEncoder(passwordEncoder)
          .withUser(user.getUsername())
          .password(user.getPassword())
          .roles(user.getRoles()
              .stream()
              .map(RoleModel::getRole)
              .toArray(String[]::new));
    }
  }


  @Override
  protected void configure(HttpSecurity http) throws Exception {

    // ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry
    // authorizeRequests = http.csrf()
    // .disable()
    // .authorizeRequests();
    // for (PatternModel pattern : properties.getPatterns()) {
    // ExpressionUrlAuthorizationConfigurer<HttpSecurity>.AuthorizedUrl authorizedUrl =
    // authorizeRequests.antMatchers(pattern.getPattern());
    // if (!properties.isEnabled()) {
    // authorizedUrl.permitAll();
    // } else {
    // if (!pattern.getAllowedRoles()
    // .isEmpty()) {
    // authorizedUrl.hasAnyRole(pattern.getAllowedRoles()
    // .stream()
    // .map(RoleModel::getRole)
    // .toArray(String[]::new));
    // } else {
    // authorizedUrl.authenticated();
    // }
    // }
    // }
    //
    // authorizeRequests.and()
    // .formLogin()
    // .disable()
    // .logout()
    // .disable();
    // authorizeRequests.and()
    // .httpBasic()
    // .realmName("OpenAPI")
    // .and()
    // .sessionManagement()
    // .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }


}
