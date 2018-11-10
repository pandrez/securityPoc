package io.mb.sellercenter.security.auth.basicauth;

import java.util.Collections;
import java.util.List;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

public class BasicAuthenticationProvider implements AuthenticationProvider {

  private final String username;
  private final String password;
  private final String role;

  public BasicAuthenticationProvider(String username, String password, String role) {
    this.username = username;
    this.password = password;
    this.role = role;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    final Object receivedUsername = authentication.getPrincipal();
    final Object receivedPassword = authentication.getCredentials();

    if (username.equals(receivedUsername) && password.equals(receivedPassword)) {
      return new UsernamePasswordAuthenticationToken(username, password, createRole(role));
    }

    throw new BadCredentialsException("Invalid Credentials");
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
  }

  private static List<GrantedAuthority> createRole(final String role) {
    return Collections.singletonList((GrantedAuthority) () -> "ROLE_" + role);
  }
}
