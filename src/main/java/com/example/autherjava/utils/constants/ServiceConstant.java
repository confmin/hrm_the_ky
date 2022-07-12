package com.example.autherjava.utils.constants;

public interface ServiceConstant {
interface OTP {
    String SUCCESS = "OTP chinh xac!" ;
    String FAIL = "OTP khong chinh xac hoac da het han";
    String INPUT ="Tai khoan mat khau chinh xac,OTP da duoc gui ve email";

}
interface Account
{
    String LOGOUT_SUCCESS ="Dang xuat thanh cong";
    String USER_NOT_EXIST = "Tai khoan nay khong ton tai tren he thong";
    String USER_EXIST = "Tai khoan nay da ton tai tren he thong";
    String EMAIL_REAL_NOT_EXIST = "Gmail nay khong chinh xac hoac khong ton tai";
    String PASSWORD_INCORRECT ="Mat khau khong dung";
    String USER_NOT_ENABLED = "Tai khoan nay chua duoc kich hoat";
    String REGISTER_SUCCESS ="Dang ky tai khoan thanh cong";
    String EMAIL_ALREADY_EXIST ="Gmail nay da ton tai trong he thong";
}
interface Mail
{
    String DOMAIN ="CTY_ELEDEVO";
    String SUBJECT = "Ma xac thuc OTP tu ".concat(DOMAIN) ;
    String BODY = "Ma xac thuc cua ban la: ";
}
interface Status {
    String  NAME_EXIST = "Ten trang thai da ton tai ";
    String LEVEL_EXIST ="Uu tien da ton tai";
    String CREATE_SUSSCESS = "Them trang thai thanh cong" ;
    String NAME_AND_LEVEL_EXIST = "Ten hoac trang thai da ton tai";
    String UPDATE_SUCCESS ="Update thanh cong";

}
}

