package com.umg.absolwentbackend.repositories;

import com.umg.absolwentbackend.exceptions.AuthenticationException;
import com.umg.absolwentbackend.models.Group;
import com.umg.absolwentbackend.models.University;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class GroupRepository {
    private static final String SQL_FIND_BY_GROUP_NAME = "SELECT group_name, questionnaire_frequency, date_of_last_questionnaire FROM absolvent.groups WHERE group_name=?";

    private static final String SQL_FIND_NAME = "SELECT group_name FROM absolvent.groups WHERE group_name=?";
    private static final String SQL_POST = "INSERT INTO absolvent.groups(group_name, questionnaire_frequency) VALUES (?, ?)";
    private static final String SQL_DELETE_BY_GROUP_NAME = "DELETE FROM absolvent.groups WHERE group_name=?";
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> findByGroupName(String groupName){
        return jdbcTemplate.queryForList(SQL_FIND_BY_GROUP_NAME, groupName);
    }

    public Group findByName(String groupName)  throws AuthenticationException
    {
        try{
            Group group = jdbcTemplate.queryForObject(SQL_FIND_NAME, groupNameRowMapper, new Object[]{groupName});
            if(!groupName.equals(group.getGroupName()))
                throw  new AuthenticationException("Invalid group name");
            return group;
        }
        catch (EmptyResultDataAccessException e)
        {
            throw  new AuthenticationException("Invalid group name");
        }
    }

    public boolean addGroup(String group_name,Integer questionnaireFrequency) {
        try {
            int t = jdbcTemplate.update(SQL_POST, group_name, questionnaireFrequency);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    public boolean deleteGroup(String group_name){
        try{
            jdbcTemplate.update(SQL_DELETE_BY_GROUP_NAME,group_name);
            return true;
            }catch(Exception ex){
            return false;
        }
    }

    private final RowMapper<Group> groupNameRowMapper = ((rs, rowNum) -> {
        return new Group(rs.getString("group_name"));
    });
}
