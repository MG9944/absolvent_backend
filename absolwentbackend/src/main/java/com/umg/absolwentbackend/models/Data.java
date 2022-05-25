package com.umg.absolwentbackend.models;

import java.sql.Date;

public class Data {
    private Date ending_date;
    private String gender;
    private String earnings;
    private String company_size;
    private String town_size;
    private String company_category;
    private String job_search_time;
    private String job_satisfaction;
    private String period_of_employment;
    private boolean training;
    private boolean location;
    private boolean proffesional_activity;
    private int data_id;
    private int questionnarie_id;

    public Data(Date ending_date, String gender, String earnings, String company_size, String town_size, String company_category, String job_search_time, String job_satisfaction, String period_of_employment, boolean training, boolean location, boolean proffesional_activity, int data_id, int questionnarie_id) {
        this.ending_date = ending_date;
        this.gender = gender;
        this.earnings = earnings;
        this.company_size = company_size;
        this.town_size = town_size;
        this.company_category = company_category;
        this.job_search_time = job_search_time;
        this.job_satisfaction = job_satisfaction;
        this.period_of_employment = period_of_employment;
        this.training = training;
        this.location = location;
        this.proffesional_activity = proffesional_activity;
        this.data_id = data_id;
        this.questionnarie_id = questionnarie_id;
    }

    public Date getEnding_date() {
        return ending_date;
    }

    public String getGender() {
        return gender;
    }

    public String getEarnings() {
        return earnings;
    }

    public String getCompany_size() {
        return company_size;
    }

    public String getTown_size() {
        return town_size;
    }

    public String getCompany_category() {
        return company_category;
    }

    public String getJob_search_time() {
        return job_search_time;
    }

    public String getJob_satisfaction() {
        return job_satisfaction;
    }

    public String getPeriod_of_employment() {
        return period_of_employment;
    }

    public boolean isTraining() {
        return training;
    }

    public boolean isLocation() {
        return location;
    }

    public boolean isProffesional_activity() {
        return proffesional_activity;
    }

    public int getData_id() {
        return data_id;
    }

    public int getQuestionnarie_id() {
        return questionnarie_id;
    }
}
