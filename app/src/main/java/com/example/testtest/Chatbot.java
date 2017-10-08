package com.example.testtest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import info.debatty.java.stringsimilarity.*;


/**
 * Created by Abdullah Sajjad on 10/09/2017.
 */

public class Chatbot {
    ArrayList<Question> Questions;
    boolean loaded = false;
    ArrayList<Question> Branches = new ArrayList<Question>();
    Levenshtein l = new Levenshtein();
    String temp = null;
    Question tempq = null;
    int threshold=0;
    boolean semantics = false;

    public Chatbot()
    {
        // read a text file and load Questions,answers and follow up questions into Questions arraylist
    }
    public void initialise_Bot(ArrayList<Question> wow,int limit)
    {
        this.Questions = wow;
        this.loaded = true;

        if (limit<1)
            this.threshold = 1;
        else
            this.threshold = limit;

    }
    public String reply(String q)
    {

        ArrayList<Question> selected_top = new ArrayList<>();
        temp = null;

        double record = simmetric(Branches.get(0).getQuestion().get(0),q);
        for(int i = 0;i<Branches.size();i++) {
            for(int j=0;j<Branches.get(i).getQuestion().size();j++) {
                if(simmetric(Branches.get(i).getQuestion().get(j),q) < record) {
                    record = simmetric(Branches.get(i).getQuestion().get(j),q);
                    // deal with multiple matches in a single array
                    j = Branches.get(i).getQuestion().size()+1;
                    temp =  Branches.get(i).getAnswer().getAnswer();
                    store(Branches.get(i),selected_top);
                    if(Branches.get(i).getAnswer().getFollow_up_Question() !=null
                            && !exists_In(Branches,Branches.get(i).getAnswer().follow_up_Question)) {

                        Branches.get(i).setQuestion(Branches.get(i).getAnswer().getFollow_up_Question().getQuestion());  // remembering a step further
                        Branches.get(i).setAnswer(Branches.get(i).getAnswer().getFollow_up_Question().getAnswer());
                    }
                }
            }
        }
        double record_plain = simmetric(Questions.get(0).getQuestion().get(0), q);
        if (temp==null) {
            for (int i = 0; i < Questions.size(); i++) {
                for (int j = 0; j < Questions.get(i).getQuestion().size(); j++) {
                    if (simmetric(Questions.get(i).getQuestion().get(j), q) < record_plain) {
                        record_plain = simmetric(Questions.get(i).getQuestion().get(j), q);
                        // deal with multiple matches in a single array
                        j = Branches.get(i).getQuestion().size()+1;
                        temp = Questions.get(i).getAnswer().getAnswer();
                        store(Questions.get(i),selected_top);

                        if(!exists_In(Branches,Questions.get(i).getAnswer().follow_up_Question))
                        tempq = Questions.get(i).getAnswer().follow_up_Question;  // remembers what was asked
                    }
                }
            }
        }
        if(tempq!=null)
            Branches.add(tempq);
        return temp;
    }





    private void store(Question temp,ArrayList<Question> top) {
        if (top.size()>threshold && threshold!=0)
        {
            top.remove(0);
            top.add(temp);
        }
        else top.add(temp);
    }

    private boolean exists_In(ArrayList<Question> branches, Question follow_up_question) { // checks if we have already added the node in the list or not
        for(int i=0;i<branches.size();i++)
        {
            if (branches.get(i).getAnswer().getAnswer().equals(follow_up_question.getAnswer().getAnswer()))
            {
                return true;
            }
        }
        return false;

    }


    public String ask_Question(String question)
    {
        return (reply(question));
    }
    public double simmetric(String a,String b)
    {
        return l.distance(a, b);
    }


}
