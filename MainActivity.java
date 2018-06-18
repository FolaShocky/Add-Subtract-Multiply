package com.folashocky.add_subtract_multiply;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

import static com.folashocky.add_subtract_multiply.PlayActivity.timer;

public class MainActivity extends AppCompatActivity {
   private Intent intent;
   private Bundle boolBundle;
   private String category;
   private Button btnAdd;
   private Button btnSubtract;
   private Button btnMultiply;
   private Button btnHighScores;
   private Button btnRandom;
   private Button[] buttons;
   static SharedPreferences sharedPreferences;
   private SharedPreferences.Editor editor;
   private AlphaAnimation FadeIn;
   private AlphaAnimation FadeOut;
   private ActionBar actionBar;
   private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnSubtract = (Button) findViewById(R.id.btnSubtract);
        btnMultiply = (Button) findViewById(R.id.btnMultiply);
        btnRandom = (Button)findViewById(R.id.btnRandom);
        btnHighScores = (Button)findViewById(R.id.btnStatistics);
        boolBundle = new Bundle();
        intent = new Intent(MainActivity.this,DifficultyActivity.class);
        buttons = new Button[]{btnAdd,btnSubtract,btnMultiply,btnRandom, btnHighScores};
        FadeIn = new AlphaAnimation(0f,1f);
        FadeOut=new AlphaAnimation(1f,0f);
        if(timer!=null)
            timer.cancel();
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        for(Button button: buttons) {
            button.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        }

        sharedPreferences = getSharedPreferences("Stats", Context.MODE_PRIVATE);

      btnAdd.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              category = "Add";
              boolBundle.putString("category",category);

              intent.putExtras(boolBundle);
              startActivity(intent);
          }
      });
        btnSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "Subtract";
                boolBundle.putString("category",category);
                intent.putExtras(boolBundle);
                startActivity(intent);
            }

        });
        btnMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "Multiply";
                boolBundle.putString("category",category);
                intent.putExtras(boolBundle);
                startActivity(intent);
            }
        });
        btnRandom.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                category = "Random";
                boolBundle.putString("category",category);
                intent.putExtras(boolBundle);
                startActivity(intent);

            }
        });
        btnHighScores.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent thisIntent = new Intent(MainActivity.this,HighScoresActivity.class);
                startActivity(thisIntent);
            }
        });

    }

@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_options,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case  R.id.spinnerhome:
                startActivity(new Intent(MainActivity.this,OptionsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void onBackPressed() {
        startActivity(new Intent(MainActivity.this,MainActivity.class));
    }

}
