package com.umg.absolwentbackend.controllers;


import com.umg.absolwentbackend.Constants;
import com.umg.absolwentbackend.models.Graduate;
import com.umg.absolwentbackend.models.Group;
import com.umg.absolwentbackend.repositories.GraduateRepository;
import com.umg.absolwentbackend.services.EmailService;
import com.umg.absolwentbackend.services.GraduateService;
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
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/survey"/*Do uzupełnienia?*/)
public class QuestionnarieController {

    @Autowired
    private EmailService emailSender;
    @Autowired
    private GraduateRepository graduateRepository;

    @Autowired
    private GraduateService graduateService;

    //To powino się wykonywać aoutomatycznie, ale narazie jest na żądanie
    @PostMapping("/sendsurvey")
    public ResponseEntity<Map<String,Object>> sendMail(@RequestBody Map<String, Object> graduateMap) {
        String groupName=(String)graduateMap.get("groupName");
        List<Map<String, Object>> graduateEmails = graduateRepository.findGroupEmails(groupName);
        List<Map<String, Object>> group = null;
        try{
            group = graduateService.validateGroup(groupName);
        }catch (Exception e){
            Map<String,Object> map = new HashMap<>();
            map.put("success", false);
            map.put("message", e.getMessage());
            return  new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }
        String token = generateSurveyToken(group);
        String title = "Ankieta dla UMG";
        String body = "Link do ankiety";//Tu powinien być link z tokenem do ankiety

        //Wysyłanie emaila
        for (Map<String, Object> emailMap : graduateEmails){
            try {
                System.out.println(emailMap.get("email").toString());
                emailSender.sendEmail(emailMap.get("email").toString()/* Sprawdzić czy to działa */, title, body);

            } catch (Exception e) {
                Map<String, Object> map = new HashMap<>();
                map.put("success", false);
                map.put("status", 500);
                return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("success", false);
        map.put("status", 500);
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String generateSurveyToken(Group group){
        long timestamp = System.currentTimeMillis();
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(Constants.API_SECRET_KEY));
        String token = Jwts.builder().signWith(key)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
                .claim("groupName", group.getGroupName())
                .compact();
        return token;
    }
}
