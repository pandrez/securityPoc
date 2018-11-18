package io.mb.sellercenter.security.auth.basicauth;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import io.mb.sellercenter.security.auth.basicauth.model.PatternModel;
import io.mb.sellercenter.security.auth.basicauth.model.UserModel;
import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "security.basicAuths.openApi")
@Getter
@Setter
public class OpenApiBasicAuthenticationSecurityProperties {

  private boolean enabled;
  private String mainPattern;
  private List<PatternModel> subPatterns = new ArrayList<>();
  private List<UserModel> users = new ArrayList<>();

}
