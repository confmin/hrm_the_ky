package com.example.autherjava.service;

import com.example.autherjava.model.entity.Account;
import com.example.autherjava.model.in.AccountIn;
import com.example.autherjava.model.in.RoleIn;
import com.example.autherjava.respon.ResponAccount;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public interface AccountService extends UserDetailsService {
    ResponAccount register(AccountIn accountIn) ;

    ResponAccount login(AccountIn accountIn);
    Boolean doPasswordsMatch(String rawPwd, String encode);


}
