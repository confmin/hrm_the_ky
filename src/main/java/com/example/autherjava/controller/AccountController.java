package com.example.autherjava.controller;

import com.example.autherjava.model.in.AccountIn;
import com.example.autherjava.respon.ResponAccount;
import com.example.autherjava.respon.ResponOTP;
import com.example.autherjava.respon.ResponToken;
import com.example.autherjava.service.AccountService;

import com.example.autherjava.service.Imp.Mail;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class AccountController {
    private final Bucket bucket;
    public AccountController() {
        Bandwidth limit = Bandwidth.classic(5, Refill.greedy(5, Duration.ofMinutes(1)));
        this.bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
    }
    @Autowired
    AccountService accountService ;




    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AccountIn accountIn) throws URISyntaxException {
        return  new ResponseEntity<ResponAccount>(accountService.register(accountIn), HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AccountIn accountIn)
    {
        return new ResponseEntity<ResponAccount>(accountService.login(accountIn),HttpStatus.OK);
    }
    @GetMapping("/test")

    public ResponAccount test()
    {
        return new ResponAccount(true,"aa",null);
    }
    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request , HttpServletResponse response)
    {
        return new ResponseEntity<ResponAccount>(accountService.logout(request, response),HttpStatus.OK);
    }
    @GetMapping("/verify_login")
    public  ResponseEntity<?> verify_login(@RequestParam int otp)
    {
        if (bucket.tryConsume(1)) {
            return new ResponseEntity<ResponToken>(accountService.verify_login(otp), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(new ResponOTP( "Ban qua 5 req"));
    }
    @GetMapping("verify_register")
    public ResponseEntity<?> verify_login(@RequestParam String username,
                                          @RequestParam int otp){
        return  new ResponseEntity<ResponOTP>(accountService.verify_register(username,otp),HttpStatus.OK);
    }
//    @GetMapping("/mail")
//    public  ResponseEntity<?> checkmail(@RequestParam String mail) throws URISyntaxException {
//
//        return  new ResponseEntity<Mail>(checkmail.check(mail),HttpStatus.OK);
//    }
}
