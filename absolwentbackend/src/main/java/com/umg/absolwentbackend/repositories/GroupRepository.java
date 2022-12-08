package com.umg.absolwentbackend.repositories;

import com.umg.absolwentbackend.exceptions.AuthenticationException;
import com.umg.absolwentbackend.models.Graduate;
import com.umg.absolwentbackend.models.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class GroupRepository {
    private static final String SQL_FIND_BY_GROUP_NAME = "SELECT group_name, questionnaire_frequency, date_of_last_questionnaire FROM absolvent.groups WHERE group_name=?";

    private static final String SQL_FIND_NAME = "SELECT group_name FROM absolvent.groups WHERE group_name=?";
    private static final String SQL_POST = "INSERT INTO absolvent.groups(group_name, questionnaire_frequency) VALUES (?, ?)";
    private static final String SQL_DELETE_BY_GROUP_NAME = "DELETE FROM absolvent.groups WHERE group_name=?";
    private static final String SQL_NEXT_SENDING_DATE ="SELECT questionnaire_frequency,questionnaire_frequency FROM absolvent.graduate inner join absolvent.groups on graduate.group_name=absolvent.groups.group_name WHERE absolvent.groups.group_name=?";
    private static final String SQL_GET_NAME = "SELECT group_name FROM absolvent.groups";
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> findByGroupName(String groupName){
        return jdbcTemplate.queryForList(SQL_FIND_BY_GROUP_NAME, groupName);
    }

    public List<Group> getAllGroup() {
        List<Group> group = jdbcTemplate.query(SQL_GET_NAME, groupNameRowMapper);
        return group;
    }

    public Group findByName(String groupName)  throws AuthenticationException
    {
        try{
            Group group = jdbcTemplate.queryForObject(SQL_FIND_NAME, groupNameRowMapper,new Object[]{groupName});
            if(!groupName.equals(group.getGroupName()))
                throw  new AuthenticationException("Invalid group name");
            return group;
        }
        catch (EmptyResultDataAccessException e)
        {
            System.out.println(e);
            throw  new AuthenticationException("Invalid group name2");
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

    private final RowMapper<Group> groupNameRowMapper = ((rs, rowNum) -> {
        return new Group(rs.getString("group_name"));
    });
}
