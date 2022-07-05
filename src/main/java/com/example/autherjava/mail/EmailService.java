package com.example.autherjava.mail;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    String sendSimpleMail(EmailDetails details);
    String sendMailWithAttachment(EmailDetails details);
}
