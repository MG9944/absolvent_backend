package com.umg.absolwentbackend.services;

import com.umg.absolwentbackend.exceptions.AuthenticationException;
import com.umg.absolwentbackend.models.University;
import com.umg.absolwentbackend.repositories.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UniveristyService {

    @Autowired
    UniversityRepository universityRepository;

    public University validateUniversity(String login, String password) throws AuthenticationException {
        return universityRepository.findByLoginAndPassword(login,password);
    }
}
