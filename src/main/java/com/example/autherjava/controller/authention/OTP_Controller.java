package com.example.autherjava.controller.authention;

import com.example.autherjava.utils.respon.ResponOTP;
import com.example.autherjava.utils.respon.ResponToken;
import com.example.autherjava.service.OTP.OTP_Service;
import com.example.autherjava.utils.constants.ControllerConstant;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class OTP_Controller {
    /*
     * Limit req
     * */
    private final Bucket bucket;
    public OTP_Controller() {
        Bandwidth limit = Bandwidth.classic(5, Refill.greedy(5, Duration.ofMinutes(1)));
        this.bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
    }
    @Autowired
    OTP_Service otp_service ;
    @GetMapping(ControllerConstant.OTP.LOGIN)
    public ResponseEntity<?> verify_login(@RequestParam int otp)
    {
        if (bucket.tryConsume(1)) {
            return new ResponseEntity<ResponToken>(otp_service.verify_login(otp), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(new ResponOTP( "Ban qua 5 req"));
    }
    @GetMapping(ControllerConstant.OTP.REGISTER)
    public ResponseEntity<?> verify_login(@RequestParam String username,
                                          @RequestParam int otp){
        return  new ResponseEntity<ResponOTP>(otp_service.verify_register(username,otp),HttpStatus.OK);
    }
}
