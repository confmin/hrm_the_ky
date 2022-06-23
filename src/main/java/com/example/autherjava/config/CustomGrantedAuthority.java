package com.example.autherjava.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
@Slf4j
public class CustomGrantedAuthority implements GrantedAuthority {
    private String roles;
    private Collection<String> permissions;
    @Override
    public String getAuthority() {
        return this.roles;
    }

}
