package com.example.autherjava.service;

import com.example.autherjava.jwt.JwtFilter;

import com.example.autherjava.jwt.JwtUtility;
import com.example.autherjava.mapper.AccountMapper;
import com.example.autherjava.model.entity.Account;
import com.example.autherjava.model.entity.Role;
import com.example.autherjava.model.in.AccountIn;

import com.example.autherjava.model.in.RoleIn;
import com.example.autherjava.repository.AccountRepository;
import com.example.autherjava.repository.RoleRepository;
import com.example.autherjava.respon.ResponAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.logging.Logger;
@Transactional @Slf4j
@Component
public class AccountServiceImp implements UserDetails,AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JwtUtility jwtUtility  ;
    @Autowired
    AuthenticationManager authenticationManager ;
    @Override
    public ResponAccount register(AccountIn accountIn) {
        Boolean checkmail = accountRepository.existsAccountByEmail(accountIn.getEmail());
        Boolean checkuser = accountRepository.existsAccountByUsername(accountIn.getUsername());
        AccountMapper accountMapper = new AccountMapper();
        Account account = accountMapper.register(accountIn);
        if (checkmail)
        {
            return new ResponAccount(false,"email nay da ton tai",null);
        }
        else if (checkuser)
        {
            return new ResponAccount(false,"tai khoan da ton tai",null);
        }
        else {

            Role role = roleRepository.findByName("hr");
            account.setRoles(Collections.singleton(role));
            accountRepository.save(account);
        }
        return new ResponAccount(true, "dang ky thanh cong", null);
    }

    @Override
    public ResponAccount login(AccountIn accountIn) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(accountIn.getUsername(),accountIn.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtility.generateJwtToken(   authentication.getName());
        return new ResponAccount(true,"dang nhap thanh cong",jwt);
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Account account = accountRepository.findByUsername(username);
//        System.out.println(username+"ccccc");
//        System.out.println(account.getRoles()+"ccc");
//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        Collection<Role> roles = account.getRoles();
//        System.out.println(roles+ "rolse");
//        for (Role role : roles) {
//            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//            System.out.println(role);
//        }
//
//        return new org.springframework.security.core.userdetails.User(
//                account.getUsername(), account.getPassword(), grantedAuthorities);
        Account account = accountRepository.findByUsername(username);
        if (username == null)
        {
            log.error("Nguoi dung khong ton tai");
        }
        else
        {
            log.info("Nguoi dung ton tai :" + username);
        }
        Collection<SimpleGrantedAuthority>authorities = new ArrayList<>();
        account.getRoles().forEach(role -> {authorities.add(new SimpleGrantedAuthority(role.getName()));});
        return  new org.springframework.security.core.userdetails.User(account.getUsername(),account.getPassword(),authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

