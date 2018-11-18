package io.mb.sellercenter.security.auth.basicauth.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractBasicAuthenticationModel {

  private boolean enabled;
  private String mainPattern;
  private List<PatternModel> subPatterns = new ArrayList<>();
  private List<UserModel> users = new ArrayList<>();
}
