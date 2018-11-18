package io.mb.sellercenter.security.auth.basicauth.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModel {
  private String username;
  private String password;
  private List<RoleModel> roles = new ArrayList<>();
}
