package com.umg.absolwentbackend.repositories;

import com.umg.absolwentbackend.models.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class GroupRepository {
    private static final String SQL_FIND_BY_GROUP_NAME = "SELECT questionnarie_id, group_name, questionnaire_frequency, date_of_last_questionnaire FROM absolvent.groups WHERE group_name=?";
    private static final String SQL_POST = "INSERT INTO absolvent.groups(questionnarie_id, group_name, questionnaire_frequency, date_of_last_questionnaire) VALUES (?, ?, ?, ?)";
    private static final String SQL_DELETE_BY_GROUP_NAME = "DELETE FROM absolvent.groups WHERE group_name=?";
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> findByGroupName(String groupName){
        return jdbcTemplate.queryForList(SQL_FIND_BY_GROUP_NAME);
    }

    public boolean addGroup(Group group_name){
        try{
            jdbcTemplate.update(SQL_POST,group_name);
            return true;
        }catch (Exception ex){
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
}
