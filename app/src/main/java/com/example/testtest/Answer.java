package com.example.testtest;

/**
 * Created by Abdullah Sajjad on 10/09/2017.
 */

public class Answer {
    private String Answer;
    Question follow_up_Question = null;

    public Answer(String a)
    {
        this.Answer = a;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setFollow_up_Question(Question follow_up_Question) {
        this.follow_up_Question = follow_up_Question;
    }

    public Question getFollow_up_Question() {
        return follow_up_Question;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }
}
