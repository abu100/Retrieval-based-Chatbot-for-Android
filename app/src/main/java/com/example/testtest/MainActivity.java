package com.example.testtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.jdom2.*;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<Question> Questions = new ArrayList<Question>();

    Button ask;
    EditText ques;
    TextView Ans;
    Chatbot my_chatbot = new Chatbot();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ask = (Button)findViewById(R.id.ask);
        ques = (EditText) findViewById(R.id.q);
        Ans = (TextView) findViewById(R.id.a);

        try {
            InputStream inputFile = this.getResources().openRawResource(R.raw.chatbotconciousness);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("Question");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("Question : " + eElement.getAttributes().getNamedItem("q").getNodeValue());
                    System.out.println("Answer : " + eElement.getAttributes().getNamedItem("a").getNodeValue());

                    Questions.add(new Question(eElement.getAttribute("q"),eElement.getAttribute("a")));


                    if (eElement.getElementsByTagName("Follow_up_Question").getLength() !=0)
                    {
                        get_Next_Level(    Questions.get(temp),eElement.getChildNodes() );
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // We have the data.
        my_chatbot.initialise_Bot(Questions);

        ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ans.setText(my_chatbot.ask_Question(ques.getText().toString()));
            }
        });



    }


    private void get_Next_Level(Question ques,NodeList nNode) {
        int length = nNode.getLength();
        for (int temp = 0; temp < length; temp++) {
            Node nod = nNode.item(temp);
            System.out.println("\nCurrent Element :" + nod.getNodeName());

            if (nod.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nod;
                System.out.println("Follow_up_Question : " + eElement.getAttribute("f"));
                System.out.println("Answer : " + eElement.getAttribute("a"));

                ques.getAnswer().setFollow_up_Question(new Question(eElement.getAttribute("f"),eElement.getAttribute("a")));

                if (eElement.getElementsByTagName("Follow_up_Question").getLength() !=0)
                {
                    get_Next_Level(ques.getAnswer().getFollow_up_Question(),eElement.getChildNodes() );
                }
            }
        }
    }
}
