package com.umg.absolwentbackend.services;

import com.umg.absolwentbackend.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GroupService {

    @Autowired
    GroupRepository groupRepository;

    public List<Map<String, Object>> findByGroupName(String groupName){
        return groupRepository.findByGroupName(groupName);
    }

    public boolean addGroup(String group_name, Integer questionnaireFrequency){
        return groupRepository.addGroup(group_name,questionnaireFrequency );
    }

    public boolean deleteGroup(String group_name){
        return groupRepository.deleteGroup(group_name);
    }
}
