package com.umg.absolwentbackend.repositories;

import com.umg.absolwentbackend.controllers.GraduateController;
import com.umg.absolwentbackend.exceptions.AuthenticationException;
import com.umg.absolwentbackend.models.Graduate;
import com.umg.absolwentbackend.models.Results;
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

    private static final String SQL_ADD  = "INSERT INTO graduate(email,name,lastname,graduation_year, faculty, field,title,group_name,gender) VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String SQL_COUNT_BY_EMAIL = "SELECT count(*) FROM graduate WHERE email=?;";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM graduate WHERE graduate_id=?";
    private static final String SQL_FIND_BY_EMAIL = "SELECT email FROM graduate WHERE email=?";
    private static final String SQL_GET_EMAILS = "SELECT email,graduation_year,field,faculty,title,gender  FROM absolvent.graduate where group_name=?";

    private static final String SQL_GET_GRADUATES = "SELECT date_of_birth, email, name, lastname, graduation_year, faculty, field, title, graduate_id, group_name, gender FROM absolvent.graduate";
    private static final String SQL_DELETE_GRADUATE = "DELETE FROM absolvent.graduate WHERE graduate.graduate_id=?";

    public Integer insertGraduate(String name, String lastName, String email, int graduation_year, String faculty, String field,String title,String group,String gender) throws AuthenticationException
    {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_ADD, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,email);
                ps.setString(2,name);
                ps.setString(3,lastName);
                ps.setInt(4, graduation_year);
                ps.setString(5,faculty);
                ps.setString(6,field);
                ps.setString(7,title);
                ps.setString(8,group);
                ps.setString(9,gender);
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
        return jdbcTemplate.queryForList(SQL_GET_EMAILS,group_name);
    }

    public List<Graduate> getAll() {
        List<Graduate> graduate = jdbcTemplate.query(SQL_GET_GRADUATES, graduateRowMapper);
        return graduate;
    }

    public boolean delete(int graduate_id) {
        try {
            jdbcTemplate.update(SQL_DELETE_GRADUATE,graduate_id);
            return true;
        }catch (Exception ex){
            return false;
        }
    }
    private RowMapper<Graduate> graduateRowMapper = ((rs, rowNum) -> {
        return new Graduate(rs.getInt("graduate_id"),
                rs.getString("name"),
                rs.getString("lastname"),
                rs.getString("gender"),
                rs.getString("email"),
                rs.getInt("graduation_year"),
                rs.getString("field"),
                rs.getString("title"),
                rs.getString("faculty"),
                rs.getString("group_name"));
    });
}
