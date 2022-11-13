package com.umg.absolwentbackend.models;

public class Results {

    Integer filling_year;
    String stringValue;
    Boolean boolValue;
    Integer count;
    String stringKey;


    public String getStringKey() {
        return stringKey;
    }
    public Integer getFilling_year() {
        return filling_year;
    }

    public String getStringValue() {
        return stringValue;
    }

    public Boolean getBoolValue() {
        return boolValue;
    }

    public Integer getCount() {
        return count;
    }

    public Results(Integer filling_year, Boolean boolValue, Integer count)
    {
        this.filling_year = filling_year;
        this.boolValue = boolValue;
        this.count = count;
    }

    public Results(Integer filling_year, String stringValue, Integer count)
    {
        this.filling_year = filling_year;
        this.stringValue = stringValue;
        this.count = count;
    }
    public Results(String stringKey, String stringValue, Integer count)
    {
        this.stringKey = stringKey;
        this.stringValue = stringValue;
        this.count = count;
    }

    public Results(String stringKey, Boolean boolValue, Integer count)
    {
        this.stringKey = stringKey;
        this.boolValue = boolValue;
        this.count = count;
    }

}
