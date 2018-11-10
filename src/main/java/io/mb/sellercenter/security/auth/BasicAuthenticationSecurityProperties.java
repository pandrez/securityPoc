package io.mb.sellercenter.security.auth;

import java.util.Map;

import io.mb.sellercenter.security.auth.model.BasicAuthenticationModel;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "security")
@Getter
@Setter
public class BasicAuthenticationSecurityProperties {

  private Map<String, BasicAuthenticationModel> basicAuths;

}
