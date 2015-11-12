package com.gz.pda.datamodel;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * user time item
 * Created by host on 2015/11/6.
 */
@DatabaseTable(tableName = "time_table")
public class TimeTable implements Serializable, Comparable<TimeTable> {
    @DatabaseField(generatedId = true)
    @Expose
    private int id;

    @DatabaseField(columnName = "title")
    @Expose
    private String title;

    @DatabaseField(columnName = "text")
    @Expose
    private String text;

    @DatabaseField(columnName = "year")
    @Expose
    private int year;

    @DatabaseField(columnName = "month")
    @Expose
    private int month;

    @DatabaseField(columnName = "day")
    @Expose
    private int day;

    @DatabaseField(columnName = "hour")
    @Expose
    private int hour;

    @DatabaseField(columnName = "minute")
    @Expose
    private int minute;

    @DatabaseField(columnName = "alarm")
    @Expose
    private boolean alarm = false;

    @DatabaseField(columnName = "star")
    @Expose
    private boolean star;

    @DatabaseField(canBeNull = true, foreign = true, columnName = "user_id")
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateString() {
        return year + "-" + month + "-" + day;
    }

    @Override
    public int compareTo(TimeTable another) {
        if (this.year < another.year) {
            return -1;
        }
        if (this.year > another.year) {
            return 1;
        }

        if (this.month < another.month) {
            return -1;
        }
        if (this.month > another.month) {
            return 1;
        }

        if (this.day < another.day) {
            return -1;
        }
        if (this.day > another.day) {
            return 1;
        }

        if (this.hour < another.hour) {
            return -1;
        }
        if (this.hour > another.hour) {
            return 1;
        }

        if (this.minute < another.minute) {
            return -1;
        }
        if (this.minute > another.minute) {
            return 1;
        }
        return -1;
    }
}
