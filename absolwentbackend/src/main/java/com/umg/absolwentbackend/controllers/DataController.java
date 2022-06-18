package com.umg.absolwentbackend.controllers;


import com.umg.absolwentbackend.models.Graduate;
import com.umg.absolwentbackend.services.DataService;
import com.umg.absolwentbackend.services.UniveristyService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/survey")
public class DataController {

    @Autowired
    DataService dataService;

    @PostMapping("")
    public ResponseEntity<Map<String,Object>> sendSurvey(@RequestBody Map<String, Object> userMap) {

        Integer endingDate = (Integer) userMap.get("endingDate");
        String gender = (String) userMap.get("gender");
        String earning = (String) userMap.get("earning");
        String companySize = (String) userMap.get("companySize");
        String townSize = (String) userMap.get("townSize");
        String companyCategory = (String) userMap.get("companyCategory");
        String jobSearchTime = (String) userMap.get("jobSearchTime");
        String periodOfEmployment = (String) userMap.get("periodOfEmployment");
        Integer questionnarieId = (Integer) userMap.get("questionnarieId");
        boolean location = (boolean) userMap.get("location");
        boolean proffesionalActivity = (boolean) userMap.get("proffesionalActivity");
        boolean training = (boolean) userMap.get("training");
        boolean jobSatisfaction = (boolean) userMap.get("jobSatisfaction");
        boolean added = dataService.sendData(endingDate, gender, earning, companySize, townSize, companyCategory, jobSearchTime, periodOfEmployment, questionnarieId, location, proffesionalActivity, training, jobSatisfaction);

        if (added) {
            Map<String, Object> map = new HashMap<>();
            map.put("success", true);
            return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
        }
        else
        {
            Map<String, Object> map = new HashMap<>();
            map.put("success", false);
            return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
        }
    }




}
