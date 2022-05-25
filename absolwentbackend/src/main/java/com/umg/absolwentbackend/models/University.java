package com.umg.absolwentbackend.models;


public class University {
    private String password;
    private String login;


    public University(String password, String login) {
        this.password = password;
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }
}
    