package io.mb.sellercenter.security.bum;

import org.springframework.stereotype.Component;
import io.mb.sellercenter.security.auth.BasicAuthenticationSecurityProperties;

@Component
public class Sec {

  private final BasicAuthenticationSecurityProperties basicProps;

  public Sec(BasicAuthenticationSecurityProperties basicProps) {
    this.basicProps = basicProps;
  }


}
