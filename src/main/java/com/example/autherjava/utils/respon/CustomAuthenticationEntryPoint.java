package com.example.autherjava.utils.respon;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.gson.Gson;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException, ServletException {
        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(401);

        HashMap<String, String> respon = new LinkedHashMap<>();
        respon.put("status","401");
        respon.put("timestamp", String.valueOf(LocalDateTime.now()));
        respon.put("message","Ban khong du quyen de thuc hien");
        String json = new Gson().toJson(respon);
        res.getWriter().write(json);
    }


}