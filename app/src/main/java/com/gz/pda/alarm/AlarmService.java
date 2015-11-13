package com.gz.pda.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;

import com.gz.pda.R;
import com.gz.pda.activity.LoginActivity;
import com.gz.pda.app.Constant;
import com.gz.pda.datamodel.TimeTable;
import com.gz.pda.utils.LogUtil;

/**
 * service that woke by alarmmanager
 */
public class AlarmService extends Service {
    private static final int NOTIFICATION_FLAG = 1;
    private NotificationManager manager;
    PendingIntent pendingIntent;

    public AlarmService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //获得传来的intent
        if (intent == null || intent.getExtras() == null) {
            LogUtil.i("the service intent is null");
            return START_NOT_STICKY;
        }
        Bundle bundle = intent.getExtras();
        TimeTable timeTable = (TimeTable) bundle.getSerializable(Constant.DataKey.TIMETABLE);
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent loginIntent = new Intent(this, LoginActivity.class);
        pendingIntent = PendingIntent.getActivity(this, 0, loginIntent, 0);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_timetable)
                .setContentTitle(timeTable.getTitle())
                .setContentText(timeTable.getText())
                .setContentIntent(pendingIntent).build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        manager.notify(NOTIFICATION_FLAG, notification);
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);
        stopSelf();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void alarm(Context context) {
        Intent intent = new Intent(context, AlarmService.class);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
    }
}
