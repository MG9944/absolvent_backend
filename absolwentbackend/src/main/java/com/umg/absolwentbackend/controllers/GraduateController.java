package com.umg.absolwentbackend.controllers;

import com.umg.absolwentbackend.exceptions.AuthenticationException;
import com.umg.absolwentbackend.models.Graduate;
import com.umg.absolwentbackend.services.GraduateService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/graduate")
public class GraduateController {

    @Autowired
    GraduateService graduateService;

    @PostMapping("")
    public ResponseEntity<Map<String,Object>> addGraduate(@RequestBody Map<String, Object> graduateMap)
    {
         String name = (String) graduateMap.get("name");
         String lastname = (String) graduateMap.get("lastname");
         Date date_of_birth = Date.valueOf(LocalDate.parse((CharSequence) graduateMap.get("date_of_birth")));
         String email = (String) graduateMap.get("email");
         int graduation_year = (Integer) graduateMap.get("graduation_year");
         String field = (String) graduateMap.get("field");
         String title = (String) graduateMap.get("title");
         String faculty = (String) graduateMap.get("faculty");
         Graduate graduate = null;
         try{
             graduate = graduateService.addGraduate(name,lastname,email,graduation_year,faculty,field,date_of_birth,title);
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

}
