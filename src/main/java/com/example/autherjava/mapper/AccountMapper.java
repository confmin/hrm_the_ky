package com.example.autherjava.mapper;

import com.example.autherjava.model.entity.Account;
import com.example.autherjava.model.entity.Role;
import com.example.autherjava.model.in.AccountIn;
import com.example.autherjava.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AccountMapper {

    @Autowired
            static
    RoleRepository roleRepository ;
    public  Account register(AccountIn accountIn)
    {

        Account account = new Account();
        account.setUsername(accountIn.getUsername());
        account.setEmail(accountIn.getEmail());
        String hashpass =new BCryptPasswordEncoder().encode(accountIn.getPassword());
        account.setPassword(hashpass);
//        Role role = roleRepository.findByName("Hr") ;
//        System.out.println("sssssssss" +role);
//        account.setRoles((Set<Role>) role);

        return account ;
    }
    public static Account login(AccountIn accountIn)
    {
        Account account = new Account();
        account.setEmail(accountIn.getEmail());
        account.setPassword(accountIn.getPassword());
        return account ;
    }
}
