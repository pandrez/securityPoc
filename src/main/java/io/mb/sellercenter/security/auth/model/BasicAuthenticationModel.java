package io.mb.sellercenter.security.auth.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicAuthenticationModel {

  private boolean enabled;
  private String pattern;
  private String username;
  private String password;
  private String role;

}
