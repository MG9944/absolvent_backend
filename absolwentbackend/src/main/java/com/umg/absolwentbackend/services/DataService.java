package com.umg.absolwentbackend.services;

import com.umg.absolwentbackend.models.Data;
import com.umg.absolwentbackend.repositories.DataRepository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class DataService {

    @Autowired
    DataRepository dataRepository;

    public boolean sendData(Integer endingDate, String gender, String earning, String companySize, String townSize, String companyCategory, String jobSearchTime, String periodOfEmployment, String field,String faculty ,String title, Integer questionnarieId, String location, String proffesionalActivity, String jobSatisfaction, String training)  {
        return dataRepository.sendData(endingDate, gender, earning, companySize, townSize, companyCategory, jobSearchTime, periodOfEmployment, field, faculty, title, questionnarieId, location, proffesionalActivity,  jobSatisfaction, training);
    }



}
