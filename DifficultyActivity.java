package com.folashocky.add_subtract_multiply;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Created by folas on 17/09/2017.
 */

public class DifficultyActivity extends AppCompatActivity
{
   private Bundle newBundle;
   private String category;
   private String difficulty;
   private Intent intent;
   private Button btnEasy;
   private Button btnMedium;
   private Button btnHard;
   private SharedPreferences sharedPreferences;
   private Bundle ThisBundle;
    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.activity_difficulty);
        intent = new Intent(DifficultyActivity.this, PlayActivity.class);
        newBundle = new Bundle();

        ThisBundle = getIntent().getExtras();
        category = ThisBundle.getString("category");

        btnEasy = (Button) findViewById(R.id.btnEasy);
        btnMedium = (Button) findViewById(R.id.btnMedium);
        btnHard = (Button) findViewById(R.id.btnHard);
        Button[] buttons = new Button[]{btnEasy, btnMedium, btnHard};
        sharedPreferences = getSharedPreferences("Stats", Context.MODE_PRIVATE);


        for (Button button : buttons)
        {
            button.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        }
        btnEasy.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                newBundle.putString("category", category);
                difficulty = "Easy";

                newBundle.putString("difficulty", difficulty);
                intent.putExtras(newBundle);
                startActivity(intent);
            }
        });
        btnMedium.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                newBundle.putString("category", category);
                difficulty = "Medium";

                newBundle.putString("difficulty", difficulty);
                intent.putExtras(newBundle);

                startActivity(intent);
            }
        });
        btnHard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                newBundle.putString("category", category);
                difficulty = "Hard";

                newBundle.putString("difficulty", difficulty);
                intent.putExtras(newBundle);
                startActivity(intent);

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.home_options,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.btnback:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.spinnerhome:
                startActivity(new Intent(DifficultyActivity.this,OptionsActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
