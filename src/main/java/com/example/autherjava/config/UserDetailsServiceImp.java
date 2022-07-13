package com.example.autherjava.config;

import com.example.autherjava.model.entity.Account;
import com.example.autherjava.repository.AccountRepository;
import com.example.autherjava.utils.constants.ServiceConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    AccountRepository accountRepository;

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
        return  new MyUserPrincipal(account);
    }
}
