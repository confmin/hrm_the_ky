package com.example.autherjava.service;

import com.example.autherjava.mapper.AccountMapper;
import com.example.autherjava.model.entity.Account;
import com.example.autherjava.model.entity.Role;
import com.example.autherjava.model.in.AccountIn;
import com.example.autherjava.repository.AccountRepository;
import com.example.autherjava.repository.RoleRepository;
import com.example.autherjava.respon.ResponAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Component
public class AccountServiceImp implements AccountService{
    @Autowired
    AccountRepository accountRepository ;

    @Autowired
    RoleRepository roleRepository;
    @Override
    public ResponAccount register(AccountIn accountIn) {
        Account account = AccountMapper.register(accountIn);
        Role role = roleRepository.findByName("admin");
        account.setRoles(Collections.singleton(role));
        accountRepository.save(account);
        return new ResponAccount(true,"done",account);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
