package com.example.autherjava.model.in;

import com.example.autherjava.model.entity.Role;
import lombok.Data;

import java.util.Collection;

@Data
public class AccountIn {
    private String username ;
    private String email ;
    private String password ;
    private String authorities ;
}
