package com.example.autherjava.service.Imp;

import com.example.autherjava.config.MyUserPrincipal;
import com.example.autherjava.jwt.JwtUtility;
import com.example.autherjava.mail.EmailDetails;
import com.example.autherjava.mail.EmailService;
import com.example.autherjava.mapper.AccountMapper;
import com.example.autherjava.model.entity.Account;
import com.example.autherjava.model.entity.Permission;
import com.example.autherjava.model.entity.Role;
import com.example.autherjava.model.in.AccountIn;

import com.example.autherjava.repository.AccountRepository;
import com.example.autherjava.repository.PermissionRepository;
import com.example.autherjava.repository.RoleRepository;
import com.example.autherjava.respon.ResponAccount;
import com.example.autherjava.respon.ResponOTP;
import com.example.autherjava.respon.ResponToken;
import com.example.autherjava.service.AccountService;
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

    private Account account  ;
    final String SUCCESS = "OTP chinh xac";
    final String FAIL = "OTP sai vui long thu lai";

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
    @Autowired
    OTPImp otpImp ;
    @Autowired
    EmailService emailService ;


    @Override
    public ResponAccount register(AccountIn accountIn) throws URISyntaxException {
        Boolean checkmail = accountRepository.existsAccountByEmail(accountIn.getEmail());
        Boolean checkuser = accountRepository.existsAccountByUsername(accountIn.getUsername());
        AccountMapper accountMapper = new AccountMapper();
        Account account = accountMapper.register(accountIn);
        log.info("checkmail : "+check_real_mail(accountIn.getEmail()));
        if ( checkmail)
        {
            return new ResponAccount(false,"Mail da ton tai trong he thong",null);
        }
        else if (checkuser)
        {
            return new ResponAccount(false,"tai khoan da ton tai",null);
        }
        else if (check_real_mail(accountIn.getEmail()))
        {
            return new ResponAccount(false,"Gmail nay khong ton tai",null);
        }
        else {

            Role role = roleRepository.findByName("ROLE_ADMIN");
                roleRepository.save(role);
            account.getRoles().add(role);
            Permission permission = permissionRepository.findByName("STATUS.UPDATE");
            if(permission == null) {
                permissionRepository.save(permission);
                role.getPermissions().add(permission);
                account.getPermissions().add(permission);
            }

            account.setEnable(false);
            String otp = String.valueOf(otpImp.generateOTP(account.getUsername()));
            account.setVerify_code(otp);
            EmailDetails details = new EmailDetails(account.getEmail(), "Ma xac thuc cua ban la"+otp, "Xac thuc OTP", null);
            emailService.sendSimpleMail(details);
            accountRepository.save(account);
        }
        return new ResponAccount(true, "dang ky thanh cong", null);
    }

    @Override
    public ResponAccount logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {

            String username = auth.getName();
            otpImp.clearOTP(username);
            account.setEnable(false);
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return new ResponAccount(true,"logout thanh cong",null);
    }

    @Override
    public ResponAccount login(AccountIn accountIn) {

        Account account =accountRepository.findByEmail(accountIn.getEmail());

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
                    new UsernamePasswordAuthenticationToken(accountIn.getEmail()
                            , accountIn.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
//            String jwt = jwtUtility.generateJwtToken(accountIn.getEmail());
            String otp = String.valueOf(otpImp.generateOTP(authentication.getName()));
            account.setVerify_code(otp);
            accountRepository.save(account);
            return new ResponAccount(true, "Tai khoan va mat khau chinh xac! Vui long xac thuc otp", null);
        }
    }

    @Override
    public Boolean doPasswordsMatch(String rawPwd, String encode) {

        return passwordEcorder.matches(rawPwd, encode);
    }

    @Override
    public ResponToken verify_login(int otp) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account account = accountRepository.findAccountByUsername(username);
        if(otp >= 0){
            int serverOtp = otpImp.getOTP(username);
            if(serverOtp > 0){
                if(otp == serverOtp){
                    String jwt = jwtUtility.generateJwtToken(account.getEmail());
                    otpImp.clearOTP(username);
                    return new ResponToken(true,jwt);
                }
                else {

                    return new ResponToken(false,FAIL);
                }
            }else {
                return new ResponToken(false,FAIL);
            }
        }else {
            return new ResponToken(false,FAIL);
        }

    }

    @Override
    public ResponOTP verify_register(String username,int otp) {
       Account account = accountRepository.findByUsername(username);
        if(otp >= 0){
            int serverOtp = otpImp.getOTP(username);
            if(serverOtp > 0){
                if(otp == serverOtp){
                    account.setEnable(true);
                    accountRepository.save(account);
                    otpImp.clearOTP(username);
                    return new ResponOTP(SUCCESS);
                }
                else {
                    return new  ResponOTP(FAIL);
                }
            }else {
                return new  ResponOTP(FAIL);
            }
        }else {
            return new  ResponOTP(FAIL);
        }
    }

    @Override
    public Boolean check_real_mail(String email) throws URISyntaxException {
        String url = "https://api.emailable.com/v1/verify?email="+email+"&api_key=live_e9a60fff95225496068d";
        URI uri = new URI(url);
        RestTemplate restTemplate = new RestTemplate();
        Mail response = restTemplate.getForObject(uri,Mail.class);
        String haha = response.getState();
        String hihi = "deliverable";
        if (Objects.equals(haha, hihi))
        {
            return false ;
        }
        else {
            return true;
        }

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
            log.info("enable : "+account.isEnable());

        }
        return  new MyUserPrincipal(account);
    }


}

