package com.example.autherjava.config;

import com.example.autherjava.model.entity.Account;
import com.example.autherjava.model.entity.Permission;
import com.example.autherjava.model.entity.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Slf4j
@Transactional
public class MyUserPrincipal implements UserDetails {
    Account account ;
    public MyUserPrincipal(Account account)
    {
        this.account=account;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (Permission privilege : account.getPermissions()) {
            authorities.add(new SimpleGrantedAuthority(privilege.getName()));
        }
        log.info("permisson"+authorities);
        return authorities;

}

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return account.isEnable();
    }
//    private Collection<? extends GrantedAuthority> getAuthorities(
//            Collection<Role> roles) {
//        return getGrantedAuthorities(getPremission(roles));
//    }
//
//    private List<String> getPremission(Collection<Role> roles) {
//
//        List<String> perrmisson = new ArrayList<>();
//        List<Permission> collection = new ArrayList<>();
//        for (Role role : roles) {
//            perrmisson.add(role.getName());
//            collection.addAll(role.getPermissions());
//        }
//        for (Permission item : collection) {
//            perrmisson.add(item.getName());
//        }
//        return perrmisson;
//    }
//
//    private List<GrantedAuthority> getGrantedAuthorities(List<String> permission) {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (String per : permission) {
//            authorities.add(new SimpleGrantedAuthority(per));
//        }
//        log.info("quyen"+authorities);
//        return authorities;
//
//    }

}
