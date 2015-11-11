package com.gz.pda.datamodel;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * user time item
 * Created by host on 2015/11/6.
 */
public class TimeTable implements Serializable {
    private String title;
    private String text;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private boolean alarm = false;
    private boolean star;
    private User user;

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public boolean isAlarm() {
        return alarm;
    }

    public void setAlarm(boolean alarm) {
        this.alarm = alarm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isStar() {
        return star;
    }

    public void setStar(boolean star) {
        this.star = star;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    public String getWeekString() {
        switch (getWeek()) {
            case 0:
                return "星期日";
            case 1:
                return "星期一";
            case 2:
                return "星期二";
            case 3:
                return "星期三";
            case 4:
                return "星期四";
            case 5:
                return "星期五";
            case 6:
                return "星期六";
            default:
                return "星期一";
        }
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return this.year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getMonth() {
        return this.month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getDay() {
        return this.day;
    }

    public int getHour() {
        return hour;
    }

    public String getHourString() {
        if (hour < 10) {
            return "0" + hour;
        } else {
            return hour + "";
        }
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getMinuteString() {
        if (minute < 10) {
            return "0" + minute;
        } else {
            return minute + "";
        }
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH) + 1;
        this.day = calendar.get(Calendar.DATE);
        this.hour = calendar.get(Calendar.HOUR_OF_DAY);
        this.minute = calendar.get(Calendar.MINUTE);
    }
}
