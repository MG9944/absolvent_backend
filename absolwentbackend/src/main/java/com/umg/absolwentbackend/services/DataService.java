package com.umg.absolwentbackend.services;

import com.umg.absolwentbackend.repositories.DataRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;

@Service
@Transactional
public class DataService {

    @Autowired
    DataRepository dataRepository;

    public boolean sendData(Integer endingDate, String gender, String earning, String companySize, String townSize, String companyCategory, String jobSearchTime, String periodOfEmployment, Integer questionnarieId, boolean location, boolean proffesionalActivity, boolean jobSatisfaction, boolean training)  {
        return dataRepository.sendData(endingDate, gender,earning,companySize,townSize,companyCategory,jobSearchTime,periodOfEmployment,questionnarieId,location,proffesionalActivity,training, jobSatisfaction);
    }
}
