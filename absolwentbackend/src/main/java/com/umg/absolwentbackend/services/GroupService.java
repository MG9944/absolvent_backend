package com.umg.absolwentbackend.services;

import com.umg.absolwentbackend.models.Group;
import com.umg.absolwentbackend.repositories.GraduateRepository;
import com.umg.absolwentbackend.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class GroupService {

    @Autowired
    GroupRepository groupRepository;

    public List<Map<String, Object>> findByGroupName(String groupName){
        return groupRepository.findByGroupName(groupName);
    }

    public boolean addGroup(Group group_name){
        return groupRepository.addGroup(group_name);
    }

    public boolean deleteGroup(String group_name){
        return groupRepository.deleteGroup(group_name);
    }
}
