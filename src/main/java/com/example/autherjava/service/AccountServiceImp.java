package com.example.autherjava.service;

import com.example.autherjava.jwt.JwtFilter;

import com.example.autherjava.jwt.JwtUtility;
import com.example.autherjava.mapper.AccountMapper;
import com.example.autherjava.model.entity.Account;
import com.example.autherjava.model.entity.Permission;
import com.example.autherjava.model.entity.Role;
import com.example.autherjava.model.in.AccountIn;

import com.example.autherjava.model.in.RoleIn;
import com.example.autherjava.repository.AccountRepository;
import com.example.autherjava.repository.PermissionRepository;
import com.example.autherjava.repository.RoleRepository;
import com.example.autherjava.respon.ResponAccount;
import com.sun.istack.internal.NotNull;
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
public class AccountServiceImp implements AccountService {
    private static BCryptPasswordEncoder passwordEcorder = new BCryptPasswordEncoder();

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JwtUtility jwtUtility  ;
    @Autowired
    AuthenticationManager authenticationManager ;
    @Autowired
    PermissionRepository permissionRepository ;
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

            Role role = roleRepository.findByName("ROLE_ADMIN");

                roleRepository.save(role);
            account.getRoles().add(role);
            Permission permission = permissionRepository.findByName("STATUS.UPDATE");
            if(permission == null) {
                permissionRepository.save(permission);
                role.getPermissions().add(permission);

            }
            accountRepository.save(account);
        }
        return new ResponAccount(true, "dang ky thanh cong", null);
    }

    @Override
    public ResponAccount login(AccountIn accountIn) {

        Boolean checkmail = accountRepository.existsAccountByEmail(accountIn.getEmail());
        String PwdEncode = accountRepository.findByPwd(accountIn.getEmail());
        if (!checkmail) {
            return new ResponAccount(false, "Tai khoan nay khong dung",null);
        }
       else if (!doPasswordsMatch(accountIn.getPassword(),PwdEncode))
        {
            return  new ResponAccount(false,"Mat khau khong dung",null);
        }
        else {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(accountIn.getEmail(), accountIn.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtility.generateJwtToken(accountIn.getEmail());
            return new ResponAccount(true, "dang nhap thanh cong", jwt);
        }
    }

    @Override
    public Boolean doPasswordsMatch(String rawPwd, String encode) {

        return passwordEcorder.matches(rawPwd, encode);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email);
        if (email == null)
        {
            log.error("Nguoi dung khong ton tai");

        }
        else
        {
            log.info("Nguoi dung ton tai :" + email);

        }
        Collection<SimpleGrantedAuthority>authorities = new ArrayList<>();
        account.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));});
        log.info("aa"+authorities);

        return  new org.springframework.security.core.userdetails.
                User(account.getUsername(),account.getPassword(),authorities);
    }


}

