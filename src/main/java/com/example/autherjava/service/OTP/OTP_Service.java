package com.example.autherjava.service.OTP;

import com.example.autherjava.utils.respon.ResponOTP;
import com.example.autherjava.utils.respon.ResponToken;

public interface OTP_Service {
    ResponToken verify_login(int otp);
    ResponOTP verify_register(String username ,int otp);
    Integer generateOTP(String key);
     int getOTP(String key);
     void clearOTP(String key);
}
