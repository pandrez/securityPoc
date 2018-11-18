package io.mb.sellercenter.security.auth.basicauth.openapi;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import io.mb.sellercenter.security.auth.basicauth.model.AbstractBasicAuthenticationModel;

@Component
@ConfigurationProperties(prefix = "security.basicAuths.openApi")
public class OpenApiBasicAuthenticationProperties extends AbstractBasicAuthenticationModel {

}
