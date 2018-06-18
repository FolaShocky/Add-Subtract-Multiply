package com.folashocky.add_subtract_multiply;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ResultsActivity extends AppCompatActivity {
    private TextView txtResults;
    private int correct;
    private int incorrect;
    private Bundle resultsBundle;
    private Intent intent;
    private EditText etxtName;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String category;
    private String difficulty;
    private int questionAmountValue;
    private Button btnReturnToMainMenu;
    private String Name;
    private TextView txtLength;
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_results);
        txtResults = (TextView)findViewById(R.id.txtResults);
        resultsBundle = getIntent().getExtras();
        category = resultsBundle.getString("category");
        difficulty = resultsBundle.getString("difficulty");
        correct = resultsBundle.getInt("correct");
        incorrect = resultsBundle.getInt("incorrect");

        btnReturnToMainMenu = (Button)findViewById(R.id.btnReturnToMainMenu);
        txtResults.setText("Results: \n"+
        String.valueOf(correct) + " correct and " + String.valueOf(incorrect)+ " incorrect.");
        intent = new Intent(ResultsActivity.this,MainActivity.class);
        etxtName = (EditText) findViewById(R.id.etxtName);
        etxtName.setEnabled(false);
        etxtName.setVisibility(View.INVISIBLE);


        sharedPreferences = getSharedPreferences("Stats", Context.MODE_PRIVATE);
        questionAmountValue = sharedPreferences.getInt("minutes",1)*20;
        editor = sharedPreferences.edit();
        txtLength = (TextView)findViewById(R.id.txtLength);
        btnReturnToMainMenu.setText("MAIN MENU");
        txtLength.setText("Your name can only be 10 characters long");
        txtLength.setVisibility(View.INVISIBLE);
        FileLogic();
        if(PlayActivity.timer!=null)
            PlayActivity.timer.cancel();



        btnReturnToMainMenu.setOnClickListener(new View.OnClickListener() 
            @Override
            public void onClick(View v) {
                Name = etxtName.getText().toString().trim();
                int Count = 0;//Used to determine number of presses

                    if (etxtName.getText().toString().trim().length() > 10 && etxtName.getVisibility() == View.VISIBLE) {
                        Toast.makeText(getApplicationContext(), "Your name can only be 10 characters long", Toast.LENGTH_SHORT).show();
                        Count++;
                    } else if (Name.length() <= 10 && etxtName.getVisibility() == View.VISIBLE && 
                               !etxtName.getText().toString().trim().equals("")) {
                        FileLogic();
                        startActivity(intent);
                    }
                    if (Count == 2 && etxtName.getText().toString().trim().length() > 10 && etxtName.getVisibility() == View.VISIBLE) {
                        etxtName.setText("Player");
                        FileLogic();
                        startActivity(intent);
                    }
                    if (etxtName.getText().toString().trim().equals("") && etxtName.getVisibility() == View.VISIBLE) 
                        Count++;
                    if (etxtName.getText().toString().trim().equals("") && etxtName.getVisibility() == View.INVISIBLE)
                        startActivity(intent);
                    if (Count == 1 && etxtName.getVisibility() == View.VISIBLE && etxtName.getText().toString().trim().equals("")) {
                        Toast.makeText(getApplicationContext(), "Please enter your name", Toast.LENGTH_SHORT).show();
                        etxtName.setText("Player");
                    }
                    if (Count == 2 && etxtName.getVisibility() == View.VISIBLE && etxtName.getText().toString().trim().equals("")) {
                        etxtName.setText("Player");
                        FileLogic();
                        startActivity(intent);
                    }

            }
        });
    }
    @Override
    public void onBackPressed() {
        int count = 0;
        if(etxtName.isEnabled()) {
            count++;
            Toast.makeText(this,"Please enter your name",Toast.LENGTH_SHORT).show();
        }
        if(count==2) {
            etxtName.setText("Player");
            FileLogic();
            count++;
            startActivity(intent);
        }
    }
    private boolean BeatsPreviousScore(String key) {
         return correct > sharedPreferences.getInt(key,0);
    }
    public void FileLogic(){
        final double minsDivByQuestionCount;
        if(correct!=0)
            minsDivByQuestionCount =  questionAmountValue /correct;
        else
            minsDivByQuestionCount = 0;
        if(difficulty.equalsIgnoreCase("Easy") && category.equalsIgnoreCase("Add")) {
            if(sharedPreferences.getInt("Add|Easy|Score",0)==0 && BeatsPreviousScore("Add|Easy|Score")) {
                etxtName.setVisibility(View.VISIBLE);
                etxtName.setEnabled(true);
                txtLength.setVisibility(View.VISIBLE);
                if (!etxtName.getText().toString().trim().equals("")) {
                    editor.putString("Add|Easy|Name", "Name: " + Name.trim());
                    editor.putInt("Add|Easy|Score", correct);
                    editor.putInt(PrefStrings.ADD_EASY_QUESTION_COUNT,sharedPreferences.getInt("minutes",1)*20);

                }
            }
            else if (minsDivByQuestionCount <
                    sharedPreferences.getInt(PrefStrings.ADD_EASY_QUESTION_COUNT,0)/sharedPreferences.getInt("Add|Easy|Score",1)
                    && BeatsPreviousScore("Add|Easy|Score")) {
                etxtName.setVisibility(View.VISIBLE);
                etxtName.setEnabled(true);
                txtLength.setVisibility(View.VISIBLE);


                if (!etxtName.getText().toString().trim().equals("")) {
                    editor.putString("Add|Easy|Name", "Name: " + Name.trim());
                    editor.putInt("Add|Easy|Score", correct);
                    editor.putInt(PrefStrings.ADD_EASY_QUESTION_COUNT,sharedPreferences.getInt("minutes",1)*20);
                }
            }
            editor.apply();
        }

       else if (difficulty.equalsIgnoreCase("Medium") && category.equals("Add")) {
            if(sharedPreferences.getInt("Add|Medium|Score",0)==0 && BeatsPreviousScore("Add|Medium|Score")) {
                etxtName.setVisibility(View.VISIBLE);
                etxtName.setEnabled(true);
                txtLength.setVisibility(View.VISIBLE);
                if (!etxtName.getText().toString().trim().equals("")) {
                    editor.putString("Add|Medium|Name", "Name: " + Name.trim());
                    editor.putInt("Add|Medium|Score", correct);
                    editor.putInt(PrefStrings.ADD_MEDIUM_QUESTION_COUNT, sharedPreferences.getInt("minutes",1)*20);
                }
            }
            else if (minsDivByQuestionCount < 
                     sharedPreferences.getInt(PrefStrings.ADD_MEDIUM_QUESTION_COUNT,0)/sharedPreferences.getInt("Add|Medium|Score",1)
                     && BeatsPreviousScore("Add|Medium|Score")) {
                etxtName.setVisibility(View.VISIBLE);
                etxtName.setEnabled(true);
                txtLength.setVisibility(View.VISIBLE);
                if (!etxtName.getText().toString().trim().equals("")) {
                    editor.putString("Add|Medium|Name", "Name: " + Name.trim());
                    editor.putInt("Add|Medium|Score", correct);
                    editor.putInt(PrefStrings.ADD_MEDIUM_QUESTION_COUNT,sharedPreferences.getInt("minutes",1)*20);
                }
            }
            editor.apply();
        }
        else if (difficulty.equalsIgnoreCase("Hard") && category.equals("Add")) {
            if (sharedPreferences.getInt("Add|Hard|Score",0)==0 && BeatsPreviousScore("Add|Hard|Score")) {
                etxtName.setVisibility(View.VISIBLE);
                etxtName.setEnabled(true);
                txtLength.setVisibility(View.VISIBLE);
                if (!etxtName.getText().toString().trim().equals("")) {
                    editor.putString("Add|Hard|Name", "Name: " + Name.trim());
                    editor.putInt("Add|Hard|Score", correct);
                    editor.putInt(PrefStrings.ADD_HARD_QUESTION_COUNT, sharedPreferences.getInt("minutes",1)*20);
                }
            }
            else if (minsDivByQuestionCount < sharedPreferences.getInt(PrefStrings.ADD_HARD_QUESTION_COUNT,0)
                    /sharedPreferences.getInt("Add|Hard|Score",1)&&BeatsPreviousScore("Add|Hard|Score")) {
                etxtName.setVisibility(View.VISIBLE);
                etxtName.setEnabled(true);
                txtLength.setVisibility(View.VISIBLE);
                if (!etxtName.getText().toString().trim().equals("")) {
                    editor.putString("Add|Hard|Name", "Name: " + Name.trim());
                    editor.putInt("Add|Hard|Score", correct);
                    editor.putInt(PrefStrings.ADD_HARD_QUESTION_COUNT,sharedPreferences.getInt("minutes",1)*20);
                }
            }
            editor.apply();
        }

        if(difficulty.equalsIgnoreCase("Easy") && category.equalsIgnoreCase("Subtract")) {
            if(sharedPreferences.getInt("Subtract|Easy|Score",0)==0 && BeatsPreviousScore("Subtract|Easy|Score")) {
                etxtName.setVisibility(View.VISIBLE);
                etxtName.setEnabled(true);
                txtLength.setVisibility(View.VISIBLE);
                if (!etxtName.getText().toString().trim().equals("")) {
                    editor.putString("Subtract|Easy|Name", "Name: " + Name.trim());
                    editor.putInt("Subtract|Easy|Score", correct);
                    editor.putInt(PrefStrings.SUBTRACT_EASY_QUESTION_COUNT,sharedPreferences.getInt("minutes",1)*20);
                }
            }
            else if (minsDivByQuestionCount < sharedPreferences.getInt(PrefStrings.SUBTRACT_EASY_QUESTION_COUNT,0)
                    /sharedPreferences.getInt("Subtract|Easy|Score",1)&&BeatsPreviousScore("Subtract|Easy|Score")) {
                etxtName.setVisibility(View.VISIBLE);
                etxtName.setEnabled(true);
                txtLength.setVisibility(View.VISIBLE);
                if (!etxtName.getText().toString().trim().equals("")) {
                    editor.putString("Subtract|Easy|Name", "Name: " + Name.trim());
                    editor.putInt("Subtract|Easy|Score", correct);
                    editor.putInt(PrefStrings.SUBTRACT_EASY_QUESTION_COUNT,sharedPreferences.getInt("minutes",1)*20);
                }
            }
            editor.apply();
        }

        else if (difficulty.equalsIgnoreCase("Medium") && category.equals("Subtract")) {
            if (sharedPreferences.getInt("Subtract|Medium|Score", 0) == 0 && BeatsPreviousScore("Subtract|Medium|Score")) {
                etxtName.setVisibility(View.VISIBLE);
                etxtName.setEnabled(true);
                txtLength.setVisibility(View.VISIBLE);
                etxtName.setVisibility(View.VISIBLE);
                etxtName.setEnabled(true);
                txtLength.setVisibility(View.VISIBLE);
                if (!etxtName.getText().toString().trim().equals("")) {
                    editor.putString("Subtract|Medium|Name", "Name: " + Name.trim());
                    editor.putInt("Subtract|Medium|Score", correct);
                    editor.putInt(PrefStrings.SUBTRACT_MEDIUM_QUESTION_COUNT, sharedPreferences.getInt("minutes",1)*20);
                }
            }
            else if (minsDivByQuestionCount < sharedPreferences.getInt(PrefStrings.SUBTRACT_MEDIUM_QUESTION_COUNT,0)
                    / sharedPreferences.getInt("Subtract|Medium|Score", 1)&&BeatsPreviousScore("Subtract|Medium|Score")) {
                etxtName.setVisibility(View.VISIBLE);
                etxtName.setEnabled(true);
                txtLength.setVisibility(View.VISIBLE);
                if (!etxtName.getText().toString().trim().equals("")) {
                    editor.putString("Subtract|Medium|Name", "Name: " + Name.trim());
                    editor.putInt("Subtract|Medium|Score", correct);
                    editor.putInt(PrefStrings.SUBTRACT_MEDIUM_QUESTION_COUNT, sharedPreferences.getInt("minutes",1)*20);
                }
            }
            editor.apply();
        }
        else if (difficulty.equalsIgnoreCase("Hard") && category.equals("Subtract")) {
            if (sharedPreferences.getInt("Subtract|Hard|Score", 0) == 0 && BeatsPreviousScore("Subtract|Hard|Score")) {
                    etxtName.setVisibility(View.VISIBLE);
                    etxtName.setEnabled(true);
                    txtLength.setVisibility(View.VISIBLE);
                    if (!etxtName.getText().toString().trim().equals("")) {
                        editor.putString("Subtract|Hard|Name", "Name: " + Name.trim());
                        editor.putInt("Subtract|Hard|Score", correct);
                        editor.putInt(PrefStrings.SUBTRACT_HARD_QUESTION_COUNT, sharedPreferences.getInt("minutes",1)*20);
                    }
                }
                 else if (minsDivByQuestionCount < sharedPreferences.getInt(PrefStrings.SUBTRACT_HARD_QUESTION_COUNT,0)
                         /sharedPreferences.getInt("Subtract|Hard|Score",1)&&BeatsPreviousScore("Subtract|Hard|Score")) {
                     etxtName.setVisibility(View.VISIBLE);
                     etxtName.setEnabled(true);
                     txtLength.setVisibility(View.VISIBLE);
                     if (!etxtName.getText().toString().trim().equals("")) {
                         editor.putString("Subtract|Hard|Name", "Name: " + Name.trim());
                         editor.putInt("Subtract|Hard|Score", correct);
                         editor.putInt(PrefStrings.SUBTRACT_HARD_QUESTION_COUNT,sharedPreferences.getInt("minutes",1)*20);
                     }
                 }
            editor.apply();
        }
        if (difficulty.equalsIgnoreCase("Easy") && category.equals("Multiply")) {
            if (sharedPreferences.getInt("Multiply|Easy|Score", 0) == 0 && BeatsPreviousScore("Multiply|Easy|Score")) {
                etxtName.setVisibility(View.VISIBLE);
                etxtName.setEnabled(true);
                txtLength.setVisibility(View.VISIBLE);
                
                if (!etxtName.getText().toString().trim().equals("")) {
                    editor.putString("Multiply|Easy|Name", "Name: " + Name.trim());
                    editor.putInt("Multiply|Easy|Score", correct);
                    editor.putInt(PrefStrings.MULTIPLY_EASY_QUESTION_COUNT,sharedPreferences.getInt("minutes",1)*20);
                }
            }
            else if (minsDivByQuestionCount < sharedPreferences.getInt(PrefStrings.MULTIPLY_EASY_QUESTION_COUNT,0)
                    / sharedPreferences.getInt("Multiply|Easy|Score", 1)&& BeatsPreviousScore("Multiply|Easy|Score")) {
                etxtName.setVisibility(View.VISIBLE);
                etxtName.setEnabled(true);
                txtLength.setVisibility(View.VISIBLE);
                if (!etxtName.getText().toString().trim().equals("")) {
                    editor.putString("Multiply|Easy|Name", "Name: " + Name.trim());
                    editor.putInt("Multiply|Easy|Score", correct);
                    editor.putInt(PrefStrings.MULTIPLY_EASY_QUESTION_COUNT,sharedPreferences.getInt("minutes",1)*20);
                }
            }
            editor.apply();
        }
        else if (difficulty.equalsIgnoreCase("Medium") && category.equals("Multiply")) {
            if (sharedPreferences.getInt("Multiply|Medium|Score", 0) == 0 && BeatsPreviousScore("Multiply|Medium|Score")) {
                etxtName.setVisibility(View.VISIBLE);
                etxtName.setEnabled(true);
                txtLength.setVisibility(View.VISIBLE);
                if (!etxtName.getText().toString().trim().equals("")) {
                    editor.putString("Multiply|Medium|Name", "Name: " + Name.trim());
                    editor.putInt("Multiply|Medium|Score", correct);
                    editor.putInt(PrefStrings.MULTIPLY_MEDIUM_QUESTION_COUNT,sharedPreferences.getInt("minutes",1)*20);

                }
            }
            else if (minsDivByQuestionCount <
                    sharedPreferences.getInt(PrefStrings.MULTIPLY_MEDIUM_QUESTION_COUNT,0)
                            /sharedPreferences.getInt("Multiply|Medium|Score",1)&& BeatsPreviousScore("Multiply|Medium|Score")) {
                etxtName.setVisibility(View.VISIBLE);
                etxtName.setEnabled(true);
                txtLength.setVisibility(View.VISIBLE);
                if (!etxtName.getText().toString().trim().equals("")) {
                    editor.putString("Multiply|Medium|Name", "Name: " + Name.trim());
                    editor.putInt("Multiply|Medium|Score", correct);
                    editor.putInt(PrefStrings.MULTIPLY_MEDIUM_QUESTION_COUNT,sharedPreferences.getInt("minutes",1)*20);
                }
            }
            editor.apply();
        }
        else if (difficulty.equalsIgnoreCase("Hard") && category.equals("Multiply")) {
            if (sharedPreferences.getInt("Multiply|Hard|Score", 0) == 0 && BeatsPreviousScore("Multiply|Hard|Score")) {
                etxtName.setVisibility(View.VISIBLE);
                etxtName.setEnabled(true);
                txtLength.setVisibility(View.VISIBLE);
                if (!etxtName.getText().toString().trim().equals("")) {
                    editor.putString("Multiply|Hard|Name", "Name: " + Name.trim());
                    editor.putInt("Multiply|Hard|Score", correct);
                    editor.putInt(PrefStrings.MULTIPLY_HARD_QUESTION_COUNT,sharedPreferences.getInt("minutes",1)*20);
                }
            }
            else if (minsDivByQuestionCount < sharedPreferences.getInt(PrefStrings.MULTIPLY_HARD_QUESTION_COUNT,0)
                    /sharedPreferences.getInt("Multiply|Hard|Score",1)&& BeatsPreviousScore("Multiply|Hard|Score")) {
                etxtName.setVisibility(View.VISIBLE);
                etxtName.setEnabled(true);
                txtLength.setVisibility(View.VISIBLE);
                if (!etxtName.getText().toString().trim().equals("")) {
                    editor.putString("Multiply|Hard|Name", "Name: " + Name.trim());
                    editor.putInt("Multiply|Hard|Score", correct);
                    editor.putInt(PrefStrings.MULTIPLY_HARD_QUESTION_COUNT,sharedPreferences.getInt("minutes",1)*20);
                }
            }
            editor.apply();
        }
        if (difficulty.equalsIgnoreCase("Easy") && category.equals("Random")) {
            if (sharedPreferences.getInt("Random|Easy|Score", 0) == 0 && BeatsPreviousScore("Random|Easy|Score")) {
                etxtName.setVisibility(View.VISIBLE);
                etxtName.setEnabled(true);
                txtLength.setVisibility(View.VISIBLE);
                if (!etxtName.getText().toString().trim().equals("")) {
                    editor.putString("Random|Easy|Name", "Name: " + Name.trim());
                    editor.putInt("Random|Easy|Score", correct);
                    editor.putInt(PrefStrings.RANDOM_EASY_QUESTION_COUNT, sharedPreferences.getInt("minutes",1)*20);
                }
            }
            else if (minsDivByQuestionCount < sharedPreferences.getInt(PrefStrings.RANDOM_EASY_QUESTION_COUNT,0)
                    /sharedPreferences.getInt("Random|Easy|Score",1)&& BeatsPreviousScore("Random|Easy|Score")) {
                etxtName.setVisibility(View.VISIBLE);
                etxtName.setEnabled(true);
                txtLength.setVisibility(View.VISIBLE);
                if (!etxtName.getText().toString().trim().equals("")) {
                    editor.putString("Random|Easy|Name", "Name: " + Name.trim());
                    editor.putInt("Random|Easy|Score", correct);
                    editor.putInt(PrefStrings.RANDOM_EASY_QUESTION_COUNT,sharedPreferences.getInt("minutes",1)*20);
                }
            }
            editor.apply();
        }
        else if (difficulty.equalsIgnoreCase("Medium") && category.equals("Random")) {
            if (sharedPreferences.getInt("Random|Medium|Score", 0) == 0 && BeatsPreviousScore("Random|Medium|Score")) {
                etxtName.setVisibility(View.VISIBLE);
                etxtName.setEnabled(true);
                txtLength.setVisibility(View.VISIBLE);
                if (!etxtName.getText().toString().trim().equals("")) {
                    editor.putString("Random|Medium|Name", "Name: " + Name.trim());
                    editor.putInt("Random|Medium|Score", correct);
                    editor.putInt(PrefStrings.RANDOM_MEDIUM_QUESTION_COUNT, sharedPreferences.getInt("minutes",1)*20);
                }
            }
            else if (minsDivByQuestionCount < sharedPreferences.getInt(PrefStrings.RANDOM_MEDIUM_QUESTION_COUNT,0)
                    /sharedPreferences.getInt("Random|Medium|Score",1)&& BeatsPreviousScore("Random|Medium|Score")) {
                etxtName.setVisibility(View.VISIBLE);
                etxtName.setEnabled(true);
                txtLength.setVisibility(View.VISIBLE);
                if (!etxtName.getText().toString().trim().equals("")) {
                    editor.putString("Random|Medium|Name", "Name: " + Name.trim());
                    editor.putInt("Random|Medium|Score", correct);
                    editor.putInt(PrefStrings.RANDOM_MEDIUM_QUESTION_COUNT,sharedPreferences.getInt("minutes",1)*20);
                }
            }
            editor.apply();
        }
        else if (difficulty.equalsIgnoreCase("Hard") && category.equals("Random")) {

                if (sharedPreferences.getInt("Random|Hard|Score", 0) == 0 && BeatsPreviousScore("Random|Hard|Score")) {
                    etxtName.setVisibility(View.VISIBLE);
                    etxtName.setEnabled(true);
                    txtLength.setVisibility(View.VISIBLE);
                    if (!etxtName.getText().toString().trim().equals("")) {
                        editor.putString("Random|Hard|Name", "Name: " + Name.trim());
                        editor.putInt("Random|Hard|Score", correct);
                        editor.putInt(PrefStrings.RANDOM_HARD_QUESTION_COUNT, sharedPreferences.getInt("minutes",1)*20);
                    }
                }
                else if (minsDivByQuestionCount < sharedPreferences.getInt(PrefStrings.RANDOM_HARD_QUESTION_COUNT,0)
                        /sharedPreferences.getInt("Random|Hard|Score",1) && BeatsPreviousScore("Random|Hard|Score")) {
                    etxtName.setVisibility(View.VISIBLE);
                    etxtName.setEnabled(true);
                    txtLength.setVisibility(View.VISIBLE);
                    if (!etxtName.getText().toString().trim().equals("")) {
                        editor.putString("Random|Hard|Name", "Name: " + Name.trim());
                        editor.putInt("Random|Hard|Score", correct);
                        editor.putInt(PrefStrings.RANDOM_HARD_QUESTION_COUNT,sharedPreferences.getInt("minutes",1)*20);
                    }
                }
            editor.apply();
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.spinnerhome:
                startActivity(new Intent(ResultsActivity.this,OptionsActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
