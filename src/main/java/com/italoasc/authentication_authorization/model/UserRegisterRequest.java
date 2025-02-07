package com.italoasc.authentication_authorization.model;

import com.italoasc.authentication_authorization.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {
    private String username;

    private String password;
    private Set<Role> roles = new HashSet<>();

    private String email;
    private boolean mfaEnabled;
    private String mfaType;
}
