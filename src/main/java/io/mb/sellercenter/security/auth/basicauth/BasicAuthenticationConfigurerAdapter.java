package io.mb.sellercenter.security.auth.basicauth;

import io.mb.sellercenter.security.auth.basicauth.model.BasicAuthenticationModel;
import io.mb.sellercenter.security.auth.basicauth.model.PatternModel;
import io.mb.sellercenter.security.auth.basicauth.model.RoleModel;
import io.mb.sellercenter.security.auth.basicauth.model.UserModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;

@Configuration
@Order(3)
public class BasicAuthenticationConfigurerAdapter extends WebSecurityConfigurerAdapter {

    public static final int PASSWORD_HASH_STRENGTH = 4;
    private final BasicAuthenticationSecurityProperties properties;

    public BasicAuthenticationConfigurerAdapter(BasicAuthenticationSecurityProperties properties) {
        this.properties = properties;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> inMemoryAuthentication = auth.inMemoryAuthentication();
        Collection<BasicAuthenticationModel> basicAuths = properties.getBasicAuths().values();
        for (BasicAuthenticationModel basicAuth : basicAuths) {
            if (basicAuth.isEnabled()) {
                for (UserModel user : basicAuth.getUsers()) {
                    inMemoryAuthentication
                            .passwordEncoder(encoder())
                            .withUser(user.getUsername()).password(user.getPassword()).roles(user.getRole());
//                    inMemoryAuthentication.withUser(user.getUsername()).password(user.getPassword()).roles(user.getRole());
                }
            }
        }
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizeRequests = http.csrf().disable().authorizeRequests();

        for (String apiName : properties.getBasicAuths().keySet()) {
            BasicAuthenticationModel basicAuth = properties.getBasicAuths().get(apiName);
            for (PatternModel pattern : basicAuth.getPatterns()) {
                ExpressionUrlAuthorizationConfigurer<HttpSecurity>.AuthorizedUrl authorizedUrl = authorizeRequests.antMatchers(pattern.getPattern());
                if (!basicAuth.isEnabled()) {
                    authorizedUrl.permitAll();
                } else {
                    if (!pattern.getAllowedRoles().isEmpty()) {
                        authorizedUrl.hasAnyRole(pattern.getAllowedRoles().stream().map(RoleModel::getRole).toArray(String[]::new));
                    } else {
                        authorizedUrl.authenticated();
                    }
                }
            }
        }
        authorizeRequests.and().formLogin().disable().logout().disable();
        authorizeRequests.and().httpBasic().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


//            .a

//    Set<String> apiNames = properties.getBasicAuths()
//        .keySet();
//    for (String apiName : apiNames) {
//      BasicAuthenticationModel basicAuthenticationModel = properties.getBasicAuths()
//          .get(apiName);
//
//      http.csrf()
//          .disable()
//          .authenticationProvider(new BasicAuthenticationProvider(basicAuthenticationModel.getUsername(), basicAuthenticationModel.getPassword(),
//              basicAuthenticationModel.getRole()))
//          .authorizeRequests()
//          .antMatchers(basicAuthenticationModel.getPattern())
//          .hasRole(basicAuthenticationModel.getRole())
//          .and()
//          .httpBasic()
//          .realmName(apiName)
//          // .authenticationEntryPoint(getBasicAuthEntryPoint())
//          .and()
//          .sessionManagement()
//          .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//    }
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(PASSWORD_HASH_STRENGTH);
    }
}
