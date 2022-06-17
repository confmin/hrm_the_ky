package com.example.autherjava.controller;

import com.example.autherjava.model.entity.Account;
import com.example.autherjava.model.in.AccountIn;
import com.example.autherjava.respon.ResponAccount;
import com.example.autherjava.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class AccountController {
    @Autowired
    AccountService accountService ;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AccountIn accountIn)
    {
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
}
