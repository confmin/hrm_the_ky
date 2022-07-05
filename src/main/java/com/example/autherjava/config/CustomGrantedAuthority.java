package com.example.autherjava.config;

import com.example.autherjava.model.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor

public class CustomGrantedAuthority implements GrantedAuthority {
    private String roles;
    private Collection<String> permissions;

    @Override
    public String getAuthority() {
        return this.roles;
    }

}
