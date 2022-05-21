<<<<<<< HEAD
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
=======
package com.umg.absolwentbackend.models;


public class University {
    private String password;
    private Date dateOfLastQuestionnaire;
    private String questionnaireFrequency;
    private String login;


    public University(String password,String login) {
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
>>>>>>> 99b24c53340dc4a62e0c191c18dd46d052843e37
