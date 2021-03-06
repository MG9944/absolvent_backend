package com.umg.absolwentbackend.controllers;

import com.umg.absolwentbackend.Constants;
import com.umg.absolwentbackend.exceptions.AuthenticationException;
import com.umg.absolwentbackend.models.Graduate;
import com.umg.absolwentbackend.models.Group;
import com.umg.absolwentbackend.repositories.GraduateRepository;
import com.umg.absolwentbackend.services.DataService;
import com.umg.absolwentbackend.services.EmailService;
import com.umg.absolwentbackend.services.GraduateService;
import com.umg.absolwentbackend.services.GroupService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class GraduateController {

    @Autowired
    GraduateService graduateService;
    @Autowired
    private DataService dataService;
    @Autowired
    private EmailService emailSender;
    @Autowired
    private GraduateRepository graduateRepository;
    @Autowired
    private GroupService groupService;

    @PostMapping("/graduate")
    public ResponseEntity<Map<String,Object>> addGraduate(@RequestBody Map<String, Object> graduateMap)
    {
         String name = (String) graduateMap.get("firstName");
         String lastname = (String) graduateMap.get("lastName");
         //Date date_of_birth = Date.valueOf(LocalDate.parse((CharSequence) graduateMap.get("dateOfBirth")));
         String email = (String) graduateMap.get("email");
         int graduation_year = (Integer) graduateMap.get("graduationYear");
         String field = (String) graduateMap.get("field");
         String title = (String) graduateMap.get("title");
         String faculty = (String) graduateMap.get("faculty");
         String group = (String) graduateMap.get("group");
         String gender = (String) graduateMap.get("gender");
         Graduate graduate = null;
         try{
             graduate = graduateService.addGraduate(name,lastname,email,graduation_year,faculty,field,title,group,gender);
         } catch (AuthenticationException e) {
             Map<String, Object> map = new HashMap<>();
             map.put("success", false);
             map.put("message", e.getMessage());
             return new ResponseEntity<>(map, HttpStatus.CONFLICT);
         }
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("value", graduate);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @PostMapping("/survey")
    public ResponseEntity<Map<String,Object>> sendMail(@RequestBody Map<String, Object> graduateMap) {
        String groupName=(String)graduateMap.get("group_name");
        List<Map<String, Object>> graduateEmails = graduateRepository.findGroupEmails(groupName);
        Group group = null;
        try{
            group = groupService.validateGroup(groupName);
        }catch (Exception e){
            Map<String,Object> map = new HashMap<>();
            map.put("success", false);
            map.put("message", e.getMessage());
            return  new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        String bodyTemplate = "Link do ankiety ";
        String title = "Ankieta dla UMG";

        if(groupName.isEmpty()) {
            Map<String, Object> map = new HashMap<>();
            map.put("success", false);
            map.put("status", 500);
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //Wysy??anie emaila
        for (Map<String, Object> emailMap : graduateEmails){
            String token = generateSurveyToken(emailMap.get("email").toString(),emailMap.get("field").toString(),emailMap.get("faculty").toString(),emailMap.get("title").toString(), (Integer) emailMap.get("graduation_year"), (String) emailMap.get("gender"));

            String body = bodyTemplate;
            try {
                System.out.println(emailMap.get("email").toString());
                body += "https://absolwent.best/survey"+"?token="+token;
                emailSender.sendEmail(emailMap.get("email").toString(), title, body);

            } catch (Exception e) {
                Map<String, Object> map = new HashMap<>();
                map.put("success", false);
                map.put("status", 500);
                return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("status", 200);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    private String generateSurveyToken(String email,String field,String faculty,String title,Integer graduation_year,String gender){
        long timestamp = System.currentTimeMillis();
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(Constants.API_SECRET_KEY));
        String token = Jwts.builder().signWith(key)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
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
