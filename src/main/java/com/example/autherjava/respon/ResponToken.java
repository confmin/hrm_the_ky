package com.example.autherjava.respon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponToken {
    private Boolean status ;
    private String access_token ;

}
