package com.example.autherjava.service.Imp;


import com.google.common.cache.CacheBuilder;

import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;
import com.google.common.cache.CacheLoader;
import java.util.Random;
import java.util.concurrent.TimeUnit;
@Service
public class OTPImp {
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

    public Integer generateOTP(String key) {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        otpCache.put(key, otp);
        return otp;
    }

    public int getOTP(String key) {
        try{
            return (int) otpCache.get(key);
        }catch (Exception e){
            return 0;
        }
    }


    public void clearOTP(String key) {
        otpCache.invalidate(key);
    }
}
