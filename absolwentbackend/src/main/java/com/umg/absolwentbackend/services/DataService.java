package com.umg.absolwentbackend.services;

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

    public List<Map<String, Object>> getSalaryByGenderAndYear(String gender, Integer year) {
        return dataRepository.getSalaryByGenderAndYear(gender, year);
    }

    public List<Map<String, Object>> getPeriodOfEmployementByGenderAndYear(String gender,Integer year) {
        return dataRepository.getPeriodOfEmployementByGenderAndYear(gender, year);
    }

    public List<Map<String, Object>> getCompanySizeByGenderAndYear(String gender,Integer year) {
        return dataRepository.getCompanySizeByGenderAndYear(gender,year);
    }

    public List<Map<String, Object>> getCompanyCategoryByGenderAndYear(String gender,Integer year) {
        return dataRepository.getCompanyCategoryByGenderAndYear(gender, year);
    }

    public List<Map<String, Object>> getJobSearchTimeByGenderAndYear(String gender,Integer year) {
        return dataRepository.getJobSearchTimeByGenderAndYear(gender, year);
    }


}
