package com.umg.absolwentbackend.controllers;



import com.umg.absolwentbackend.exceptions.AuthenticationException;
import com.umg.absolwentbackend.Constants;
import com.umg.absolwentbackend.models.University;
import com.umg.absolwentbackend.services.UniveristyService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/admin")
public class loginController {

    @Autowired
    UniveristyService universityService;

    @PostMapping("")
    public ResponseEntity<Map<String,Object>> login(@RequestBody Map<String, Object> userMap) {

        String name = (String) userMap.get("username");
        String password = (String) userMap.get("password");
        University uni = null;
        System.out.println("TEST");
        try{
            uni = universityService.validateUniversity(name,password);
        }
        catch (AuthenticationException e){
            Map<String,Object> map = new HashMap<>();
            map.put("success", false);
            map.put("message", e.getMessage());
            return  new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("success", true);
        map.put("token", generateToken(uni));
        return  new ResponseEntity<>(map, HttpStatus.OK);
    }

    private String generateToken(University university){
        long timestamp = System.currentTimeMillis();
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(Constants.API_SECRET_KEY));
        String token = Jwts.builder().signWith(key)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
                .claim("login", university.getLogin())
                .claim("password", university.getPassword())
                .claim("frequency", university.getQuestionnaireFrequency())
                .compact();
        return token;
    }
}
