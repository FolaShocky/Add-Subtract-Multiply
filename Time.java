package com.folashocky.add_subtract_multiply;

/**
 * Created by folas on 27/02/2018.
 */

public class Time
{
    private String minutesAsString,secondsAsString;
    private int minutes, seconds;
    private String customTag;
    private int id;
    private boolean isChecked;
    private static int position;
    public Time(int minutes,int seconds)
    {
        this.seconds = seconds;
        this.minutes = minutes;
    }
    public String getMinutesAsString()
    {
        minutesAsString = String.valueOf(minutes);
        return minutesAsString;
    }
    public void setMinutesAsString(String MinutesAsString)
    {
        this.minutesAsString = MinutesAsString;
    }
    public int getMinutes()
    {
        return minutes;
    }
    public void setMinutes(int Minutes)
    {
        this.minutes = Minutes;
    }
    public int getSeconds()
    {
        return seconds;
    }
    public void setSeconds(int seconds)
    {
        this.seconds = seconds;
    }
    public String getSecondsAsString()
    {
        return secondsAsString;
    }
    public void setSecondsAsString(String secondsAsString)
    {
        this.secondsAsString = secondsAsString;
    }
    public void setCustomTag(String customTag)
    {
        this.customTag = customTag;
    }
    public String getCustomTag()
    {
        return customTag;
    }
    public void setRadioButtonId(int id)
    {
        this.id = id;
    }
    public int getRadioButtonId()
    {
        return id;
    }
    public boolean getChecked()
    {
        return isChecked;
    }
    public void setChecked(boolean isChecked)
    {
        this.isChecked = isChecked;
    }

    public static void setPosition(int position)
    {
        Time.position = position;
    }
    public static int getPosition()
    {
        return position;
    }




}
