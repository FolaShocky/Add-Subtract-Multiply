package com.folashocky.add_subtract_multiply;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class HighScoresActivity extends AppCompatActivity {

    private TextView txtCategory,txtName,txtScore;
    private TextView txtDifficulty,txtAnimOperator;
    private RelativeLayout relLayoutCategory,relLayoutName,relLayoutScore,relLayoutDifficulty;
    private RelativeLayout[]relativeLayouts;
    private Spinner spinnerCategory;
    private String itemAsString;
    private Button btnMainMenu;
    private ArrayList<String>items;
    private ArrayAdapter arrayAdapter;
    private int QuestionAmount_Add_Easy,QuestionAmount_Add_Medium,QuestionAmount_Add_Hard,
    QuestionAmount_Subtract_Easy,QuestionAmount_Subtract_Medium,QuestionAmount_Subtract_Hard,
    QuestionAmount_Multiply_Easy,QuestionAmount_Multiply_Medium,QuestionAmount_Mutiply_Hard,
    QuestionAmount_Random_Easy,QuestionAmount_Random_Medium,QuestionAmount_Random_Hard;//These are used for shared preferences logic
    private TranslateAnimation translateAnimation;


    private AlertDialog alertDialog;
    private AlertDialog.Builder alertdialogbuilder;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_statistics);
        txtCategory = (TextView)findViewById(R.id.txtCategory);
        txtName=(TextView)findViewById(R.id.txtName);
        txtScore = (TextView)findViewById(R.id.txtScore);

        txtDifficulty = (TextView)findViewById(R.id.txtDifficulty);
        txtAnimOperator = (TextView)findViewById(R.id.txtAnimOperator);
        relLayoutCategory = (RelativeLayout)findViewById(R.id.relLayoutCategory);
        relLayoutName = (RelativeLayout)findViewById(R.id.relLayoutName);
        relLayoutScore = (RelativeLayout)findViewById(R.id.relLayoutScore);

        alertdialogbuilder = new AlertDialog.Builder(HighScoresActivity.this);

        relLayoutDifficulty = (RelativeLayout)findViewById(R.id.relLayoutDifficulty);
        btnMainMenu = (Button)findViewById(R.id.btnMainMenu);
        relativeLayouts = new RelativeLayout[]{relLayoutCategory,relLayoutName,relLayoutScore,relLayoutDifficulty};
        spinnerCategory = (Spinner)findViewById(R.id.spinnerCategory);
        items = new ArrayList<>();
        translateAnimation =new TranslateAnimation(0,500,0,0);
        translateAnimation.setDuration(500);
        translateAnimation.setRepeatCount(Animation.INFINITE);
        translateAnimation.setRepeatMode(Animation.REVERSE);
        txtAnimOperator.startAnimation(translateAnimation);

        sharedPreferences = getSharedPreferences("Stats", Context.MODE_PRIVATE);

        QuestionAmount_Add_Easy = sharedPreferences.getInt(PrefStrings.ADD_EASY_QUESTION_COUNT,20);
        QuestionAmount_Add_Medium = sharedPreferences.getInt(PrefStrings.ADD_MEDIUM_QUESTION_COUNT,20);
        QuestionAmount_Add_Hard = sharedPreferences.getInt(PrefStrings.ADD_HARD_QUESTION_COUNT,20);

        QuestionAmount_Subtract_Easy = sharedPreferences.getInt(PrefStrings.SUBTRACT_EASY_QUESTION_COUNT,20);
        QuestionAmount_Subtract_Medium = sharedPreferences.getInt(PrefStrings.SUBTRACT_MEDIUM_QUESTION_COUNT,20);
        QuestionAmount_Subtract_Hard = sharedPreferences.getInt(PrefStrings.SUBTRACT_HARD_QUESTION_COUNT,20);

        QuestionAmount_Multiply_Easy = sharedPreferences.getInt(PrefStrings.MULTIPLY_EASY_QUESTION_COUNT,20);
        QuestionAmount_Multiply_Medium = sharedPreferences.getInt(PrefStrings.MULTIPLY_MEDIUM_QUESTION_COUNT,20);
        QuestionAmount_Mutiply_Hard = sharedPreferences.getInt(PrefStrings.MULTIPLY_HARD_QUESTION_COUNT,20);

        QuestionAmount_Random_Easy = sharedPreferences.getInt(PrefStrings.RANDOM_EASY_QUESTION_COUNT,20);
        QuestionAmount_Random_Medium = sharedPreferences.getInt(PrefStrings.RANDOM_MEDIUM_QUESTION_COUNT,20);
        QuestionAmount_Random_Hard = sharedPreferences.getInt(PrefStrings.RANDOM_HARD_QUESTION_COUNT,20);


        items.add("Add|Easy");
        items.add("Add|Medium");
        items.add("Add|Hard");
        items.add("Subtract|Easy");
        items.add("Subtract|Medium");
        items.add("Subtract|Hard");
        items.add("Multiply|Easy");
        items.add("Multiply|Medium");
        items.add("Multiply|Hard");
        items.add("Random|Easy");
        items.add("Random|Medium");
        items.add("Random|Hard");
        arrayAdapter = new ArrayAdapter<>(this,R.layout.spinnerstyle,items);
        itemAsString = "";
        spinnerCategory.setAdapter(arrayAdapter);
        for(RelativeLayout relativeLayout:relativeLayouts) {
            relativeLayout.setBackgroundColor(Color.BLACK);
        }
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemAsString = spinnerCategory.getItemAtPosition(position).toString();
                InitialiseUI();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                itemAsString ="Add|Easy";
            }
        });

        editor = sharedPreferences.edit();

        btnMainMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(HighScoresActivity.this,MainActivity.class));
            }
        });
        alertdialogbuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editor.clear();
                editor.apply();
                startActivity(new Intent(HighScoresActivity.this,MainActivity.class));
            }
        });
        alertdialogbuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        alertdialogbuilder.setTitle("Reset Scores");
        alertdialogbuilder.setMessage("Are you sure?");
        alertdialogbuilder.setCancelable(true);
        alertDialog =  alertdialogbuilder.create();
    }
    private void InitialiseUI() {

        txtName.setText("-----");
        txtScore.setText("0");

        txtCategory.setText("-----");
        if(itemAsString.equals("Add|Easy")) {
            txtName.setText(sharedPreferences.getString("Add|Easy|Name","Name: -----"));
            txtScore.setText("Score: " + String.valueOf(sharedPreferences.getInt("Add|Easy|Score",0))+"/"+String.valueOf(QuestionAmount_Add_Easy));
            txtDifficulty.setText("Difficulty: "+ "Easy");
            txtCategory.setText("Category: "+"Add");
            txtAnimOperator.setText("+");
        }
        if(itemAsString.equals("Add|Medium")) {
            txtName.setText(sharedPreferences.getString("Add|Medium|Name","Name: -----"));
            txtScore.setText("Score: " + String.valueOf(sharedPreferences.getInt("Add|Medium|Score",0))+"/"+String.valueOf(QuestionAmount_Add_Medium));
            txtDifficulty.setText("Difficulty: "+"Medium");
            txtCategory.setText("Category: "+"Add");
            txtAnimOperator.setText("+");
        }
        if(itemAsString.equals("Add|Hard")) {
            txtName.setText(sharedPreferences.getString("Add|Hard|Name","Name: -----"));
            txtScore.setText("Score: " + String.valueOf(sharedPreferences.getInt("Add|Hard|Score",0))+"/"+String.valueOf(QuestionAmount_Add_Hard));
            txtDifficulty.setText("Difficulty: "+"Hard");
            txtCategory.setText("Category: "+"Add");
            txtAnimOperator.setText("+");
        }
        if(itemAsString.equals("Subtract|Easy")) {
            txtName.setText(sharedPreferences.getString("Subtract|Easy|Name","Name: -----"));
            txtScore.setText("Score: " + String.valueOf(sharedPreferences.getInt("Subtract|Easy|Score",0))+"/"+String.valueOf(QuestionAmount_Subtract_Easy));
            txtDifficulty.setText("Difficulty: "+"Easy");
            txtCategory.setText("Category: "+"Subtract");
            txtAnimOperator.setText("-");
        }
        if(itemAsString.equals("Subtract|Medium")) {
            txtName.setText(sharedPreferences.getString("Subtract|Medium|Name","Name: -----"));
            txtScore.setText("Score: " + String.valueOf(sharedPreferences.getInt("Subtract|Medium|Score",0))+"/"+String.valueOf(QuestionAmount_Subtract_Medium));
            txtDifficulty.setText("Difficulty: "+"Medium");
            txtCategory.setText("Category: "+"Subtract");
            txtAnimOperator.setText("-");
        }
        if(itemAsString.equals("Subtract|Hard")) {
            txtName.setText(sharedPreferences.getString("Subtract|Hard|Name","Name: -----"));
            txtScore.setText("Score: " + String.valueOf(sharedPreferences.getInt("Subtract|Hard|Score",0))+"/"+String.valueOf(QuestionAmount_Subtract_Hard));
            txtDifficulty.setText("Difficulty: "+"Hard");
            txtCategory.setText("Category: "+"Subtract");
            txtAnimOperator.setText("-");

        }
        if(itemAsString.equals("Multiply|Easy")) {
            txtName.setText(sharedPreferences.getString("Multiply|Easy|Name","Name: -----"));
            txtScore.setText("Score: " + String.valueOf(sharedPreferences.getInt("Multiply|Easy|Score",0))+"/"+String.valueOf(QuestionAmount_Multiply_Easy));
            txtDifficulty.setText("Difficulty: "+"Easy");
            txtCategory.setText("Category: "+"Multiply");
            txtAnimOperator.setText("x");

        }
        if(itemAsString.equals("Multiply|Medium")) {
            txtName.setText(sharedPreferences.getString("Multiply|Medium|Name","Name: -----"));
            txtScore.setText("Score: " + String.valueOf(sharedPreferences.getInt("Multiply|Medium|Score",0))+"/"+String.valueOf(QuestionAmount_Multiply_Medium));
            txtDifficulty.setText("Difficulty: "+"Easy");
            txtCategory.setText("Category: "+"Medium");
            txtAnimOperator.setText("x");
        }
        if(itemAsString.equals("Multiply|Hard")) {
            txtName.setText(sharedPreferences.getString("Multiply|Hard|Name","Name: -----"));
            txtScore.setText("Score: " + String.valueOf(sharedPreferences.getInt("Multiply|Hard|Score",0))+"/"+String.valueOf(QuestionAmount_Mutiply_Hard));
            txtDifficulty.setText("Difficulty: "+"Hard");
            txtCategory.setText("Category: "+"Multiply");
            txtAnimOperator.setText("x");
        }
        if(itemAsString.equals("Random|Easy")) {
            txtName.setText(sharedPreferences.getString("Random|Easy|Name","Name: -----"));
            txtScore.setText("Score: " + String.valueOf(sharedPreferences.getInt("Random|Easy|Score",0))+"/"+String.valueOf(QuestionAmount_Random_Easy));
            txtDifficulty.setText("Difficulty: "+"Easy");
            txtCategory.setText("Category: " + "Random");
            txtAnimOperator.setText("?");
        }
        if(itemAsString.equals("Random|Medium")) {
            txtName.setText(sharedPreferences.getString("Random|Medium|Name","Name: -----"));
            txtScore.setText("Score: " + String.valueOf(sharedPreferences.getInt("Random|Medium|Score",0))+"/"+String.valueOf(QuestionAmount_Random_Medium));
            txtDifficulty.setText("Difficulty: "+"Medium");
            txtCategory.setText("Category: "+"Random");
            txtAnimOperator.setText("?");
        }
        if(itemAsString.equals("Random|Hard")) {
            txtName.setText(sharedPreferences.getString("Random|Hard|Name","Name: -----"));
            txtScore.setText("Score: " + String.valueOf(sharedPreferences.getInt("Random|Hard|Score",0))+"/"+String.valueOf(QuestionAmount_Random_Hard));
            txtDifficulty.setText("Difficulty: "+"Hard");
            txtCategory.setText("Category: "+"Random");
            txtAnimOperator.setText("?");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.statistics_options,menu);
        getMenuInflater().inflate(R.menu.home_options,menu);
        return true;
    }
        @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {

            case R.id.btnback:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.resetstats:
                alertDialog.show();
                return true;
            case R.id.spinnerhome:
                startActivity(new Intent(HighScoresActivity.this,OptionsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
