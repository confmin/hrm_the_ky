package com.example.autherjava.controller.authention;

import com.example.autherjava.model.in.AccountIn;
import com.example.autherjava.utils.respon.ResponAccount;
import com.example.autherjava.service.account.AccountService;

import com.example.autherjava.utils.constants.ControllerConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URISyntaxException;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class AccountController {

    @Autowired
    AccountService accountService ;
    @PostMapping(ControllerConstant.AUTHENTION.REGISTER)
    public ResponseEntity<?> register(@RequestBody AccountIn accountIn) throws URISyntaxException {
        return  new ResponseEntity<ResponAccount>(accountService.register(accountIn), HttpStatus.OK);
    }
    @PostMapping(ControllerConstant.AUTHENTION.LOGIN)
    public ResponseEntity<?> login(@RequestBody AccountIn accountIn)
    {
        return new ResponseEntity<ResponAccount>(accountService.login(accountIn),HttpStatus.OK);
    }
    @GetMapping(ControllerConstant.AUTHENTION.LOGOUT)
    public ResponseEntity<?> logout(HttpServletRequest request , HttpServletResponse response)
    {
        return new ResponseEntity<ResponAccount>(accountService.logout(request, response),HttpStatus.OK);
    }

}
