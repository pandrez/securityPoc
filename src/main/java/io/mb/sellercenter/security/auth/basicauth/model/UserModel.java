package io.mb.sellercenter.security.auth.basicauth.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModel {
    private String username;
    private String password;
    private String role;
}
