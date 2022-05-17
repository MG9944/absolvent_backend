package com.umg.absolwentbackend.models;

import java.sql.Date;

public class Questionnaire {

    private  String id;
    private  Graduate graduate;
    private  Date filling_date;
    private  Date sending_date;
    private Boolean filled;

    public Questionnaire(String id, Graduate graduate, Date filling_date, Date sending_date, Boolean filled) {
        this.id = id;
        this.graduate = graduate;
        this.filling_date = filling_date;
        this.sending_date = sending_date;
        this.filled = filled;
    }


    public String getId() {
        return id;
    }

    public Graduate getGraduate() {
        return graduate;
    }

    public Date getFilling_date() {
        return filling_date;
    }

    public Date getSending_date() {
        return sending_date;
    }

    public Boolean getFilled() {
        return filled;
    }


}
