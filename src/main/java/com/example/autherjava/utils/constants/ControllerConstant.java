package com.example.autherjava.utils.constants;

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
        String UPDATE = PATH_STATUS+"update";
        String UPDATE_ALL = PATH_STATUS+"update_all";
    }
}
