package com.example.autherjava.mapper;

import com.example.autherjava.model.dto.UserDto;
import com.example.autherjava.model.entity.User;

import com.example.autherjava.model.in.UserIn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Time;
import java.time.LocalTime;

public class UserMapper {
    public static UserDto map(User user)
    {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setStatus(user.getStatus());
        userDto.setTime(user.getTime());
        return userDto ;
    }
    public static User map(UserIn userIn)
    {
        User user = new User();
        user.setName(userIn.getName());
//        user.setStatus(userIn.getStatus());
        return user ;
    }


}
