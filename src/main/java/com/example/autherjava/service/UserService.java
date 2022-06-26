package com.example.autherjava.service;

import com.example.autherjava.model.dto.UserDto;
import com.example.autherjava.model.entity.User;

import com.example.autherjava.model.in.UserIn;
import com.example.autherjava.respon.ResponPage;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Map;

@Service
public interface UserService {
    List<UserDto> get();
    ResponPage add(UserIn userIn, Integer limit);
    User update(Integer id , UserIn userIn);
    Map<String,Object> delete(Integer id);
    List<User> getbest();
    ResponPage getpage(Integer pageNo , Integer pageSize) ;
    User getuser(Integer id);





}
