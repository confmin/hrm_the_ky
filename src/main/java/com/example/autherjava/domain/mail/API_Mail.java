package com.example.autherjava.domain.mail;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class API_Mail {
   private boolean accept_all ;
   private Object did_you_mean ;
   private boolean disposable ;
   private String domain ;
   private int duration ;
   private  String email ;
   private  String first_name ;
   private  boolean free ;
   private String full_name;
   private String gender ;
   private String last_name ;
   private String mx_record ;
   private String reason ;
   private boolean role ;
   private int score ;
    private String smtp_provider ;
    private String state ;
    private String tag ;
    private  String user ;
}
