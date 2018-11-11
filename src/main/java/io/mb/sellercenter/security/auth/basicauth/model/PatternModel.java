package io.mb.sellercenter.security.auth.basicauth.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PatternModel {
    private String pattern;
    private List<RoleModel> allowedRoles = new ArrayList<>();
}
