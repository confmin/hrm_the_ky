package com.example.autherjava.utils.constants;

import org.springframework.security.core.parameters.P;

public interface ControllerConstant {
    String PATH_DEFAULT ="/api/v1/";

String PATH_AUTHENTION  = PATH_DEFAULT+"authention/";
    interface AUTHENTION {
        String LOGIN = PATH_AUTHENTION+"login";
        String REGISTER = PATH_AUTHENTION+"register";
        String LOGOUT =PATH_AUTHENTION+"logout";
    }
String PATH_OTP =PATH_DEFAULT+"verify_otp/";
    interface OTP {
        String LOGIN =PATH_OTP+"login";
        String REGISTER =PATH_OTP+"register";
        String FORGOT_PASSWORD = PATH_OTP+"forgot_password";
    }

String PATH_STATUS = PATH_DEFAULT+"status/";
    interface STATUS {
        String GET_ALL = PATH_STATUS+"getall";
        String CREATE = PATH_STATUS+"create";
        String UPDATE = PATH_STATUS+"update/{id}";
        String DELETE = PATH_STATUS+"delete/{id}";
        String UPDATE_ALL = PATH_STATUS+"update_all/{id}";

    }
    String PATH_ROLE = PATH_DEFAULT+"role/";
    interface ROLE {
        String CREATE = PATH_ROLE+"create" ;
        String GET = PATH_ROLE+"get";
        String UPDATE =PATH_ROLE+"update/{id}";
        String DELETE = PATH_ROLE+"delete/{id}";
    }
}
