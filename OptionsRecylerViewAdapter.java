package com.folashocky.add_subtract_multiply;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by folas on 27/02/2018.
 */

public class OptionsRecylerViewAdapter extends RecyclerView.Adapter<OptionsRecylerViewAdapter.TimeViewHolder>
{

    private int count = 0;
    private Context context;
    private List<Time> timesList;
    public static List<Integer>TimeLimit = new ArrayList<>(1);

    private RadioButton previousRadioButton;
    public OptionsRecylerViewAdapter(Context context, List<Time> timesList)
    {
        this.context = context;
        this.timesList = timesList;
        TimeLimit.add(OptionsActivity.sharedPreferences.getInt("minutes",1));
    }
    @Override
    public TimeViewHolder onCreateViewHolder(ViewGroup parent,int viewType)
    {
        View ItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_option_content,parent,false);
        ItemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new TimeViewHolder(ItemView);
    }
    @Override
   public void onBindViewHolder(final TimeViewHolder timeViewHolder, final int position)
   {

       timeViewHolder.setIsRecyclable(false);
       final int retrievedMinutes = OptionsActivity.sharedPreferences.getInt("minutes",1);
       TimeLimit.add(retrievedMinutes);
       if(TimeLimit.get(0) == 1)
           Time.setPosition(0);
       timeViewHolder.txtTime.setText(R.string.Time);

       timeViewHolder.txtMins.setText(String.valueOf(timesList.get(position).getMinutes()));
       timeViewHolder.txtMinWord.setText(R.string.minuteword);
       if(timesList.get(position).getMinutes()<=1)
       {
           timeViewHolder.txtMinWord.setText(R.string.singleminute);
       }
       timeViewHolder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
       {
           @Override
           public void onCheckedChanged(RadioGroup group, int checkedId)
           {
               RadioButton checkedRadioButton=(RadioButton)group.findViewById(checkedId);
               Random random = new Random();
               int num = random.nextInt(Integer.MAX_VALUE);
               if(previousRadioButton !=null)
               {
                   previousRadioButton.setChecked(false);
                   previousRadioButton.setId(num);
               }
               previousRadioButton = checkedRadioButton;
               if(!previousRadioButton.isChecked())
               {
                   timesList.get(position).setChecked(false);
                   timeViewHolder.txtTime.setTextColor(Color.BLACK);
                   timeViewHolder.txtMins.setTextColor(Color.BLACK);
                   timeViewHolder.txtMinWord.setTextColor(Color.BLACK);
                   timeViewHolder.relativeLayout.setBackgroundColor(Color.WHITE);
               }


           }

       });

       timeViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener()
       {
           @Override
           public void onClick(View v)
           {

               if(TimeLimit.size() == 0)
               {
                   timeViewHolder.radioButtonZero.setChecked(true);
                   timesList.get(timeViewHolder.getAdapterPosition()).setMinutes(timeViewHolder.getAdapterPosition() + 1);
                   TimeLimit.add(timesList.get(timeViewHolder.getAdapterPosition()).getMinutes());
                   timeViewHolder.txtTime.setTextColor(Color.WHITE);
                   timeViewHolder.txtMins.setTextColor(Color.WHITE);
                   timeViewHolder.txtMinWord.setTextColor(Color.WHITE);
                   timeViewHolder.relativeLayout.setBackgroundColor(Color.BLACK);
                   Time.setPosition(position);
               }
               else
               {

                   timeViewHolder.radioButtonZero.setChecked(true);
                   TimeLimit.clear();
                   timesList.get(timeViewHolder.getAdapterPosition()).setMinutes(timeViewHolder.getAdapterPosition()+1);
                   TimeLimit.add(timesList.get(timeViewHolder.getAdapterPosition()).getMinutes());
                   timeViewHolder.txtTime.setTextColor(Color.WHITE);
                   timeViewHolder.txtMins.setTextColor(Color.WHITE);
                   timeViewHolder.txtMinWord.setTextColor(Color.WHITE);
                   timeViewHolder.relativeLayout.setBackgroundColor(Color.BLACK);
                   Time.setPosition(position);
               }
           }
       });

       timeViewHolder.radioButtonZero.setOnClickListener(new View.OnClickListener()
       {
           @Override
           public void onClick(View v)
           {
               if(TimeLimit.size() == 0)
               {
                   timesList.get(timeViewHolder.getAdapterPosition()).setMinutes(timeViewHolder.getAdapterPosition() + 1);
                   TimeLimit.add(Integer.parseInt(timeViewHolder.txtMins.getText().toString()));

                   timeViewHolder.txtTime.setTextColor(Color.WHITE);
                   timeViewHolder.txtMins.setTextColor(Color.WHITE);
                   timeViewHolder.txtMinWord.setTextColor(Color.WHITE);
                   timeViewHolder.relativeLayout.setBackgroundColor(Color.BLACK);
                   timesList.get(position).setChecked(true);
                   Time.setPosition(position);
               }
               else
               {
                   TimeLimit.clear();
                   timesList.get(timeViewHolder.getAdapterPosition()).setMinutes(timeViewHolder.getAdapterPosition()+1);
                   TimeLimit.add(Integer.parseInt(timeViewHolder.txtMins.getText().toString()));
                   timeViewHolder.txtTime.setTextColor(Color.WHITE);
                   timeViewHolder.txtMins.setTextColor(Color.WHITE);
                   timeViewHolder.txtMinWord.setTextColor(Color.WHITE);
                   timeViewHolder.relativeLayout.setBackgroundColor(Color.BLACK);
                   timesList.get(position).setChecked(true);
                   Time.setPosition(position);
               }

           }
       });

       if(timesList.get(position).getMinutes() == TimeLimit.get(0))
       {
           timesList.get(position).setChecked(true);
           Time.setPosition(position);
       }
       else
       {
          timesList.get(position).setChecked(false);

       }


        if(timeViewHolder.txtMins.getCurrentTextColor()==Color.WHITE && timeViewHolder.txtTime.getCurrentTextColor()==Color.WHITE
                && timeViewHolder.txtMinWord.getCurrentTextColor()==Color.WHITE&&position == Time.getPosition())
        {
            timeViewHolder.radioButtonZero.setChecked(true);
        }
        if(timesList.get(position).getChecked()&&position == Time.getPosition())
        {
            timeViewHolder.radioButtonZero.setChecked(true);
            timeViewHolder.txtTime.setTextColor(Color.WHITE);
            timeViewHolder.txtMins.setTextColor(Color.WHITE);
            timeViewHolder.txtMinWord.setTextColor(Color.WHITE);
            timeViewHolder.relativeLayout.setBackgroundColor(Color.BLACK);
        }
        else if (!timesList.get(position).getChecked())
        {
            timeViewHolder.txtTime.setTextColor(Color.BLACK);
            timeViewHolder.txtMins.setTextColor(Color.BLACK);
            timeViewHolder.txtMinWord.setTextColor(Color.BLACK);
            timeViewHolder.relativeLayout.setBackgroundColor(Color.WHITE);
        }
   }

    @Override
    public int getItemCount()
    {
        return timesList.size();
    }

    class TimeViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtMins, txtTime,txtMinWord;
        RadioGroup radioGroup;
        RadioButton radioButtonZero;
        RelativeLayout relativeLayout;
        TimeViewHolder(View view)
        {
            super(view);


            relativeLayout = (RelativeLayout)view.findViewById(R.id.relLayout_option_content);
            txtTime = (TextView)view.findViewById(R.id.txtTime);
            txtMins = (TextView)view.findViewById(R.id.txtMins);
            txtMinWord = (TextView)view.findViewById(R.id.txtMinWord);

            radioGroup = (RadioGroup) view.findViewById(R.id.rgParent);
            radioButtonZero =(RadioButton)view.findViewById(R.id.rButton0);
            txtTime.setTextColor(Color.BLACK);
            txtMins.setTextColor(Color.BLACK);
            txtMinWord.setTextColor(Color.BLACK);


        }
    }
}
