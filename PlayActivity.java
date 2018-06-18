package com.folashocky.add_subtract_multiply;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayActivity extends AppCompatActivity {
    private String category;
    private Bundle sendingBundle;
    private Random random;
    private ArrayList<String> RandomString;
    private String difficulty;
    private TextView txtNum1,txtNum2,txtOperator;
    private EditText etxtAnswer;
    private TextView txtCorrect,txtIncorrect,txtCountDown;
    private Button btnConfirm;
    private Integer numOne, numTwo;
    private RotateAnimation animRotateBegin;
    private AlphaAnimation animFadeOut,animFadeIn;
    private int actualAnswer;
    private Random otherRandom;
    private int correct,incorrect;
    private int QuestionCount,QuestionNumber;
    private TextView txtQuestion;
    private Intent intent;
    private Bundle resultsBundle;
    private List<Integer> numberlist=new ArrayList<>(1);
    private int userAnswer;
    private SharedPreferences sharedPreferences;
    private int minutes,Seconds;
    private TextView[] textViews;
    private Long minasLong;
    private TextView txtTime;
    public static CountDownTimer timer;


    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_play);
        sendingBundle = getIntent().getExtras();
        category = sendingBundle.getString("category");
        difficulty = sendingBundle.getString("difficulty");
        txtNum1 = (TextView) findViewById(R.id.txtNum1);
        txtNum2 = (TextView) findViewById(R.id.txtNum2);
        txtOperator = (TextView) findViewById(R.id.txtOperator);
        etxtAnswer = (EditText) findViewById(R.id.etxtAnswer);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        txtCorrect = (TextView) findViewById(R.id.txtCorrect);
        txtIncorrect = (TextView) findViewById(R.id.txtIncorrect);
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtCountDown = (TextView) findViewById(R.id.txtCountDown);
        textViews = new TextView[]{txtNum1, txtNum2, txtOperator, etxtAnswer, txtCorrect, txtIncorrect, txtQuestion, txtTime};
        random = new Random();
        RandomString = new ArrayList<>(1);

        sharedPreferences = getSharedPreferences("Stats", MODE_PRIVATE);
        minutes = sharedPreferences.getInt("minutes", 1);


        btnConfirm.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);

        animRotateBegin = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animFadeIn = new AlphaAnimation(0f, 1f);
        animFadeOut = new AlphaAnimation(0f, 1f);

        QuestionNumber = 1;
        correct = 0;
        incorrect = 0;
        intent = new Intent(PlayActivity.this, ResultsActivity.class);
        txtIncorrect.setText("Incorrect: ");
        txtCorrect.setText("Correct: ");
        actualAnswer = 0;

        otherRandom = new Random();
        DetermineAnswer();
        GenerateQuestion();
        initialiseAnswer();

        resultsBundle = new Bundle();
        etxtAnswer.setHintTextColor(Color.WHITE);
        animRotateBegin.setDuration(1000);
        animRotateBegin.setRepeatCount(0);
        animFadeIn.setDuration(2000);
        animFadeOut.setDuration(2000);
        for (TextView textView : textViews) {
            textView.setVisibility(View.INVISIBLE);
        }
        btnConfirm.setEnabled(false);
        btnConfirm.setVisibility(View.INVISIBLE);
        final Animation anim = AnimationUtils.loadAnimation(this, R.anim.animscale);

        if (txtTime.getVisibility() == View.VISIBLE) {
            txtNum1.startAnimation(animRotateBegin);
            txtOperator.startAnimation(animRotateBegin);
            txtNum2.startAnimation(animRotateBegin);
        }
        QuestionCount = sharedPreferences.getInt("minutes", 1) * 20;
        txtQuestion.setText("Question: " + String.valueOf(QuestionNumber) + "/" + String.valueOf(QuestionCount));
        minasLong = Long.parseLong(String.valueOf(minutes));
        switch (difficulty) {
            case "Easy":
                txtNum1.setTextSize(80);
                txtNum2.setTextSize(80);
                txtOperator.setTextSize(80);
            break;
            case "Medium":
                txtNum1.setTextSize(70);
                txtNum2.setTextSize(70);
                txtOperator.setTextSize(70);
            break;

            case"Hard":
                txtNum1.setTextSize(60);
                txtNum2.setTextSize(60);
                txtOperator.setTextSize(60);
            break;

        }
        timer = new CountDownTimer(minasLong * 60000, 500) {
            @Override
            public void onTick(long millisUntilFinished) {

                minasLong = millisUntilFinished;
                minutes = (int) (minasLong / 60000);
                Seconds = (int) (minasLong % 60000 / 1000);
                txtTime.setText("Time: " + String.valueOf(minutes) + ":" + String.valueOf(Seconds));

                if (Seconds < 10) {
                    txtTime.setText("Time: " + String.valueOf(minutes) + ":0" + String.valueOf(Seconds));
                }
                if (Seconds <= -1 && minutes > -1) {
                    Seconds = 59;
                    minutes--;
                }
            }

            @Override
            public void onFinish() {

                intent.putExtras(sendingBundle);
                resultsBundle.putString("difficulty", difficulty);
                resultsBundle.putString("category", category);
                resultsBundle.putInt("correct", correct);
                resultsBundle.putInt("incorrect", incorrect);

                intent.putExtras(resultsBundle);
                this.cancel();

                startActivity(intent);
            }
        };
        final CountDownTimer oneSecondTimer = new CountDownTimer(500, 500) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                this.cancel();
                timer.start();
            }
        };

        CountDownTimer threeSecondTimer = new CountDownTimer(5000, 1000) {
            int seconds = 3000;

            @Override
            public void onTick(long millisUntilFinished) {
                seconds = (int) (millisUntilFinished % 60000 / 1000);
                txtCountDown.startAnimation(anim);
                txtCountDown.setText(String.valueOf(seconds - 1));
                if (seconds == 1) 
                   txtCountDown.setText("GO!");
            }

            @Override
            public void onFinish() {

                txtCountDown.setVisibility(View.INVISIBLE);
                txtCountDown.setEnabled(false);

                for (TextView textView : textViews)
                    textView.setVisibility(View.VISIBLE);
                
                btnConfirm.setEnabled(true);
                btnConfirm.setVisibility(View.VISIBLE);
                this.cancel();
                oneSecondTimer.start();
            }
        };

        threeSecondTimer.start();

        etxtAnswer.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onClickLogic();
                    handled = true;
                }
                return handled;
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                onClickLogic();
            }
        });
    }



    private void onClickLogic() {
       if(!etxtAnswer.getText().toString().equals(""))
           userAnswer = Integer.parseInt(etxtAnswer.getText().toString());
       else {
            while (userAnswer == actualAnswer) {
                userAnswer = otherRandom.nextInt(1);
                if (userAnswer != actualAnswer) {
                    etxtAnswer.setText(String.valueOf(userAnswer));
                    break;
                }
            }
        }

        if (!etxtAnswer.getText().toString().equals(""))
            etxtAnswer.setText("");
        QuestionNumber++;
        txtQuestion.setText("Question: " + String.valueOf(QuestionNumber) + "/" + String.valueOf(QuestionCount));
        txtNum1.startAnimation(animFadeOut);
        txtNum2.startAnimation(animFadeOut);
        txtOperator.startAnimation(animFadeOut);

        if (animFadeOut.hasEnded()) {
            txtNum1.startAnimation(animFadeIn);
            txtNum2.startAnimation(animFadeIn);
            txtOperator.startAnimation(animFadeIn);
        }

        if (userAnswer == getActualAnswer()) {
            correct++;
            txtCorrect.setText("Correct: " + String.valueOf(correct));
            numberlist.clear();
            etxtAnswer.setText("");
        }
        else if (userAnswer != getActualAnswer()) {
            incorrect++;
            txtIncorrect.setText("Incorrect: " + String.valueOf(incorrect));
            numberlist.clear();
            etxtAnswer.setText("");
        }
        if (QuestionNumber == QuestionCount + 1) {
            resultsBundle.putInt("correct", correct);
            resultsBundle.putInt("incorrect", incorrect);
            resultsBundle.putString("difficulty", difficulty);
            resultsBundle.putString("category", category);
            txtQuestion.setText("");
            intent.putExtras(resultsBundle);
            timer.cancel();

            startActivity(intent);
        }
        RandomString.clear();
        DetermineAnswer();
        GenerateQuestion();
        initialiseAnswer();
        userAnswer = Integer.MIN_VALUE;
    }


    private void DetermineAnswer() {
        Random random = new Random();
        int value = random.nextInt(3);
        if(category.equals("Random")) {
            if(value == 0)
                RandomString.add("Add");
            else if(value == 1)
                RandomString.add("Subtract");
            else if(value == 2)
                RandomString.add("Multiply");
        }
    }
    private void GenerateQuestion() {
        if(category.equalsIgnoreCase("Add")) {
            txtOperator.setText("+");
            if(difficulty.equalsIgnoreCase("Easy")) {
                numOne = random.nextInt(20);
                numTwo = random.nextInt(20);
                txtNum1.setText(String.valueOf(numOne));
                txtNum2.setText(String.valueOf(numTwo));
                txtNum1.setTextSize(80);
                txtNum2.setTextSize(80);
                txtOperator.setTextSize(80);
            }
            if (difficulty.equalsIgnoreCase("Medium")) {
                numOne = random.nextInt(100)+100;
                numTwo = random.nextInt(100)+100;
                txtNum1.setText(String.valueOf(numOne));
                txtNum2.setText(String.valueOf(numTwo));
                txtNum1.setTextSize(70);
                txtNum2.setTextSize(70);
                txtOperator.setTextSize(70);
            }
            if (difficulty.equalsIgnoreCase("Hard")) {
                numOne = random.nextInt(1000)+500;
                numTwo = random.nextInt(1000)+500;
                txtNum1.setText(String.valueOf(numOne));
                txtNum2.setText(String.valueOf(numTwo));
                txtNum1.setTextSize(60);
                txtNum2.setTextSize(60);
                txtOperator.setTextSize(60);
            }
        }
        if (category.equalsIgnoreCase("Subtract")) {
            txtOperator.setText("-");
            if(difficulty.equalsIgnoreCase("Easy")) {
                numOne = random.nextInt(20);
                numTwo = random.nextInt(20);
                txtNum1.setText(String.valueOf(numOne));
                txtNum2.setText(String.valueOf(numTwo));
            }
            if (difficulty.equalsIgnoreCase("Medium")) {
                numOne = random.nextInt(100)+100;
                numTwo = random.nextInt(100)+100;
                txtNum1.setText(String.valueOf(numOne));
                txtNum2.setText(String.valueOf(numTwo));
            }
            if (difficulty.equalsIgnoreCase("Hard")) {
                numOne = random.nextInt(1000)+500;
                numTwo = random.nextInt(1000)+500;
                txtNum1.setText(String.valueOf(numOne));
                txtNum2.setText(String.valueOf(numTwo));
            }

        }
      if (category.equalsIgnoreCase("Multiply")) {
            txtOperator.setText("x");
            if(difficulty.equalsIgnoreCase("Easy")) {
                numOne = random.nextInt(20);
                numTwo = random.nextInt(20);
                txtNum1.setText(String.valueOf(numOne));
                txtNum2.setText(String.valueOf(numTwo));

            }
            if (difficulty.equalsIgnoreCase("Medium")) {
                numOne = random.nextInt(100)+100;
                numTwo = random.nextInt(100)+100;
                txtNum1.setText(String.valueOf(numOne));
                txtNum2.setText(String.valueOf(numTwo));

            }
            if (difficulty.equalsIgnoreCase("Hard")) {
                numOne = random.nextInt(1000)+500;
                numTwo = random.nextInt(1000)+500;
                txtNum1.setText(String.valueOf(numOne));
                txtNum2.setText(String.valueOf(numTwo));
            }
        }
        if(category.equalsIgnoreCase("Random")) {
            if(RandomString.contains("Add"))
                txtOperator.setText("+");
           else if(RandomString.contains("Subtract"))
                txtOperator.setText("-");
            else if(RandomString.contains("Multiply"))
                txtOperator.setText("x");
            
            if(difficulty.equalsIgnoreCase("Easy")) {
                numOne = random.nextInt(20);
                numTwo =random.nextInt(20);
                txtNum1.setText(String.valueOf(numOne));
                txtNum2.setText(String.valueOf(numTwo));
            }
            if(difficulty.equalsIgnoreCase("Medium")) {
                numOne = random.nextInt(100)+100;
                numTwo =random.nextInt(100)+100;
                txtNum1.setText(String.valueOf(numOne));
                txtNum2.setText(String.valueOf(numTwo));
            }
            if(difficulty.equalsIgnoreCase("Hard")) {
                numOne = random.nextInt(1000)+500;
                numTwo = random.nextInt(1000)+500;
                txtNum1.setText(String.valueOf(numOne));
                txtNum2.setText(String.valueOf(numTwo));
            }
        }
    }
    public int getActualAnswer() {
        return actualAnswer;
    }
    private void initialiseAnswer() {
        if(category.equalsIgnoreCase("Add"))
            actualAnswer = numOne + numTwo;
        
        else if(category.equalsIgnoreCase("Subtract"))
            actualAnswer = numOne - numTwo;
        
       else if(category.equalsIgnoreCase("Multiply"))
            actualAnswer = numOne * numTwo;
        else {
            if(RandomString.contains("Add"))
                actualAnswer = numOne + numTwo;
            else if(RandomString.contains("Subtract"))
                actualAnswer = numOne - numTwo;
            else
                actualAnswer = numOne * numTwo;
        }
        numberlist.add(actualAnswer);
    }

    @Override
    public void onBackPressed() {
        timer.cancel();
        startActivity(new Intent(PlayActivity.this,MainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home_options,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.spinnerhome:
                timer.cancel();
                startActivity(new Intent(PlayActivity.this,OptionsActivity.class));
                return true;
            case R.id.btnback:
                timer.cancel();
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
