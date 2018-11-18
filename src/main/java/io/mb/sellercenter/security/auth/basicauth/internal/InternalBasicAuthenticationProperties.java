package io.mb.sellercenter.security.auth.basicauth.internal;

import java.util.ArrayList;
import java.util.List;

import io.mb.sellercenter.security.auth.basicauth.model.AbstractBasicAuthenticationModel;
import io.mb.sellercenter.security.auth.basicauth.model.PatternModel;
import io.mb.sellercenter.security.auth.basicauth.model.UserModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security.basicAuths.internalApi")
public class InternalBasicAuthenticationProperties extends AbstractBasicAuthenticationModel {

}
