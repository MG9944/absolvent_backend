package com.umg.absolwentbackend.services;

import com.umg.absolwentbackend.models.Data;
import com.umg.absolwentbackend.repositories.DataRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DataService {

    @Autowired
    DataRepository dataRepository;

    public boolean sendData(Integer endingDate, String gender, String earning, String companySize, String townSize, String companyCategory, String jobSearchTime, String periodOfEmployment, Integer questionnarieId, boolean location, boolean proffesionalActivity, boolean jobSatisfaction, boolean training)  {
        return dataRepository.sendData(endingDate, gender,earning,companySize,townSize,companyCategory,jobSearchTime,periodOfEmployment,questionnarieId,location,proffesionalActivity,training, jobSatisfaction);
    }

    // TODO: Done!
    public List<Data> findAll()
    {
        return dataRepository.findAll();
    }

    // TODO: Done!
    public List<Data> getAllByGenderAndYear(String gender, Integer year)
    {
        return dataRepository.getAllByGenderAndYear(gender, year);
    }

    // TODO: Done!
    public List<String> getPeriodOfEmployementByGenderAndYear(String gender,Integer year)
    {
        return dataRepository.getPeriodOfEmployementByGenderAndYear(gender, year);
    }

    // TODO: Done!
    public List<String> getCompanySizeByGenderAndYear(String gender,Integer year) {
        return dataRepository.getCompanySizeByGenderAndYear(gender,year);
    }

    // TODO: Done!
    public List<String> getCompanyCategoryByGenderAndYear(String gender,Integer year) {
        return dataRepository.getCompanyCategoryByGenderAndYear(gender, year);
    }

    // TODO: Done!
    public List<String> getJobSearchTimeByGenderAndYear(String gender,Integer year) {
        return dataRepository.getJobSearchTimeByGenderAndYear(gender, year);
    }
}
