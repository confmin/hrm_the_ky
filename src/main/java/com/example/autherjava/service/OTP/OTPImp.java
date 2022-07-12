package com.example.autherjava.service.OTP;


import com.example.autherjava.model.entity.Account;
import com.example.autherjava.repository.AccountRepository;
import com.example.autherjava.utils.respon.ResponOTP;
import com.example.autherjava.utils.respon.ResponToken;
import com.example.autherjava.security.jwt.JwtUtility;
import com.example.autherjava.utils.constants.ServiceConstant;
import com.google.common.cache.CacheBuilder;

import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.google.common.cache.CacheLoader;
import java.util.Random;
import java.util.concurrent.TimeUnit;


@Service
public class OTPImp implements OTP_Service {
    @Autowired
    AccountRepository accountRepository ;
    @Autowired
    JwtUtility jwtUtility ;
    private static final Integer EXPIRE_MINS = 5;
    private LoadingCache otpCache;
    public OTPImp(){
        super();
        otpCache = CacheBuilder.newBuilder().
                expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES)
                .build(new CacheLoader() {
                    @Override
                    public Object load(Object o) throws Exception {
                        return null;
                    }

                    public Integer load(String key) {
                        return 0;
                    }
                });
    }

    @Override
    public ResponToken verify_login(int otp) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account account = accountRepository.findAccountByUsername(username);
        if(otp >= 0){
                int serverOtp = getOTP(username);
            if(serverOtp > 0){
                if(otp == serverOtp){
                    String jwt = jwtUtility.generateJwtToken(account.getEmail());
                    clearOTP(username);
                    return new ResponToken(true,jwt);
                }
                else {

                    return new ResponToken(false, ServiceConstant.OTP.SUCCESS);
                }
            }else {
                return new ResponToken(false,ServiceConstant.OTP.FAIL);
            }
        }else {
            return new ResponToken(false,ServiceConstant.OTP.FAIL);
        }

    }

    @Override
    public ResponOTP verify_register(String username, int otp) {
        Account account = accountRepository.findByUsername(username);
        if(otp >= 0){
            int serverOtp = getOTP(username);
            if(serverOtp > 0){
                if(otp == serverOtp){
                    account.setEnable(true);
                    accountRepository.save(account);
                    clearOTP(username);
                    return new ResponOTP(ServiceConstant.OTP.SUCCESS);
                }
                else {
                    return new  ResponOTP(ServiceConstant.OTP.FAIL);
                }
            }else {
                return new  ResponOTP(ServiceConstant.OTP.FAIL);
            }
        }else {
            return new  ResponOTP(ServiceConstant.OTP.FAIL);
        }
    }
@Override
    public Integer generateOTP(String key) {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        otpCache.put(key, otp);
        return otp;
    }
@Override
    public int getOTP(String key) {
        try{
            return (int) otpCache.get(key);
        }catch (Exception e){
            return 0;
        }
    }

@Override
    public void clearOTP(String key) {
        otpCache.invalidate(key);
    }
}
