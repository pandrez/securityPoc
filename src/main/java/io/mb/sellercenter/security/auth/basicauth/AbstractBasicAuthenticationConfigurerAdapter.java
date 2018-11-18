package io.mb.sellercenter.security.auth.basicauth;

import io.mb.sellercenter.security.auth.basicauth.model.PatternModel;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import io.mb.sellercenter.security.auth.basicauth.model.AbstractBasicAuthenticationModel;
import io.mb.sellercenter.security.auth.basicauth.model.RoleModel;
import io.mb.sellercenter.security.auth.basicauth.model.UserModel;

public abstract class AbstractBasicAuthenticationConfigurerAdapter extends WebSecurityConfigurerAdapter {

  protected final AbstractBasicAuthenticationModel properties;
  protected final PasswordEncoder passwordEncoder;


  protected AbstractBasicAuthenticationConfigurerAdapter(AbstractBasicAuthenticationModel properties, PasswordEncoder passwordEncoder) {
    this.properties = properties;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> inMemoryAuthentication = auth.inMemoryAuthentication()
        .passwordEncoder(passwordEncoder);
    for (UserModel user : properties.getUsers()) {
      inMemoryAuthentication.withUser(user.getUsername())
          .password(user.getPassword())
          .roles(user.getRoles()
              .stream()
              .map(RoleModel::getRole)
              .toArray(String[]::new));
    }
  }

  protected void configureHttp(HttpSecurity http, String realmName) throws Exception {
    ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizeRequests = http.csrf()
            .disable()
            .antMatcher(properties.getMainPattern())
            .authorizeRequests();

    for (PatternModel subPattern : properties.getSubPatterns()) {
      ExpressionUrlAuthorizationConfigurer<HttpSecurity>.AuthorizedUrl authorizedUrl = authorizeRequests.antMatchers(subPattern.getPattern());
      if (properties.isEnabled()) {
        String[] roles = subPattern.getAllowedRoles()
                .stream()
                .map(RoleModel::getRole)
                .toArray(String[]::new);
        if (roles.length > 0) {
          authorizedUrl.hasAnyRole(roles);
        } else {
          authorizedUrl.authenticated();
        }
      } else {
        authorizedUrl.permitAll();
      }
    }

    authorizeRequests.and()
            .httpBasic()
            .realmName("OpenAPI")
            .and();

  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizeRequests = http.csrf()
            .disable()
            .antMatcher(properties.getMainPattern())
            .authorizeRequests();

    for (PatternModel subPattern : properties.getSubPatterns()) {
      ExpressionUrlAuthorizationConfigurer<HttpSecurity>.AuthorizedUrl authorizedUrl = authorizeRequests.antMatchers(subPattern.getPattern());
      if (properties.isEnabled()) {
        String[] roles = subPattern.getAllowedRoles()
                .stream()
                .map(RoleModel::getRole)
                .toArray(String[]::new);
        if (roles.length > 0) {
          authorizedUrl.hasAnyRole(roles);
        } else {
          authorizedUrl.authenticated();
        }
      } else {
        authorizedUrl.permitAll();
      }
    }

    authorizeRequests.and()
            .httpBasic()
            .realmName("OpenAPI")
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // http.csrf()
    // .disable()
    // // .authorizeRequests()
    //
    // .antMatcher("/api/**")
    // .authorizeRequests()
    // .antMatchers("/api/**")
    // .hasRole("DCP")
    // .and()
    // .httpBasic()
    // .realmName("OpenAPI")
    // .and()
    // .sessionManagement()
    // .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry
    // authorizeRequests = http.csrf()
    // .disable()
    // .authorizeRequests();
    //
    // authorizeRequests.antMatchers("/open/**")
    // .hasAnyRole("DCP");
    //
    // authorizeRequests.and()
    // .httpBasic()
    // .realmName("OpenAPI")
    // .and()
    // .sessionManagement()
    // .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

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
