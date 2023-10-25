package com.example.quizapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionDisplayActivity extends AppCompatActivity {
    private List<Question> questionList;
    private Button previousButton,nextButton;
    private ViewSwitcher viewSwitcher;
    private Button submitButton;
    private int currentQuestionIndex;
    int marks,total_questions,answered;
    private CountDownTimer quizTimer;
    private static final long TOTAL_QUIZ_TIME_IN_MILLIS = 600000;
    private static final long COUNTDOWN_INTERVAL = 1000;
    private TextView timerTextView;
    private long timeLeftInMillis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_display);
        viewSwitcher = findViewById(R.id.questionViewSwitcher);

        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);
        submitButton = findViewById(R.id.submitButton);
        timerTextView = findViewById(R.id.questionTimerTextView); // Make sure you have this TextView in your XML layout
        timeLeftInMillis = TOTAL_QUIZ_TIME_IN_MILLIS;
        startQuizTimer();


        questionList = new ArrayList<>();
        currentQuestionIndex = 0;
        marks = 0;
        answered = 0;
        total_questions = 0;
        questionList.add(new Question("What is the atomic number of carbon?", Arrays.asList("3","4","5","6"),3));
        questionList.add(new Question("In the world of chemistry, what is the chemical element with the symbol \"Fe\" and the atomic number 26, widely recognized for its presence in red blood cells and its role in oxygen transport in the human body?", Arrays.asList("Iron","Gold","Silver","Platinum"),0));
        questionList.add(new Question("Which functional group is commonly found in organic compounds that contain a carbonyl group (C=O) and has the general formula R-C(=O)-OH?", Arrays.asList("Aldehyde","Ketone","Ester"," Carboxylic Acid"),3));
        questionList.add(new Question("Chemical kinetics is the study of reaction rates. Which factor does not affect the rate of a chemical reaction?", Arrays.asList("Temperature","Concentration of reactants"," Presence of a catalyst"," Color of the reactants"),3));
        questionList.add(new Question("In an electrochemical cell, oxidation occurs at the anode, and reduction occurs at the cathode. Which of the following half-reactions represents an oxidation process?", Arrays.asList(" 2H+ + 2e- → H2"," Cu2+ + 2e- → Cu","2Cl- → Cl2 + 2e-"," 2H2O + 2e- → H2 + 2OH-"),2));
        questionList.add(new Question("Which subatomic particle has a negative charge?", Arrays.asList("Proton","Neutron","Electron","Positron"),2));
        questionList.add(new Question("When a chemical reaction reaches equilibrium, the concentrations of reactants and products remain constant. Which statement is true regarding a dynamic chemical equilibrium?", Arrays.asList("The rate of the forward reaction is equal to the rate of the reverse reaction.","The concentration of reactants is always higher than that of products."," Equilibrium can only be achieved in closed systems.","Equilibrium reactions are irreversible."),0));
        questionList.add(new Question("In a covalent bond, electrons are shared between atoms. However, in a coordinate covalent bond, one atom donates both electrons to the bond. Which of the following compounds contains a coordinate covalent bond?", Arrays.asList(" Methane (CH4)"," Ammonium ion (NH4+)"," Water (H2O)","Hydrogen chloride (HCl)"),1));
        questionList.add(new Question("Which of the following is a noble gas?", Arrays.asList("Oxygen","Neon","Sodium","Chlorine"),1));
        questionList.add(new Question("What is the pH of a neutral solution?", Arrays.asList("7","0","14","1"),0));

        displayQuestion(currentQuestionIndex);


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentQuestionIndex < questionList.size() - 1) {
                    currentQuestionIndex++;
                    displayQuestion(currentQuestionIndex);
                }
                if(currentQuestionIndex==questionList.size()-1){
                    nextButton.setVisibility(View.GONE);
                    submitButton.setVisibility(View.VISIBLE);
                }else {
                    submitButton.setVisibility(View.GONE);
                }
                previousButton.setVisibility(View.VISIBLE);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QuestionDisplayActivity.this);
                builder.setMessage("Are you sure you want to submit the test?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //here is the submission logic
                        for(Question question:questionList){
                            if(question.isAnswered()){
                                answered++;
                                if(question.getUserAnswer() == question.getCorrectAnswer()){
                                    marks++;
                                }
                            }
                        }
//                        Toast.makeText(QuestionDisplayActivity.this, "Time completed", Toast.LENGTH_SHORT).show();
                        total_questions = questionList.size();
                        Intent intent = new Intent(QuestionDisplayActivity.this, result_display.class);
                        intent.putExtra("marks",marks);
                        intent.putExtra("total_questions",total_questions);
                        intent.putExtra(" answered", answered);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //user canceled the submission
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentQuestionIndex>0){
                    currentQuestionIndex--;
                    displayQuestion(currentQuestionIndex);
                    nextButton.setVisibility(View.VISIBLE);
                    submitButton.setVisibility(View.GONE);
                }
                if(currentQuestionIndex == 0) {
                    previousButton.setVisibility(View.GONE);
                }
            }
        });
    }

    private void displayQuestion(int index){
        View questionView = getLayoutInflater().inflate(R.layout.question_model_design, null);
        TextView questionTextView = questionView.findViewById(R.id.questionTextView);
        TextView questionNumber = questionView.findViewById(R.id.questionNumber);
        RadioButton option1RadioButton = questionView.findViewById(R.id.option1);
        RadioButton option2RadioButton = questionView.findViewById(R.id.option2);
        RadioButton option3RadioButton = questionView.findViewById(R.id.option3);
        RadioButton option4RadioButton = questionView.findViewById(R.id.option4);
        Question question = questionList.get(index);
        questionTextView.setText(question.getQuestionText());
        questionNumber.setText("Question "+ (currentQuestionIndex + 1)+" of "+questionList.size());
        option1RadioButton.setText(question.getOptions().get(0));
        option2RadioButton.setText(question.getOptions().get(1));
        option3RadioButton.setText(question.getOptions().get(2));
        option4RadioButton.setText(question.getOptions().get(3));

        int userAnswer = question.getUserAnswer();
        if (userAnswer >= 0 && userAnswer <= 3) {
            switch (userAnswer) {
                case 0:
                    option1RadioButton.setChecked(true);
                    break;
                case 1:
                    option2RadioButton.setChecked(true);
                    break;
                case 2:
                    option3RadioButton.setChecked(true);
                    break;
                case 3:
                    option4RadioButton.setChecked(true);
                    break;
            }
        }

        option1RadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question.setUserAnswer(0);
            }
        });
        option2RadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question.setUserAnswer(1);
            }
        });
        option3RadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question.setUserAnswer(2);
            }
        });
        option4RadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question.setUserAnswer(3);
            }
        });

        viewSwitcher.removeAllViews();
        viewSwitcher.addView(questionView);
    }

    private void startQuizTimer(){
        quizTimer = new CountDownTimer(timeLeftInMillis, COUNTDOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimerUI();
            }

            @Override
            public void onFinish() {
                AlertDialog.Builder builder = new AlertDialog.Builder(QuestionDisplayActivity.this);
                builder.setCancelable(false);
                builder.setMessage("Time limit exceeded");
                builder.setPositiveButton("Show Results", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //here is the submission logic if time limit exceeds
                        for(Question question:questionList){
                            if(question.isAnswered()){
                                answered++;
                                if(question.getUserAnswer() == question.getCorrectAnswer()){
                                    marks++;
                                }
                            }
                        }
//                        Toast.makeText(QuestionDisplayActivity.this, "Time completed", Toast.LENGTH_SHORT).show();
                        total_questions = questionList.size();
                        Intent intent = new Intent(QuestionDisplayActivity.this, result_display.class);
                        intent.putExtra("marks",marks);
                        intent.putExtra("total_questions",total_questions);
                        intent.putExtra(" answered", answered);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.show();
            }
        }.start();
    }

    private void updateTimerUI(){
        int minutes = (int) (timeLeftInMillis / 60000);
        int seconds = (int) (timeLeftInMillis % 60000 / 1000);
        timerTextView.setText("Time Left: " + minutes + "m " + seconds + "s");
    }
}