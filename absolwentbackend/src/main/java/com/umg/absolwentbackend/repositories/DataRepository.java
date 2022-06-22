package com.umg.absolwentbackend.repositories;

import com.umg.absolwentbackend.models.Data;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.security.KeyPair;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        public Data mapRow(ResultSet rs, int rowNum) throws SQLException
        {
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
                    rs.getInt("data_id")
            );
        }
    }

    // TODO: Return all data.
    public List<Data> findAll()
    {
        String sql_query = "SELECT * FROM absolvent.data";
        System.out.println("Query: " + sql_query + "\n");
        return jdbcTemplate.query(sql_query, new DataRowMapper());
    }

    // TODO: Get salary by gender and year.
    public List<Data> getAllByGenderAndYear(String gender,Integer year)
    {
        String sql_query = "SELECT * FROM absolvent.data WHERE gender ='"+gender+"' AND ending_date = "+year;
        System.out.println("Query: "+sql_query+"\n");
        return jdbcTemplate.query(sql_query, new DataRowMapper());
    }

    // TODO: Get period of employement by gender and year.
    public JSONObject getPeriodOfEmployementByGenderAndYear(String gender,Integer year)
    {
        String sql_query = "SELECT * FROM absolvent.data WHERE gender ='"+ gender+"' AND ending_date = "+year;
        System.out.println("Query: "+sql_query+"\n");
        List<Data> buff = jdbcTemplate.query(sql_query,new DataRowMapper());
        Hashtable<String, Integer> data = new Hashtable<>();
        int counter = 0;
        for(Data d: buff)
        {
            if(!data.contains(d.getPeriod_of_employment())){
                data.put(d.getPeriod_of_employment(), 1);
                counter++;
            }
        }
        JSONArray j_buffon = new JSONArray();
        Set<String> keys = data.keySet();
        int i = 1;
        for(String key: keys)
        {
            JSONObject object = new JSONObject();
            object.put("name", key);
            object.put("count", data.get(key));
            object.put("id", i);
            j_buffon.add(object);
            i++;
        }
        JSONObject object = new JSONObject();
        object.put("answers", j_buffon);
        object.put("name", "Rok zatrudnienia");
        object.put("answersCount", counter);
        return object;
    }

    // TODO: Get company category.
    public JSONObject getCompanyCategoryByGenderAndYear(String gender,Integer year)
    {
        String sql_query = "SELECT * FROM absolvent.data WHERE gender ='"+ gender+"' AND ending_date = "+year;
        System.out.println("Query: "+sql_query+"\n");
        List<Data> buff = jdbcTemplate.query(sql_query, new DataRowMapper());
        Set<String> names = new TreeSet<>();
        for(Data d: buff)
        {
            names.add(d.getCompany_category());
        }
        int[]counters = new int[names.size()];
        for(int i =0; i<counters.length; i++)
        {
            counters[i]=0;
        }
        for(Data d: buff)
        {
            String name_of_company = d.getCompany_category();
            int i=0;
            for(String name : names)
            {
                if(name.equals(name_of_company) || name == name_of_company)
                {
                    counters[i]++;
                }
                i++;
            }
        }
        JSONArray j_buff = new JSONArray();
        int answers_count = 0;
        int i=0;
        for(String name: names)
        {
            JSONObject obj = new JSONObject();
            obj.put("id", (i+1));
            obj.put("count", counters[i]);
            obj.put("name", name);
            j_buff.add(obj);
            answers_count += counters[i];
            i++;
        }
        JSONObject data_object = new JSONObject();
        data_object.put("answers", buff);
        data_object.put("name", "Wielkość firmy");
        data_object.put("answersCount", answers_count);
        System.out.println(data_object);
        return data_object;
    }

    // TODO: Get company size.
    public JSONObject getCompanySizeByGenderAndYear(String gender,Integer year)
    {
        String sql_query = "SELECT * FROM absolvent.data WHERE gender ='"+ gender+"' AND ending_date = "+year;
        List<Data> res = jdbcTemplate.query(sql_query, new DataRowMapper());
        int[]counters = {0, 0, 0, 0, 0, 0, 0, 0};
        String[]name_of_counters={"30-44", "44-59", "59-74", "74-89", "89-104", "104-119", "119-149", "149"};
        int counter_position = 0;
        for(Data d: res)
        {
            float searchTime = Float.parseFloat(d.getCompany_size());
            for(String current: name_of_counters)
            {
                try
                {
                    String[] s_current = current.split("-");
                    float lb = Float.parseFloat(s_current[0]);
                    float rb = Float.parseFloat(s_current[1]);
                    if(searchTime > lb && searchTime < rb)
                        counters[counter_position]++;
                }catch (Exception e)
                {
                    float value = Float.parseFloat(current);
                    if(searchTime > value)
                        counters[counter_position]++;
                }
                counter_position++;
            }
            counter_position = 0;
        }
        JSONArray buff = new JSONArray();
        int answers_count = 0;
        for(int i=0;i<counters.length;i++)
        {
            JSONObject obj = new JSONObject();
            obj.put("id", (i+1));
            obj.put("count", counters[i]);
            obj.put("name", name_of_counters[i]);
            buff.add(obj);
            answers_count += counters[i];
        }
        JSONObject data_object = new JSONObject();
        data_object.put("answers", buff);
        data_object.put("name", "Wielkość firmy");
        data_object.put("answersCount", answers_count);
        System.out.println(data_object);
        return data_object;
    }

    // TODO: Get job search time.
    public JSONObject getJobSearchTimeByGenderAndYear(String gender,Integer year)
    {
        String sql_query = "SELECT * FROM absolvent.data WHERE gender ='"+ gender+"' AND ending_date = "+year;
        List<Data> res = jdbcTemplate.query(sql_query, new DataRowMapper());
        int[]counters = {0, 0, 0, 0, 0, 0, 0, 0};
        String[]name_of_counters={"30-44", "44-59", "59-74", "74-89", "89-104", "104-119", "119-149", "149"};
        int counter_position = 0;
        for(Data d: res)
        {
            float searchTime = Float.parseFloat(d.getJob_search_time());
            for(String current: name_of_counters)
            {
                try
                {
                    String[] s_current = current.split("-");
                    float lb = Float.parseFloat(s_current[0]);
                    float rb = Float.parseFloat(s_current[1]);
                    if(searchTime > lb && searchTime < rb)
                        counters[counter_position]++;
                }catch (Exception e)
                {
                    float value = Float.parseFloat(current);
                    if(searchTime > value)
                        counters[counter_position]++;
                }
                counter_position++;
            }
            counter_position = 0;
        }
        JSONArray buff = new JSONArray();
        int answers_count = 0;
        for(int i=0;i<counters.length;i++)
        {
            JSONObject obj = new JSONObject();
            obj.put("id", (i+1));
            obj.put("count", counters[i]);
            obj.put("name", name_of_counters[i]);
            buff.add(obj);
            answers_count += counters[i];
        }
        JSONObject data_object = new JSONObject();
        data_object.put("answers", buff);
        data_object.put("name", "Czas szukania pracy");
        data_object.put("answersCount", answers_count);
        System.out.println(data_object);
        return data_object;
    }

    // TODO: Get salary.
    public JSONObject getSalaryByGenderAndYear(String gender,Integer year)
    {
        String sql_query = "SELECT * FROM absolvent.data WHERE gender ='"+ gender+"' AND ending_date = "+year;
        List<Data> res = jdbcTemplate.query(sql_query, new DataRowMapper());
        int[]counters = {0, 0, 0, 0, 0, 0, 0, 0};
        String[]name_of_counters={"3010-4400", "4401-5900", "5901-7400", "7401-8900", "8901-10400", "10401-11900", "11901-14900", "14900"};
        for(Data d: res)
        {
            float salary = Float.parseFloat(d.getEarnings());
            if(salary > 3010 & salary < 4400)
            {
                counters[0]++;
            }
            else if(salary > 4401 & salary < 5900)
            {
                counters[1]++;
            }
            else if(salary > 5901 & salary < 7400)
            {
                counters[2]++;
            }
            else if(salary > 7401 & salary < 8900)
            {
                counters[3]++;
            }
            else if(salary > 8901 & salary < 10400)
            {
                counters[4]++;
            }
            else if(salary > 10401 & salary < 11900)
            {
                counters[5]++;
            }
            else if(salary > 11901 & salary < 14900)
            {
                counters[6]++;
            }
            else if(salary > 14900)
            {
                counters[7]++;
            }
        }
        JSONArray buff = new JSONArray();
        int answers_count = 0;
        for(int i=0;i<counters.length;i++)
        {
            JSONObject obj = new JSONObject();
            obj.put("id", (i+1));
            obj.put("count", counters[i]);
            obj.put("name", name_of_counters[i]);
            buff.add(obj);
            answers_count += counters[i];
        }
        JSONObject data_object = new JSONObject();
        data_object.put("answers", buff);
        data_object.put("name", "Przedział zarobkowy brutton");
        data_object.put("answersCount", 721);
        System.out.println(data_object);
        return data_object;
    }

    // TODO: Creating new record.
    public boolean sendData(Integer endingDate, String gender, String earning, String companySize, String townSize, String companyCategory, String jobSearchTime, String periodOfEmployment, String field,String faculty ,String title, Integer questionnarieId, boolean location, boolean proffesionalActivity, String jobSatisfaction, boolean training)
    {
        try
        {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO absolvent.data(ending_date, gender, earnings, company_size, town_size, company_category, job_search_time, period_of_employement,field,faculty,title, location, proffesional_activity, job_satisfaction, training) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1,endingDate);
                ps.setString(2,gender);
                ps.setString(3,earning);
                ps.setString(4,companySize);
                ps.setString(5, townSize);
                ps.setString(6,companyCategory);
                ps.setString(7,jobSearchTime);
                ps.setString(8,periodOfEmployment);
                ps.setString(9,field);
                ps.setString(10,faculty);
                ps.setString(11,title);
                ps.setBoolean(12,location);
                ps.setBoolean(13,proffesionalActivity);
                ps.setString(14,jobSatisfaction);
                ps.setBoolean(15,training);
                return ps;
            },keyHolder);
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }
}
