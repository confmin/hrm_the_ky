package com.example.autherjava.model.dto;

import com.example.autherjava.model.entity.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id ;
    private  String name ;
    private Status status ;
    private LocalDateTime time ;
}
