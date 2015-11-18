package com.gz.pda.alarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.gz.pda.app.Constant;
import com.gz.pda.datamodel.TimeTable;
import com.gz.pda.datamodel.User;
import com.gz.pda.db.DBhelper;

import java.util.Calendar;
import java.util.Date;

/**
 * single instance class of alarmqueue
 * Created by host on 2015/11/13.
 */
public class AlarmHelper {
    private Context context;
    private static AlarmHelper instance;

    private AlarmHelper(Context context) {
        this.context = context;
    }

    public static void init(Context context) {
        instance = new AlarmHelper(context);
    }

    public static AlarmHelper getInstance() {
        return instance;
    }

    //添加到闹钟队列
    public void add(TimeTable timeTable) {
        if (!timeTable.isAlarm()) {
            return;
        }
        switch (timeTable.getDate().compareTo(new Date())) {
            case -1:
            case 0:
                return;
            default:
                break;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timeTable.getDate());
        Intent intent = new Intent(context, AlarmService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.DataKey.TIMETABLE, timeTable);
        intent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getService(context, timeTable.getId(), intent, 0);
        AlarmManager alarmManager =
                (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    //更新闹钟队列项（有无闹钟）
    public void update(TimeTable timeTable) {
        if (!timeTable.isAlarm()) {
            cancel(timeTable);
        } else {
            add(timeTable);
        }
    }

    //取消闹钟队列项（队列中删除）
    public void cancel(TimeTable timeTable) {
        Intent intent = new Intent(context, AlarmService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, timeTable.getId(), intent, 0);
        AlarmManager alarmManager =
                (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    //取消所有闹钟队列
    public void cancelAll() {
        User user = DBhelper.getInstance().getFirstUser();
        for (TimeTable timeTable : user.getTimeTables()) {
            cancel(timeTable);
        }
    }
}
