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

    // TODO: Done!
    public boolean sendData(Integer endingDate, String gender, String earning, String companySize, String townSize, String companyCategory, String jobSearchTime, String periodOfEmployment, String field,String faculty ,String title, Integer questionnarieId, boolean location, boolean proffesionalActivity, String jobSatisfaction, boolean training)  {
        return dataRepository.sendData(endingDate, gender, earning, companySize, townSize, companyCategory, jobSearchTime, periodOfEmployment, field, faculty, title, questionnarieId, location, proffesionalActivity,  jobSatisfaction, training);
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
    public JSONObject getPeriodOfEmployementByGenderAndYear(String gender,Integer year)
    {
        return dataRepository.getPeriodOfEmployementByGenderAndYear(gender, year);
    }

    // TODO: Done!
    public JSONObject getCompanyCategoryByGenderAndYear(String gender,Integer year) {
        return dataRepository.getCompanyCategoryByGenderAndYear(gender, year);
    }

    // TODO: Done!
    public JSONObject getCompanySizeByGenderAndYear(String gender,Integer year) {
        return dataRepository.getCompanySizeByGenderAndYear(gender,year);
    }

    // TODO: Done!
    public JSONObject getJobSearchTimeByGenderAndYear(String gender,Integer year) {
        return dataRepository.getJobSearchTimeByGenderAndYear(gender, year);
    }

    // TODO: Done!
    public JSONObject getSalaryByGenderAndYear(String gender,Integer year) {
        return dataRepository.getSalaryByGenderAndYear(gender, year);
    }
}
