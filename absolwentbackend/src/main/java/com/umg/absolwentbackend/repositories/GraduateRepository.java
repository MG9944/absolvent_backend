package com.umg.absolwentbackend.repositories;

import com.umg.absolwentbackend.controllers.GraduateController;
import com.umg.absolwentbackend.exceptions.AuthenticationException;
import com.umg.absolwentbackend.models.Graduate;
import com.umg.absolwentbackend.models.University;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public class GraduateRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String SQL_ADD  = "INSERT INTO graduate(date_of_birth,email,name,lastname,graduation_year, faculty, field,tittle) VALUES (?,?,?,?,?,?,?,?)";
    private static final String SQL_COUNT_BY_EMAIL = "SELECT count(*) FROM graduate WHERE email=?;";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM graduate WHERE graduate_id=?";

    private static final String SQL_FIND_BY_EMAIL = "SELECT email FROM graduate WHERE email=?";
    private static final String SQL_GET_EMAILS = "SELECT email FROM absolvent.graduate Inner join absolvent.questionnaire on graduate.graduate_id=questionnaire.graduate_id WHERE questionnaire.group_name=?";
    private static final String SQL_NEXT_SENDING_DATE ="SELECT questionnaire_frequency,questionnaire_frequency FROM absolvent.graduate Inner join absolvent.questionnaire on graduate.graduate_id=questionnaire.graduate_id inner join absolvent.groups on questionnaire.group_name=absolvent.groups.group_name WHERE absolvent.groups.group_name=?";


    public Integer insertGraduate(String name, String lastName, String email, int graduation_year, String faculty, String field, Date date_of_birth,String title) throws AuthenticationException
    {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_ADD, Statement.RETURN_GENERATED_KEYS);
                ps.setDate(1,date_of_birth);
                ps.setString(2,email);
                ps.setString(3,name);
                ps.setString(4,lastName);
                ps.setInt(5, graduation_year);
                ps.setString(6,faculty);
                ps.setString(7,field);
                ps.setString(8,title);
                return ps;
            },keyHolder);
            return (Integer) keyHolder.getKeys().get("graduate_id");

       }catch (Exception e)
        {
            throw new AuthenticationException("Invalid details. Failed to add graduate");
        }
    }

    public Graduate findbyid(int id) {
        Graduate graduate = jdbcTemplate.queryForObject(SQL_FIND_BY_ID, graduateRowMapper, new Object[]{id});
        return graduate;
    }

    public Integer countbyemail(String email) {//ZW
        Integer number = jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, Integer.class, new Object[]{email});
        return number;
    }

    public List<Map<String, Object>> findGroupEmails(String group_name) {
        return jdbcTemplate.queryForList(SQL_GET_EMAILS,new Object[]{group_name});
    }

    public LocalDateTime findNextSendingDate(String group_name) {
        //To sie pewnie da zrobić inaczej,bez RowMappera XD
        LocalDateTime data = jdbcTemplate.queryForObject(SQL_NEXT_SENDING_DATE,nextDateRowMapper,new Object[]{group_name});
        return data;
    }

    private RowMapper<LocalDateTime> nextDateRowMapper = ((rs, rowNum) -> {
        int freq = rs.getInt("date_of_last_questionnaire");
        Date lastDate = rs.getDate("date_of_last_questionnaire");
        return LocalDateTime.from(lastDate.toInstant()).plusDays(freq);
    });

    private RowMapper<Graduate> graduateRowMapper = ((rs, rowNum) -> {
        return new Graduate(rs.getInt("graduate_id"),
                rs.getString("name"),
                rs.getString("lastname"),
                rs.getDate("date_of_birth"),
                rs.getString("email"),
                rs.getInt("graduation_year"),
                rs.getString("field"),
                rs.getString("tittle"),
                rs.getString("faculty"));
    });

}
