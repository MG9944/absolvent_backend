package com.umg.absolwentbackend.repositories;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class DataRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;
    private static final String SQL_ADD  = "INSERT INTO absolvent.data(ending_date, gender, earnings, company_size, town_size, company_category, job_search_time, period_of_employement, questionnarie_id, location, proffesional_activity, job_satisfaction, training) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SQL_SELECT_SALARY_BY_YEAR_AND_GENDER = "SELECT earnings FROM absolvent.data WHERE gender = ? AND ending_date = ?";
    private static final String SQL_SELECT_PERIOD_OF_EMPLOYEMENT_BY_YEAR_AND_GENDER = "SELECT period_of_employement FROM absolvent.data WHERE gender = ? AND ending_date = ?";
    private static final String SQL_SELECT_COMPANY_SIZE_BY_YEAR_AND_GENDER = "SELECT company_size FROM absolvent.data WHERE gender = ? AND ending_date = ?";
    private static final String SQL_SELECT_JOB_SEARCH_TIME_BY_YEAR_AND_GENDER = "SELECT job_search_time FROM absolvent.data WHERE gender = ? AND ending_date = ?";
    private static final String SQL_SELECT_COMPANY_CATEGORY_BY_YEAR_AND_GENDER = "SELECT company_category FROM absolvent.data WHERE gender = ? AND ending_date = ?";

    public boolean sendData(Integer endingDate, String gender, String earning, String companySize, String townSize, String companyCategory, String jobSearchTime, String periodOfEmployment, Integer questionnarieId, boolean location, boolean proffesionalActivity, boolean jobSatisfaction, boolean training ) {
        try{
            int t = jdbcTemplate.update(SQL_ADD, endingDate, gender, earning, companySize, townSize, companyCategory, jobSearchTime, periodOfEmployment, questionnarieId, location, proffesionalActivity, jobSatisfaction,training);
            return true;

        } catch (Exception ex) {

            return false;
        }

    }

    public List<Map<String, Object>> getSalaryByGenderAndYear(String gender,Integer year) {
        try{
        return jdbcTemplate.queryForList(SQL_SELECT_SALARY_BY_YEAR_AND_GENDER,gender,year);
        } catch (Exception ex) {

            return null;
        }
    }

    public List<Map<String, Object>> getPeriodOfEmployementByGenderAndYear(String gender,Integer year) {
        try{
        return jdbcTemplate.queryForList(SQL_SELECT_PERIOD_OF_EMPLOYEMENT_BY_YEAR_AND_GENDER,gender,year);
        } catch (Exception ex) {

            return null;
        }
    }
    public List<Map<String, Object>> getCompanySizeByGenderAndYear(String gender,Integer year) {
        try{
        return jdbcTemplate.queryForList(SQL_SELECT_COMPANY_SIZE_BY_YEAR_AND_GENDER,gender,year);
        } catch (Exception ex) {

            return null;
        }
    }

    public List<Map<String, Object>> getCompanyCategoryByGenderAndYear(String gender,Integer year) {
        try{
        return jdbcTemplate.queryForList(SQL_SELECT_COMPANY_CATEGORY_BY_YEAR_AND_GENDER,gender,year);
        } catch (Exception ex) {

            return null;
        }
    }
    public List<Map<String, Object>> getJobSearchTimeByGenderAndYear(String gender,Integer year) {
        try{
        return jdbcTemplate.queryForList(SQL_SELECT_JOB_SEARCH_TIME_BY_YEAR_AND_GENDER,gender,year);
        } catch (Exception ex) {

            return null;
        }
    }
}
