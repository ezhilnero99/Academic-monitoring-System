package com.example.acmod.models;

import com.google.firebase.firestore.GeoPoint;

public class question_details {
    //1 - yes or no question
    //2 - rating questions
    //3 - other answers
    String question , numb , type;

    public question_details(String question, String numb, String type) {
        this.question = question;
        this.numb = numb;
        this.type = type;
    }

    public question_details() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getNumb() {
        return numb;
    }

    public void setNumb(String numb) {
        this.numb = numb;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
