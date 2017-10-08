package com.example.testtest;

import java.util.ArrayList;

/**
 * Created by Abdullah Sajjad on 10/09/2017.
 */

public class Question {
    private ArrayList<String> question;
    private Answer answer;
    private Boolean choice_Question=false;

    public Question()
    {

    }
    public Question(String q,String a)
    {
        question= new ArrayList<String>();
        this.question = new ArrayList<String>();
        this.question.add(q);
        answer = new Answer(a);
    }

    public void setQuestion(ArrayList<String> question) {
        this.question = question;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public Answer getAnswer() {
        return answer;
    }

    public Boolean getChoice_Question() {
        return choice_Question;
    }

    public void setChoice_Question(Boolean choice_Question) {
        this.choice_Question = choice_Question;
    }

    public ArrayList<String> getQuestion() {
        return question;
    }
}
