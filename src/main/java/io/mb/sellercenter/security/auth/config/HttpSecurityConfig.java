package io.mb.sellercenter.security.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import io.mb.sellercenter.security.auth.basicauth.InternalApiBasicAuthenticationSecurityProperties;
import io.mb.sellercenter.security.auth.basicauth.OpenApiBasicAuthenticationSecurityProperties;
import io.mb.sellercenter.security.auth.basicauth.model.PatternModel;
import io.mb.sellercenter.security.auth.basicauth.model.RoleModel;
import io.mb.sellercenter.security.auth.basicauth.model.UserModel;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class HttpSecurityConfig {

  public static final int PASSWORD_HASH_STRENGTH = 4;

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder(PASSWORD_HASH_STRENGTH);
  }

  @Configuration
  @Order(100)
  public static class OpenApiBasicAuthenticationConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private final OpenApiBasicAuthenticationSecurityProperties properties;
    private final PasswordEncoder passwordEncoder;

    public OpenApiBasicAuthenticationConfigurerAdapter(OpenApiBasicAuthenticationSecurityProperties properties, PasswordEncoder passwordEncoder) {
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


    @Override
    protected void configure(HttpSecurity http) throws Exception {

      ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizeRequests = http.csrf()
          .disable()
          .antMatcher(properties.getMainPattern())
          .authorizeRequests();
      // .antMatchers("/api/open1/**")
      // .hasAnyRole("DCP")
      // .antMatchers("/api/open2/**")
      // .authenticated();

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

  @Configuration
  @Order(101)
  public static class InternalApiBasicAuthenticationConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private final InternalApiBasicAuthenticationSecurityProperties properties;
    private final PasswordEncoder passwordEncoder;

    public InternalApiBasicAuthenticationConfigurerAdapter(InternalApiBasicAuthenticationSecurityProperties properties, PasswordEncoder passwordEncoder) {
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
          .formLogin()
          .and()
          .httpBasic()
          .realmName("InternalAPI");


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
      // .and()
      // .httpBasic()
      // .realmName("InternalAPI");
    }


  }
}
