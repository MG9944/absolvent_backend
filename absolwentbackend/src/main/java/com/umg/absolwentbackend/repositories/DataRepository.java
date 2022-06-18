package com.umg.absolwentbackend.repositories;


import com.umg.absolwentbackend.models.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Repository
public class DataRepository
{
    @Autowired
    JdbcTemplate jdbcTemplate;
    //TODO: Create mapper for data.
    class DataRowMapper implements RowMapper<Data>
    {
        @Override
        public Data mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Data(
                    rs.getInt("ending_date"),
                    rs.getString("gender"),
                    rs.getString("earnings"),
                    rs.getString("company_size"),
                    rs.getString("town_size"),
                    rs.getString("company_category"),
                    rs.getString("job_search_time"),
                    rs.getString("job_satisfaction"),
                    "1",
                    rs.getBoolean("training"),
                    rs.getBoolean("location"),
                    rs.getBoolean("proffesional_activity"),
                    rs.getInt("data_id"),
                    rs.getInt("questionnarie_id")
            );
        }
    }

    // TODO: Extract values from objects.
    private List<String> getColumnValues(List<Data> buff, String nameOfColumn)
    {
        List<String> str_res = new ArrayList<String>();
        for(Data d: buff)
        {
            switch (nameOfColumn){
                case "period_of_employement":
                    str_res.add(d.getPeriod_of_employment());
                    break;
                case "company_category":
                    str_res.add(d.getCompany_category());
                    break;
                case "ending_date":
                    str_res.add(d.getEnding_date().toString());
                    break;
                case "data_id":
                    str_res.add(d.getData_id()+"");
                    break;
                case "company_size":
                    str_res.add(d.getCompany_size());
                    break;
                case "job_search_time":
                    str_res.add(d.getJob_search_time());
                    break;
                case "questionnarie_id":
                    str_res.add(d.getQuestionnarie_id()+"");
                    break;
                case "proffesional_activity":
                    str_res.add(d.isProffesional_activity()+"");
                    break;
                case "location":
                    str_res.add(d.isLocation()+"");
                    break;
                case "training":
                    str_res.add(d.isTraining()+"");
                    break;
                case "job_satisfaction":
                    str_res.add(d.getJob_satisfaction());
                    break;
                case "town_size":
                    str_res.add(d.getTown_size());
                    break;
            }
        }
        return str_res;
    }

    // TODO: Return all data.
    public List<Data> findAll()
    {
        try {
            String sql_query = "SELECT * FROM absolvent.data";
            System.out.println("Query: " + sql_query + "\n");
            return jdbcTemplate.query(sql_query, new DataRowMapper());
        }catch (Exception ex)
        {
            return null;
        }
    }

    // TODO: Get salary by gender and year.
    public List<Data> getAllByGenderAndYear(String gender,Integer year) {
        try
        {
            String sql_query = "SELECT * FROM absolvent.data WHERE gender ='"+gender+"' AND ending_date = "+year;
            System.out.println("Query: "+sql_query+"\n");
            return jdbcTemplate.query(sql_query, new DataRowMapper());
        } catch (Exception ex)
        {
            return null;
        }
    }

    // TODO: Get period of employement by gender and year.
    public List<String> getPeriodOfEmployementByGenderAndYear(String gender,Integer year)
    {
        try
        {
            String sql_query = "SELECT * FROM absolvent.data WHERE gender ='"+ gender+"' AND ending_date = "+year;
            System.out.println("Query: "+sql_query+"\n");
            return getColumnValues(jdbcTemplate.query(sql_query,new DataRowMapper()),"period_of_employement");
        } catch (Exception ex)
        {
            return null;
        }
    }

    // TODO: Get company category.
    public List<String> getCompanyCategoryByGenderAndYear(String gender,Integer year)
    {
        try
        {
            String sql_query = "SELECT * FROM absolvent.data WHERE gender ='"+ gender+"' AND ending_date = "+year;
            System.out.println("Query: "+sql_query+"\n");
            return getColumnValues(jdbcTemplate.query(sql_query, new DataRowMapper()), "company_category");
        } catch (Exception ex)
        {
            return null;
        }
    }

    // TODO: Get company size.
    public List<String> getCompanySizeByGenderAndYear(String gender,Integer year)
    {
        try
        {
            String sql_query = "SELECT * FROM absolvent.data WHERE gender ='"+ gender+"' AND ending_date = "+year;
            System.out.println("Query: "+sql_query+"\n");
            return getColumnValues(jdbcTemplate.query(sql_query, new DataRowMapper()), "company_size");
        } catch (Exception ex)
        {
            return null;
        }
    }

    // TODO: Get job search time.
    public List<String> getJobSearchTimeByGenderAndYear(String gender,Integer year)
    {
        try
        {
            String sql_query = "SELECT * FROM absolvent.data WHERE gender ='"+ gender+"' AND ending_date = "+year;
            System.out.println("Query: "+sql_query+"\n");
            return getColumnValues(jdbcTemplate.query(sql_query, new DataRowMapper()), "job_search_time");
        } catch (Exception ex)
        {
            return null;
        }
    }

    // TODO: Send object to database.
    public boolean sendData(Integer endingDate, String gender, String earning, String companySize, String townSize, String companyCategory, String jobSearchTime, String periodOfEmployment, Integer questionnarieId, boolean location, boolean proffesionalActivity, boolean jobSatisfaction, boolean training )
    {
        try
        {
            String sql_add  = "INSERT INTO absolvent.data(ending_date, gender, earnings, company_size, town_size, company_category, job_search_time, period_of_employement, questionnarie_id, location, proffesional_activity, job_satisfaction, training) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            jdbcTemplate.update(sql_add, endingDate, gender, earning, companySize, townSize, companyCategory, jobSearchTime, periodOfEmployment, questionnarieId, location, proffesionalActivity, jobSatisfaction,training);
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }
}
