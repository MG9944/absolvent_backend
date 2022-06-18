package com.umg.absolwentbackend.models;

public class Group {
    private Integer questionnaireFrequency;
    private  String groupName;
    private Integer questionnaireId;

    private Data dataOfLastQuestionnaire;


    public Group(String groupName){
        this.groupName = groupName;
    }

    public  String getGroupName(){
        return groupName;
    }

    public Integer getQuestionnaireFrequency() {
        return questionnaireFrequency;
    }

    public Integer getQuestionnaireId() {
        return questionnaireId;
    }

    public Data getDataOfLastQuestionnaire() {
        return dataOfLastQuestionnaire;
    }
}
