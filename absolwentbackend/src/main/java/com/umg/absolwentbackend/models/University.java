package com.umg.absolwentbackend.models;

import java.sql.Date;

public class University {
    private String password;
    private Date dateOfLastQuestionnaire;
    private String questionnaireFrequency;
    private String login;


    public University(String password, Date dateOfLastQuestionnaire, String questionnaireFrequency, String login) {
        this.password = password;
        this.dateOfLastQuestionnaire = dateOfLastQuestionnaire;
        this.questionnaireFrequency = questionnaireFrequency;
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public Date getDateOfLastQuestionnaire() {
        return dateOfLastQuestionnaire;
    }

    public String getQuestionnaireFrequency() {
        return questionnaireFrequency;
    }

    public String getLogin() {
        return login;
    }
}
