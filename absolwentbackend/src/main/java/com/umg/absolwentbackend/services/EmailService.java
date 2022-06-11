package com.umg.absolwentbackend.services;

import com.umg.absolwentbackend.models.Graduate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private JavaMailSender javaMailSender;
    private static final String NOREPLY_ADDRESS = "noreply@absolwent.com";

    @Autowired
        public EmailService(JavaMailSender javaMailSender ) {
            this.javaMailSender = javaMailSender;
        }

        @Async
        public void sendEmail(String to, String subject, String text) {
            try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(NOREPLY_ADDRESS);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            javaMailSender.send(message);
            } catch (MailException exception) {
                exception.printStackTrace();
            }
        }
}

