package com.umg.absolwentbackend.controllers;



import com.umg.absolwentbackend.exceptions.AuthenticationException;
import com.umg.absolwentbackend.Constants;
import com.umg.absolwentbackend.models.University;
import com.umg.absolwentbackend.services.UniveristyService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    UniveristyService universityService;

    @PostMapping("/admin")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, Object> userMap) {

        String name = (String) userMap.get("login");
        String password = (String) userMap.get("password");
        University uni = null;
        try {
            uni = universityService.validateUniversity(name, password);
        } catch (AuthenticationException e) {
            Map<String, Object> map = new HashMap<>();
            map.put("success", false);
            map.put("message", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("id",1);
        map.put("login",uni.getLogin());
        map.put("success", true);
        map.put("token", generateToken(uni));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    private String generateToken(University university) {
        long timestamp = System.currentTimeMillis();
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(Constants.API_SECRET_KEY));
        String token = Jwts.builder().signWith(key)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
                .claim("login", university.getLogin())
                .claim("admin", true)
                .compact();
        return token;
    }

    @PostMapping("/survey")
    public ResponseEntity<Object> authPool(@RequestBody Map<String, Object> userMap) {

        String token = (String) userMap.get("token");
        if(token!= null) {
            try {
                SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(Constants.API_SECRET_KEY));
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token).getBody();
                Map<String, Object> map = new HashMap<>();
                map.put("field",claims.get("field"));
                map.put("faculty",claims.get("faculty"));
                map.put("title",claims.get("title"));
                map.put("gender",claims.get("gender"));
                map.put("year",claims.get("graduationYear"));
                Map<String, Object> map2 = new HashMap<>();
                map2.put("data",map);
                map2.put("success",true);
                return new ResponseEntity<>(map2, HttpStatus.OK);
            }
            catch (Exception e)
            {
                Map<String, Object> map = new HashMap<>();
                map.put("success", false);
                map.put("msg","Token expired");
                return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
            }
        }
        else
        {
            Map<String, Object> map = new HashMap<>();
            map.put("success", false);
            map.put("msg","Token must be provided");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }

    }
}
