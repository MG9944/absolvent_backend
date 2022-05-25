package com.umg.absolwentbackend.repositories;

import com.umg.absolwentbackend.exceptions.AuthenticationException;
import com.umg.absolwentbackend.models.University;

public interface UniversityRepoInterface {

        //Integer create(String name, String password, String defaultaddress) throws AuthenticationException;

        University findByLoginAndPassword(String login, String password)  throws AuthenticationException;

        //University findById(Integer userid);

}
