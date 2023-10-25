package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class result_display extends AppCompatActivity {
    private ProgressBar progress_bar;
    private TextView text_view_progress,percentText,answeredText,unattempted,score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_display);
        progress_bar = findViewById(R.id.progress_bar);
        text_view_progress = findViewById(R.id.text_view_progress);
        percentText = findViewById(R.id.percentText);
        answeredText = findViewById(R.id.answeredText);
        unattempted = findViewById(R.id.unattempted);
        score = findViewById(R.id.score);


        Intent intent = getIntent();
        int marks = intent.getIntExtra("marks",0);
        int total_questions = intent.getIntExtra("total_questions",0);
        int answered = intent.getIntExtra(" answered",0);
        progress_bar.setMax(total_questions);
        progress_bar.setProgress(marks);
        text_view_progress.setText(marks+ "/" + total_questions);
        double percent = ((double) marks / total_questions) * 100.0;
        if(percent > 50){
            percentText.setText("Good job you passed the test");
        }
        else{
            percentText.setText("You failed the test , better luck next time");
        }
        answeredText.setText("Total attempted questions : "+ answered);
        unattempted.setText("Total un-attempted questions : "+ (total_questions-answered));
        score.setText("Final Score : "+ marks);

    }
}