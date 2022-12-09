package com.umg.absolwentbackend.repositories;

import com.umg.absolwentbackend.models.Data;
import com.umg.absolwentbackend.models.Graduate;
import com.umg.absolwentbackend.models.Questionnaire;
import com.umg.absolwentbackend.models.Results;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

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

    final String SQL_ADD = "INSERT INTO absolvent.data(ending_date, gender, earnings, company_size, town_size, company_category, job_search_time, period_of_employement,field,faculty,title, location, proffesional_activity, job_satisfaction, training, questionnarieId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    final String SQL_GET_PA_COUNT_BY_YEAR = "SELECT extract(year from questionnaire.sending_data) as year,project.absolvent.data.proffesional_activity, COUNT(project.absolvent.data.proffesional_activity) FROM project.absolvent.data JOIN project.absolvent.questionnaire ON project.absolvent.data.questionnaire_id = project.absolvent.questionnaire.questionnaire_id GROUP BY project.absolvent.questionnaire.sending_data, project.absolvent.data.proffesional_activity ORDER BY year;";
    final String SQL_GET_POE_COUNT_BY_YEAR = "SELECT extract(year from questionnaire.sending_data) as year,project.absolvent.data.period_of_employement, COUNT(project.absolvent.data.period_of_employement) FROM project.absolvent.data JOIN project.absolvent.questionnaire ON project.absolvent.data.questionnaire_id = project.absolvent.questionnaire.questionnaire_id GROUP BY project.absolvent.questionnaire.sending_data, project.absolvent.data.period_of_employement;";
    final String SQL_GET_JS_COUNT_BY_YEAR ="SELECT extract(year from questionnaire.sending_data) as year,project.absolvent.data.job_satisfaction, COUNT(project.absolvent.data.job_satisfaction) FROM project.absolvent.data JOIN project.absolvent.questionnaire ON project.absolvent.data.questionnaire_id = project.absolvent.questionnaire.questionnaire_id GROUP BY project.absolvent.questionnaire.sending_data, project.absolvent.data.job_satisfaction;";
    final String SQL_GET_T_COUNT_BY_YEAR ="SELECT extract(year from questionnaire.sending_data) as year,project.absolvent.data.title, COUNT(project.absolvent.data.title) FROM project.absolvent.data JOIN project.absolvent.questionnaire ON project.absolvent.data.questionnaire_id = project.absolvent.questionnaire.questionnaire_id GROUP BY project.absolvent.questionnaire.sending_data, project.absolvent.data.title;";
    final String SQL_GET_E_COUNT_BY_YEAR ="SELECT extract(year from questionnaire.sending_data) as year,project.absolvent.data.earnings, COUNT(project.absolvent.data.earnings) FROM project.absolvent.data JOIN project.absolvent.questionnaire ON project.absolvent.data.questionnaire_id = project.absolvent.questionnaire.questionnaire_id GROUP BY project.absolvent.questionnaire.sending_data, project.absolvent.data.earnings;";

    final String SQL_GET_E_COUNT_BY_SEX ="SELECT project.absolvent.data.gender, project.absolvent.data.earnings, COUNT(project.absolvent.data.earnings) FROM project.absolvent.data GROUP BY project.absolvent.data.gender, project.absolvent.data.earnings ORDER BY project.absolvent.data.gender ASC, project.absolvent.data.earnings DESC;";
    final String SQL_GET_JST_COUNT_BY_SEX ="SELECT project.absolvent.data.gender, project.absolvent.data.job_search_time, COUNT(project.absolvent.data.job_search_time) FROM project.absolvent.data GROUP BY project.absolvent.data.gender, project.absolvent.data.job_search_time ORDER BY project.absolvent.data.gender ASC;";
    final String SQL_GET_CS_COUNT_BY_SEX ="SELECT project.absolvent.data.gender, project.absolvent.data.company_size, COUNT(project.absolvent.data.company_size) FROM project.absolvent.data GROUP BY project.absolvent.data.gender, project.absolvent.data.company_size ORDER BY project.absolvent.data.gender ASC, count ASC;";
    final String SQL_GET_T_COUNT_BY_SEX ="SELECT project.absolvent.data.gender, project.absolvent.data.training, COUNT(project.absolvent.data.training) FROM project.absolvent.data GROUP BY project.absolvent.data.gender, project.absolvent.data.training ORDER BY project.absolvent.data.gender ASC, count ASC;";
    final String SQL_GET_TS_COUNT_BY_SEX ="SELECT project.absolvent.data.gender, project.absolvent.data.town_size, COUNT(project.absolvent.data.town_size) as count FROM project.absolvent.data GROUP BY project.absolvent.data.gender, project.absolvent.data.town_size ORDER BY project.absolvent.data.gender ASC, count ASC;";

    final String SQL_GET_CS_COUNT_BY_EARNINGS ="SELECT project.absolvent.data.earnings, project.absolvent.data.company_size, COUNT(project.absolvent.data.company_size) FROM project.absolvent.data GROUP BY project.absolvent.data.earnings, project.absolvent.data.company_size ORDER BY count ASC  ";
    final String SQL_GET_TS_COUNT_BY_EARNINGS ="SELECT project.absolvent.data.earnings, project.absolvent.data.town_size, COUNT(project.absolvent.data.town_size) FROM project.absolvent.data GROUP BY project.absolvent.data.earnings, project.absolvent.data.town_size ORDER BY count ASC  ";
    final String SQL_GET_CC_COUNT_BY_EARNINGS ="SELECT project.absolvent.data.earnings, project.absolvent.data.company_category, COUNT(project.absolvent.data.company_category) FROM project.absolvent.data GROUP BY project.absolvent.data.earnings, project.absolvent.data.company_category ORDER BY count ASC  ";
    final String SQL_GET_FACULTY_COUNT_BY_EARNINGS ="SELECT project.absolvent.data.earnings, project.absolvent.data.faculty, COUNT(project.absolvent.data.faculty) FROM project.absolvent.data GROUP BY project.absolvent.data.earnings, project.absolvent.data.faculty ORDER BY count ASC  ";
    final String SQL_GET_T_COUNT_BY_EARNINGS ="SELECT project.absolvent.data.earnings, project.absolvent.data.title, COUNT(project.absolvent.data.title) FROM project.absolvent.data GROUP BY project.absolvent.data.earnings, project.absolvent.data.title ORDER BY count ASC  ";
    final String SQL_GET_FIELD_COUNT_BY_EARNINGS ="SELECT project.absolvent.data.earnings, project.absolvent.data.field, COUNT(project.absolvent.data.field) FROM project.absolvent.data GROUP BY project.absolvent.data.earnings, project.absolvent.data.field ORDER BY count ASC  ";

    final String SQL_GET_CC_COUNT_BY_FACULTY ="SELECT project.absolvent.data.faculty, project.absolvent.data.company_category, COUNT(project.absolvent.data.company_category) FROM project.absolvent.data GROUP BY project.absolvent.data.faculty, project.absolvent.data.company_category ORDER BY project.absolvent.data.faculty ASC  ";
    final String SQL_GET_JST_COUNT_BY_FACULTY ="SELECT project.absolvent.data.faculty, project.absolvent.data.job_search_time, COUNT(project.absolvent.data.job_search_time) FROM project.absolvent.data GROUP BY project.absolvent.data.faculty, project.absolvent.data.job_search_time ORDER BY project.absolvent.data.faculty ASC  ";
    final String SQL_GET_JS_COUNT_BY_FACULTY ="SELECT project.absolvent.data.faculty, project.absolvent.data.job_satisfaction, COUNT(project.absolvent.data.job_satisfaction) FROM project.absolvent.data GROUP BY project.absolvent.data.faculty, project.absolvent.data.job_satisfaction ORDER BY project.absolvent.data.faculty ASC  ";


    public boolean sendData(Integer endingDate, String gender, String earning, String companySize, String townSize, String companyCategory, String jobSearchTime, String periodOfEmployment, String field,String faculty ,String title, Integer questionnarieId, String location, String proffesionalActivity, String jobSatisfaction, String training)
    {
        try
        {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_ADD, Statement.RETURN_GENERATED_KEYS);
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
                ps.setString(12,location);
                ps.setString(13,proffesionalActivity);
                ps.setString(14,jobSatisfaction);
                ps.setString(15,training);
                ps.setInt(16, questionnarieId);
                return ps;
            },keyHolder);
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    /*
     *
     * BY YEAR
     *
     */
    public List<Results> getPaCountByYear()
    {
        List<Results> PA =  jdbcTemplate.query(SQL_GET_PA_COUNT_BY_YEAR,activityRowMaperYear);
        return PA;
    }

    public List<Results> getPoeCountByYear()
    {
        List<Results> PoE =  jdbcTemplate.query(SQL_GET_POE_COUNT_BY_YEAR,poeRowMaperYear);
        return PoE;
    }

    public List<Results> getJsCountByYear()
    {
        List<Results> JS =  jdbcTemplate.query(SQL_GET_JS_COUNT_BY_YEAR,JSRowMaperYear);
        return JS;
    }

    public List<Results> getTCountByYear()
    {
        List<Results> T =  jdbcTemplate.query(SQL_GET_T_COUNT_BY_YEAR,TRowMaperYear);
        return T;
    }

    public List<Results> getECountByYear()
    {
        List<Results> E =  jdbcTemplate.query(SQL_GET_E_COUNT_BY_YEAR,ERowMaperYear);
        return E;
    }

    private RowMapper<Results> activityRowMaperYear = ((rs, rowNum) -> {
        return new Results(rs.getInt("year"),
                rs.getBoolean("proffesional_activity"),
                rs.getInt("count"));
    });
    private RowMapper<Results> poeRowMaperYear = ((rs, rowNum) -> {
        return new Results(rs.getInt("year"),
                rs.getString("period_of_employement"),
                rs.getInt("count"));
    });
    private RowMapper<Results> JSRowMaperYear = ((rs, rowNum) -> {
        return new Results(rs.getInt("year"),
                rs.getString("job_satisfaction"),
                rs.getInt("count"));
    });
    private RowMapper<Results> TRowMaperYear = ((rs, rowNum) -> {
        return new Results(rs.getInt("year"),
                rs.getString("title"),
                rs.getInt("count"));
    });
    private RowMapper<Results> ERowMaperYear = ((rs, rowNum) -> {
        return new Results(rs.getInt("year"),
                rs.getString("earnings"),
                rs.getInt("count"));
    });


    /*
     *
     * BY SEX
     *
     */
    public List<Results> getECountBySex()
    {
        List<Results> E = jdbcTemplate.query(SQL_GET_E_COUNT_BY_SEX, eRowMaperSex);
        return E;
    }
    public List<Results> getJstCountBySex()
    {
        List<Results> JST = jdbcTemplate.query(SQL_GET_JST_COUNT_BY_SEX, jstRowMaperSex);
        return JST;
    }
    public List<Results> getCsCountBySex()
    {
        List<Results> CS = jdbcTemplate.query(SQL_GET_CS_COUNT_BY_SEX, csRowMaperSex);
        return CS;
    }
    public List<Results> getTCountBySex()
    {
        List<Results> T = jdbcTemplate.query(SQL_GET_T_COUNT_BY_SEX, tRowMaperSex);
        return T;
    }
    public List<Results> getTsCountBySex()
    {
        List<Results> TS = jdbcTemplate.query(SQL_GET_TS_COUNT_BY_SEX, tsRowMaperSex);
        return TS;
    }

    private RowMapper<Results> eRowMaperSex = ((rs, rowNum) -> {
        return new Results(rs.getString("gender"),
                rs.getString("earnings"),
                rs.getInt("count"));
    });
    private RowMapper<Results> jstRowMaperSex = ((rs, rowNum) -> {
        return new Results(rs.getString("gender"),
                rs.getString("job_search_time"),
                rs.getInt("count"));
    });
    private RowMapper<Results> csRowMaperSex = ((rs, rowNum) -> {
        return new Results(rs.getString("gender"),
                rs.getString("company_size"),
                rs.getInt("count"));
    });
    private RowMapper<Results> tRowMaperSex = ((rs, rowNum) -> {
        return new Results(rs.getString("gender"),
                rs.getBoolean("training"),
                rs.getInt("count"));
    });
    private RowMapper<Results> tsRowMaperSex = ((rs, rowNum) -> {
        return new Results(rs.getString("gender"),
                rs.getString("town_size"),
                rs.getInt("count"));
    });

    /*
     *
     * BY EARNINGS
     *
     */
    public List<Results> getCsCountByEarnings()
    {
        List<Results> CS = jdbcTemplate.query(SQL_GET_CS_COUNT_BY_EARNINGS, csRowMaperEarnings);
        return CS;
    }
    public List<Results> getTsCountByEarnings()
    {
        List<Results> TS = jdbcTemplate.query(SQL_GET_TS_COUNT_BY_EARNINGS, tsRowMaperEarnings);
        return TS;
    }
    public List<Results> getCcCountByEarnings()
    {
        List<Results> CC = jdbcTemplate.query(SQL_GET_CC_COUNT_BY_EARNINGS, ccRowMaperEarnings);
        return CC;
    }
    public List<Results> getFacultyCountByEarnings()
    {
        List<Results> FACULTY = jdbcTemplate.query(SQL_GET_FACULTY_COUNT_BY_EARNINGS, facultyRowMaperEarnings);
        return FACULTY;
    }
    public List<Results> getTCountByEarnings()
    {
        List<Results> T = jdbcTemplate.query(SQL_GET_T_COUNT_BY_EARNINGS, tRowMaperEarnings);
        return T;
    }
    public List<Results> getFieldCountByEarnings()
    {
        List<Results> FIELD = jdbcTemplate.query(SQL_GET_FIELD_COUNT_BY_EARNINGS, fieldRowMaperEarnings);
        return FIELD;
    }


    private RowMapper<Results> csRowMaperEarnings = ((rs, rowNum) -> {
        return new Results(rs.getString("earnings"),
                rs.getString("company_size"),
                rs.getInt("count"));
    });
    private RowMapper<Results> tsRowMaperEarnings = ((rs, rowNum) -> {
        return new Results(rs.getString("earnings"),
                rs.getString("town_size"),
                rs.getInt("count"));
    });
    private RowMapper<Results> ccRowMaperEarnings = ((rs, rowNum) -> {
        return new Results(rs.getString("earnings"),
                rs.getString("company_category"),
                rs.getInt("count"));
    });
    private RowMapper<Results> facultyRowMaperEarnings = ((rs, rowNum) -> {
        return new Results(rs.getString("earnings"),
                rs.getString("faculty"),
                rs.getInt("count"));
    });
    private RowMapper<Results> tRowMaperEarnings = ((rs, rowNum) -> {
        return new Results(rs.getString("earnings"),
                rs.getString("title"),
                rs.getInt("count"));
    });
    private RowMapper<Results> fieldRowMaperEarnings = ((rs, rowNum) -> {
        return new Results(rs.getString("earnings"),
                rs.getString("field"),
                rs.getInt("count"));
    });


    /*
     *
     * BY FACULTY
     *
     */
    public List<Results> getCcCountByFaculty()
    {
        List<Results> CC = jdbcTemplate.query(SQL_GET_CC_COUNT_BY_FACULTY, ccRowMaperFaculty);
        return CC;
    }
    public List<Results> getJstCountByFaculty()
    {
        List<Results> JST = jdbcTemplate.query(SQL_GET_JST_COUNT_BY_FACULTY, jstRowMaperFaculty);
        return JST;
    }
    public List<Results> getJsCountByFaculty()
    {
        List<Results> JS = jdbcTemplate.query(SQL_GET_JS_COUNT_BY_FACULTY, jsRowMaperFaculty);
        return JS;
    }

    private RowMapper<Results> ccRowMaperFaculty = ((rs, rowNum) -> {
        return new Results(rs.getString("faculty"),
                rs.getString("company_category"),
                rs.getInt("count"));
    });
    private RowMapper<Results> jstRowMaperFaculty = ((rs, rowNum) -> {
        return new Results(rs.getString("faculty"),
                rs.getString("job_search_time"),
                rs.getInt("count"));
    });
    private RowMapper<Results> jsRowMaperFaculty = ((rs, rowNum) -> {
        return new Results(rs.getString("faculty"),
                rs.getString("job_satisfaction"),
                rs.getInt("count"));
    });
}
