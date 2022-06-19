package com.umg.absolwentbackend.repositories;

import com.umg.absolwentbackend.models.Data;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private List<JSONObject> getColumnValuesToJSONArray(List<Data> buff, String nameOfColumn)
    {
        List<JSONObject> res = new ArrayList<JSONObject>();
        for(Data d: buff)
        {
            JSONObject object = new JSONObject();
            switch (nameOfColumn){
                case "period_of_employement":
                    object.put("period_of_employement", d.getPeriod_of_employment());
                    res.add(object);
                    break;
                case "company_category":
                    object.put("company_category", d.getCompany_category());
                    res.add(object);
                    break;
                case "ending_date":
                    object.put("ending_date", d.getEnding_date());
                    res.add(object);
                    break;
                case "data_id":
                    object.put("data_id", d.getData_id());
                    res.add(object);
                    break;
                case "company_size":
                    object.put("company_size", d.getCompany_size());
                    res.add(object);
                    break;
                case "job_search_time":
                    object.put("job_search_time", d.getJob_search_time());
                    res.add(object);
                    break;
                case "questionnarie_id":
                    object.put("questionnarie_id", d.getQuestionnarie_id());
                    res.add(object);
                    break;
                case "proffesional_activity":
                    object.put("proffesional_activity", d.isProffesional_activity());
                    res.add(object);
                    break;
                case "location":
                    object.put("location", d.isLocation());
                    res.add(object);
                    break;
                case "training":
                    object.put("training", d.isTraining());
                    res.add(object);
                    break;
                case "job_satisfaction":
                    object.put("job_satisfaction", d.getJob_satisfaction());
                    res.add(object);
                    break;
                case "town_size":
                    object.put("town_size", d.getTown_size());
                    res.add(object);
                    break;
                case "earnings":
                    object.put("earnings", d.getEarnings());
                    res.add(object);
                    break;
            }
        }
        return res;
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
    public List<JSONObject> getPeriodOfEmployementByGenderAndYear(String gender,Integer year)
    {
        try
        {
            String sql_query = "SELECT * FROM absolvent.data WHERE gender ='"+ gender+"' AND ending_date = "+year;
            System.out.println("Query: "+sql_query+"\n");
            return getColumnValuesToJSONArray(jdbcTemplate.query(sql_query,new DataRowMapper()),"period_of_employement");
        } catch (Exception ex)
        {
            return null;
        }
    }

    // TODO: Get company category.
    public List<JSONObject> getCompanyCategoryByGenderAndYear(String gender,Integer year)
    {
        try
        {
            String sql_query = "SELECT * FROM absolvent.data WHERE gender ='"+ gender+"' AND ending_date = "+year;
            System.out.println("Query: "+sql_query+"\n");
            return getColumnValuesToJSONArray(jdbcTemplate.query(sql_query, new DataRowMapper()), "company_category");
        } catch (Exception ex)
        {
            return null;
        }
    }

    // TODO: Get company size.
    public List<JSONObject> getCompanySizeByGenderAndYear(String gender,Integer year)
    {
        try
        {
            String sql_query = "SELECT * FROM absolvent.data WHERE gender ='"+ gender+"' AND ending_date = "+year;
            System.out.println("Query: "+sql_query+"\n");
            return getColumnValuesToJSONArray(jdbcTemplate.query(sql_query, new DataRowMapper()), "company_size");
        } catch (Exception ex)
        {
            return null;
        }
    }

    // TODO: Get job search time.
    public List<JSONObject> getJobSearchTimeByGenderAndYear(String gender,Integer year)
    {
        try
        {
            String sql_query = "SELECT * FROM absolvent.data WHERE gender ='"+ gender+"' AND ending_date = "+year;
            System.out.println("Query: "+sql_query+"\n");
            return getColumnValuesToJSONArray(jdbcTemplate.query(sql_query, new DataRowMapper()), "job_search_time");
        } catch (Exception ex)
        {
            return null;
        }
    }

    // TODO: Get salary.
    public List<JSONObject> getSalaryByGenderAndYear(String gender,Integer year)
    {
        try
        {
            String sql_query = "SELECT * FROM absolvent.data WHERE gender ='"+ gender+"' AND ending_date = "+year;
            System.out.println("Query: "+sql_query+"\n");
            return getColumnValuesToJSONArray(jdbcTemplate.query(sql_query, new DataRowMapper()), "earnings");
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
