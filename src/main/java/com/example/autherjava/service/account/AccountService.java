package com.example.autherjava.service.account;

import com.example.autherjava.model.in.AccountIn;
import com.example.autherjava.utils.respon.ResponAccount;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URISyntaxException;

@Service
public interface AccountService  {
    ResponAccount register(AccountIn accountIn) throws URISyntaxException;
    ResponAccount logout(HttpServletRequest request, HttpServletResponse response);
    ResponAccount login(AccountIn accountIn);
    Boolean doPasswordsMatch(String rawPwd, String encode);
    Boolean check_real_mail(String email) throws URISyntaxException;


}
