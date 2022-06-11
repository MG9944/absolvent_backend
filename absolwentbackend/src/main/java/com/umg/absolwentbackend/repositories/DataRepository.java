package com.umg.absolwentbackend.repositories;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;

@Repository
public class DataRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;
    private static final String SQL_ADD  = "INSERT INTO absolvent.data(ending_date, gender, earnings, company_size, town_size, company_category, job_search_time, period_of_employement, questionnarie_id, location, proffesional_activity, job_satisfaction, training) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SQL_SELECT = "";


    public boolean sendData(Integer endingDate, String gender, String earning, String companySize, String townSize, String companyCategory, String jobSearchTime, String periodOfEmployment, Integer questionnarieId, boolean location, boolean proffesionalActivity, boolean jobSatisfaction, boolean training ) {
        try{
            int t = jdbcTemplate.update(SQL_ADD, endingDate, gender, earning, companySize, townSize, companyCategory, jobSearchTime, periodOfEmployment, questionnarieId, location, proffesionalActivity, jobSatisfaction,training);
            return true;

        } catch (Exception ex) {

            return false;
        }

    }
}
