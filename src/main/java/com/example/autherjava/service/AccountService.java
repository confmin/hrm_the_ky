package com.example.autherjava.service;

import com.example.autherjava.model.entity.Account;
import com.example.autherjava.model.in.AccountIn;
import com.example.autherjava.model.in.RoleIn;
import com.example.autherjava.respon.ResponAccount;
import com.example.autherjava.respon.ResponOTP;
import com.example.autherjava.respon.ResponToken;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URISyntaxException;
import java.util.Map;
@Service
public interface AccountService extends UserDetailsService , UserDetails {
    ResponAccount register(AccountIn accountIn) throws URISyntaxException;
    ResponAccount logout(HttpServletRequest request, HttpServletResponse response);
    ResponAccount login(AccountIn accountIn);
    Boolean doPasswordsMatch(String rawPwd, String encode);
    ResponToken verify_login(int otp);
    ResponOTP verify_register(String username ,int otp);
    Boolean check_real_mail(String email) throws URISyntaxException;


}
