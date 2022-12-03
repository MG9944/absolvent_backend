package com.umg.absolwentbackend.controllers;


import com.umg.absolwentbackend.Constants;
import com.umg.absolwentbackend.models.Group;
import com.umg.absolwentbackend.repositories.GraduateRepository;
import com.umg.absolwentbackend.services.DataService;
import com.umg.absolwentbackend.services.EmailService;
import com.umg.absolwentbackend.services.GroupService;
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
@RequestMapping("/api/survey")
public class QuestionnarieController {

    @Autowired
    private DataService dataService;
    @Autowired
    private EmailService emailSender;
    @Autowired
    private GraduateRepository graduateRepository;
    @Autowired
    private GroupService groupService;


    @PostMapping("")
    public ResponseEntity<Map<String, Object>> sendSurvey(@RequestBody Map<String, Object> userMap) {

        Integer endingDate = (Integer) userMap.get("endingDate");
        String gender = (String) userMap.get("gender");
        String earning = (String) userMap.get("earning");
        String companySize = (String) userMap.get("companySize");
        String townSize = (String) userMap.get("townSize");
        String companyCategory = (String) userMap.get("companyCategory");
        String jobSearchTime = (String) userMap.get("jobSearchTime");
        String periodOfEmployment = (String) userMap.get("periodOfEmployment");
        String field = (String) userMap.get("field");
        String faculty = (String) userMap.get("faculty");
        String title = (String) userMap.get("title");
        String jobSatisfaction = (String) userMap.get("jobSatisfaction");
        Integer questionnarieId = (Integer) userMap.get("questionnarieId");
        boolean location = (boolean) userMap.get("location");
        boolean proffesionalActivity = (boolean) userMap.get("proffesionalActivity");
        boolean training = (boolean) userMap.get("training");
        boolean added = dataService.sendData(endingDate, gender, earning, companySize, townSize, companyCategory, jobSearchTime, periodOfEmployment,field,faculty,title,questionnarieId, location, proffesionalActivity, jobSatisfaction, training);

        if (added) {
            Map<String, Object> map = new HashMap<>();
            map.put("success", true);
            return new ResponseEntity<>(map, HttpStatus.CREATED);
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("success", false);
            return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
        }
    }

}
