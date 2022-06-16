package com.example.autherjava.service;

import com.example.autherjava.jwt.JwtFilter;
import com.example.autherjava.jwt.JwtUtility;
import com.example.autherjava.mapper.AccountMapper;
import com.example.autherjava.model.entity.Account;
import com.example.autherjava.model.entity.Role;
import com.example.autherjava.model.in.AccountIn;
import com.example.autherjava.repository.AccountRepository;
import com.example.autherjava.repository.RoleRepository;
import com.example.autherjava.respon.ResponAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Component
public class AccountServiceImp implements AccountService {


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

            Role role = roleRepository.findByName("admin");
            account.setRoles(Collections.singleton(role));
            accountRepository.save(account);
        }
        return new ResponAccount(true, "dang ky thanh cong", account);

    }

    @Override
    public ResponAccount login(AccountIn accountIn) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(accountIn.getUsername(),
                        accountIn.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtility.generateJwtToken(accountIn.getUsername());
        return new ResponAccount(true,"suss",jwt);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
