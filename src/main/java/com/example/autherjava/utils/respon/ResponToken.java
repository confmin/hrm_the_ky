package com.example.autherjava.utils.respon;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResponToken {
    private Boolean status ;
    private String access_token ;

}
