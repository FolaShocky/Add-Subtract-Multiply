package com.folashocky.add_subtract_multiply;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.folashocky.add_subtract_multiply.OptionsRecylerViewAdapter.TimeLimit;

/**
 * Created by folas on 27/02/2018.
 */

public class OptionsActivity extends AppCompatActivity
{
    ArrayList<Time> timeList;
    private static final int VERTICAL_ITEM_SPACE = 35;
    private Context context;
    public static SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.activity_options);
        timeList = new ArrayList<>();
        context = this;
        populateList();
        RecyclerView optionsRecyclerView = (RecyclerView)findViewById(R.id.recyclerViewOptions);

        sharedPreferences = getSharedPreferences("Stats", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if(!sharedPreferences.contains("minutes"))
        editor.putInt("minutes",1);
        TextView txtSave = (TextView) findViewById(R.id.btnsave);

        final OptionsRecylerViewAdapter optionsRecylerViewAdapter = new OptionsRecylerViewAdapter(this,timeList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        optionsRecyclerView.setLayoutManager(layoutManager);
        optionsRecyclerView.setAdapter(optionsRecylerViewAdapter);
        optionsRecyclerView.addItemDecoration(new VerticalSpaceOptionsRV(VERTICAL_ITEM_SPACE));
        optionsRecyclerView.addItemDecoration(new DividerOptionsRV(context));
    }

    private void populateList()
    {
        timeList.add(new Time(1,0));
        timeList.add(new Time(2,0));
        timeList.add(new Time(3,0));
        timeList.add(new Time(4,0));
        timeList.add(new Time(5,0));
        timeList.add(new Time(6,0));
        timeList.add(new Time(7,0));
        timeList.add(new Time(8,0));
        timeList.add(new Time(9,0));
        timeList.add(new Time(10,0));
        timeList.add(new Time(11,0));
        timeList.add(new Time(12,0));
        timeList.add(new Time(13,0));
        timeList.add(new Time(14,0));
        timeList.add(new Time(15,0));
        timeList.add(new Time(16,0));
        timeList.add(new Time(17,0));
        timeList.add(new Time(18,0));
        timeList.add(new Time(19,0));
        timeList.add(new Time(20,0));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.options,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.btnback:
                startActivity(new Intent(OptionsActivity.this,MainActivity.class));
                return true;
            case R.id.btnsave:
                if(TimeLimit.size()>0)
                {
                    editor.putInt("minutes", TimeLimit.get(0));
                    editor.apply();
                    Toast.makeText(context,"Saved!",Toast.LENGTH_SHORT).show();
                    TimeLimit.clear();
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }
}
