package com.umg.absolwentbackend.controllers;

import com.umg.absolwentbackend.Constants;
import com.umg.absolwentbackend.services.EmailService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.crypto.SecretKey;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class sendMails extends Thread{

    List<Map<String, Object>> graduateEmails;
    Integer validDays;

    @Autowired
    private EmailService emailSender;

    public sendMails(List<Map<String, Object>> graduateEmails,Integer validDays) {
        this.graduateEmails = graduateEmails;
        this.validDays = validDays;
    }

    @Override
    public void run() {
        for (Map<String, Object> emailMap : graduateEmails){
            String token = generateSurveyToken(emailMap.get("email").toString(),emailMap.get("field").toString(),emailMap.get("faculty").toString(),emailMap.get("title").toString(), (Integer) emailMap.get("graduation_year"), (String) emailMap.get("gender"),validDays);

            try {
                Constants.EMAIL_BODY += Constants.SURVEY_LINK+"?token="+token;
                emailSender.sendEmail(emailMap.get("email").toString(), Constants.EMAIL_TITLE, Constants.EMAIL_BODY);
                Constants.EMAIL_BODY = "";

            } catch (Exception e) {
                Map<String, Object> map = new HashMap<>();
                map.put("success", false);
                map.put("status", 500);
            }
        }
    }

    private String generateSurveyToken(String email,String field,String faculty,String title,Integer graduation_year,String gender,Integer validDays){
        long timestamp = System.currentTimeMillis();
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(Constants.API_SECRET_KEY));
        String token = Jwts.builder().signWith(key)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + (Constants.SURVEY_TOKEN_VALIDITY * validDays)))
                .claim("email", email)
                .claim("field", field)
                .claim("faculty", faculty)
                .claim("title", title)
                .claim("graduationYear", graduation_year)
                .claim("gender", gender)
                .compact();
        return token;
    }


}
