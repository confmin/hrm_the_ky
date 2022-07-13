package com.example.autherjava.model.in;

import com.example.autherjava.model.entity.Role;
import lombok.*;

import java.util.Collection;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccountIn {
    private String username ;
    private String email ;
    private String password ;
    private String authorities ;
}
