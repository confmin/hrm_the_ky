package com.example.autherjava.service.account;

import com.example.autherjava.config.MyUserPrincipal;
import com.example.autherjava.domain.mail.EmailDetails;
import com.example.autherjava.domain.mail.EmailService;
import com.example.autherjava.model.mapper.AccountMapper;
import com.example.autherjava.model.entity.Account;
import com.example.autherjava.model.in.AccountIn;

import com.example.autherjava.repository.AccountRepository;
import com.example.autherjava.repository.PermissionRepository;
import com.example.autherjava.utils.respon.ResponAccount;
import com.example.autherjava.domain.mail.API_Mail;
import com.example.autherjava.service.OTP.OTP_Service;
import com.example.autherjava.utils.constants.ServiceConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@Transactional @Slf4j
@Component
public class AccountServiceImp implements AccountService {
    private static BCryptPasswordEncoder passwordEcorder = new BCryptPasswordEncoder();
    @Autowired
    AccountRepository accountRepository;
   @Autowired
    AuthenticationManager authenticationManager ;
    @Autowired
    PermissionRepository permissionRepository ;
    @Autowired
    OTP_Service otp_service ;
    @Autowired
    EmailService emailService ;


    @Override
    public ResponAccount register(AccountIn accountIn) throws URISyntaxException {
        Boolean check_mail = accountRepository.existsAccountByEmail(accountIn.getEmail());
        Boolean check_user = accountRepository.existsAccountByUsername(accountIn.getUsername());
        AccountMapper accountMapper = new AccountMapper();
        Account account = accountMapper.register(accountIn);
        if ( check_mail)
        {
            return new ResponAccount(false,ServiceConstant.Account.EMAIL_ALREADY_EXIST,null);
        }
        else if (check_user)
        {
            return new ResponAccount(false,ServiceConstant.Account.USER_EXIST,null);
        }
        else if (check_real_mail(accountIn.getEmail()))
        {
            return new ResponAccount(false,ServiceConstant.Account.EMAIL_REAL_NOT_EXIST,null);
        }
        else {
            account.setEnable(false);
            String otp = String.valueOf(otp_service.generateOTP(account.getUsername()));
            account.setVerify_code(otp);
            EmailDetails details = new EmailDetails(account.getEmail(), ServiceConstant.Mail.BODY+otp, ServiceConstant.Mail.SUBJECT, null);
            emailService.sendSimpleMail(details);
            accountRepository.save(account);
        }
        return new ResponAccount(true, ServiceConstant.Account.REGISTER_SUCCESS, null);
    }

    @Override
    public ResponAccount logout(HttpServletRequest request, HttpServletResponse response) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {

            String username = auth.getName();
            otp_service.clearOTP(username);
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return new ResponAccount(true,ServiceConstant.Account.LOGOUT_SUCCESS,null);
    }

    @Override
    public ResponAccount login(AccountIn accountIn) {

        Account account =accountRepository.findByEmail(accountIn.getEmail());
        MyUserPrincipal check_enable = new MyUserPrincipal(account);
        Boolean check_email = accountRepository.existsAccountByEmail(accountIn.getEmail());
        String PwdEncode = accountRepository.findByPwd(accountIn.getEmail());
        if (!check_email) {
            return new ResponAccount(false, ServiceConstant.Account.USER_NOT_EXIST,null);
        }
       else if (!doPasswordsMatch(accountIn.getPassword(),PwdEncode))
        {
            return  new ResponAccount(false,ServiceConstant.Account.PASSWORD_INCORRECT,null);
        }
       else if (!check_enable.isEnabled())
        {
            return  new ResponAccount(false,ServiceConstant.Account.USER_NOT_ENABLED,null);
        }
        else {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(accountIn.getEmail()
                            ,accountIn.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String otp = String.valueOf(otp_service.generateOTP(authentication.getName()));
            account.setVerify_code(otp);
            EmailDetails details = new EmailDetails(account.getEmail(), ServiceConstant.Mail.BODY.concat(otp), ServiceConstant.Mail.SUBJECT, null);
            emailService.sendSimpleMail(details);
            accountRepository.save(account);
            return new ResponAccount(true, ServiceConstant.OTP.INPUT, null);
        }
    }
    @Override
    public Boolean doPasswordsMatch(String rawPwd, String encode) {

        return passwordEcorder.matches(rawPwd, encode);
    }

    @Override
    public Boolean check_real_mail(String email) throws URISyntaxException {
        String api_key = "live_e9a60fff95225496068d";
        String url = "https://api.emailable.com/v1/verify?email="+email+"&api_key="+api_key+"";
        URI uri = new URI(url);
        RestTemplate restTemplate = new RestTemplate();
        API_Mail response = restTemplate.getForObject(uri, API_Mail.class);
        String deliverable_api = response.getState();
        if (Objects.equals(deliverable_api, "deliverable"))
        {
            return false ;
        }
            return true;
    }

}

