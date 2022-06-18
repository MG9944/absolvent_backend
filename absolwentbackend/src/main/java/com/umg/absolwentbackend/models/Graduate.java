package com.umg.absolwentbackend.models;

import java.sql.Date;

public class Graduate {

    private Integer graduate_id;
    private String name;
    private String lastname;
    private Date date_of_birth;
    private String email;
    private int graduation_year;
    private String field;
    private String title;
    private String faculty;
    private String group;
    private String gender;

    public Integer getGraduate_id() {
        return graduate_id;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public String getEmail() {
        return email;
    }

    public int getGraduation_year() {
        return graduation_year;
    }

    public String getField() {
        return field;
    }

    public String getTitle() {
        return title;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getGroup() {
        return group;
    }

    public Graduate(Integer graduate_id, String name, String lastname,String gender, Date date_of_birth, String email, int graduation_year, String field, String title, String faculty, String group) {
        this.graduate_id = graduate_id;
        this.name = name;
        this.lastname = lastname;
        this.gender=gender;
        this.date_of_birth = date_of_birth;
        this.email = email;
        this.graduation_year = graduation_year;
        this.field = field;
        this.title = title;
        this.faculty = faculty;
        this.group = group;
    }
}
