package com.umg.absolwentbackend.services;

import com.umg.absolwentbackend.exceptions.AuthenticationException;
import com.umg.absolwentbackend.models.Graduate;
import com.umg.absolwentbackend.repositories.GraduateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GraduateService {

    @Autowired
    GraduateRepository graduateRepository;
    public boolean delete(int graduate_id) {

        return graduateRepository.delete(graduate_id);
    }

    public Graduate addGraduate(String name, String lastName, String email, int graduation_year, String faculty, String field, String title,String group,String gender) throws AuthenticationException {
        Integer count = graduateRepository.countbyemail(email);
        if(count>0) {
            throw new AuthenticationException("Graduate already exists");
        }
        Integer userid = graduateRepository.insertGraduate(name,lastName,email,graduation_year,faculty,field,title,group,gender);
        return graduateRepository.findbyid(userid);
    }



}
