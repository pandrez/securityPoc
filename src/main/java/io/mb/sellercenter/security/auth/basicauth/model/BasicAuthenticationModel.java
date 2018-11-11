package io.mb.sellercenter.security.auth.basicauth.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BasicAuthenticationModel {

  private boolean enabled;
  private List<PatternModel> patterns = new ArrayList<>();
  private List<UserModel> users = new ArrayList<>();


}
