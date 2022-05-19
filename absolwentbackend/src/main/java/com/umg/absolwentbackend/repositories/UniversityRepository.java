package com.umg.absolwentbackend.repositories;

import com.umg.absolwentbackend.exceptions.AuthenticationException;
import com.umg.absolwentbackend.models.University;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

@Repository
public class UniversityRepository implements UniversityRepoInterface {

    private static final String SQL_FIND_BY_NAME = "SELECT password, date_of_last_questionnaire, questionnaire_frequency, login FROM university WHERE login=?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public University findByLoginAndPassword(String login, String password)  throws AuthenticationException
    {
        try{
            //System.out.println(BCrypt.hashpw(password,BCrypt.gensalt(10)));
            University university = jdbcTemplate.queryForObject(SQL_FIND_BY_NAME, universityRowMapper, new Object[]{login});
            if(!BCrypt.checkpw(password, university.getPassword())  /*!password.equals(university.getPassword())*/)
                throw  new AuthenticationException("Invalid name/password");
            return university;
        }
        catch (EmptyResultDataAccessException e)
        {
            throw  new AuthenticationException("Invalid name/password");
        }
    }

    private RowMapper<University> universityRowMapper = ((rs, rowNum) -> {
        return new University(rs.getString("password"),
                rs.getDate("date_of_last_questionnaire"),
                rs.getString("questionnaire_frequency"),
                rs.getString("login"));
    });

}
