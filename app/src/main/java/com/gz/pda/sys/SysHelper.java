package com.gz.pda.sys;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.gz.pda.Network.Net;
import com.gz.pda.activity.MainActivity;
import com.gz.pda.alarm.AlarmHelper;
import com.gz.pda.datamodel.TimeTable;
import com.gz.pda.datamodel.User;
import com.gz.pda.db.DBhelper;
import com.gz.pda.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by host on 2015/11/19.
 */
public class SysHelper {
    private Gson gson;
    private TimerTask timerTask;
    private Timer timer;
    private MainActivity mainActivity;
//    private static SysHelper instance;
//
//    public static void init(Context context) {
//        instance = new SysHelper();
//    }

    public SysHelper(MainActivity mainActivity) {
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        this.mainActivity = mainActivity;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Net.get(new Net.NetworkListener() {
                    @Override
                    public void onSuccess(String response) {
                        sysOperation(response);
                    }

                    @Override
                    public void onFail(String error) {

                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask,5000,10000);
    }


    private void sysOperation(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);

            List<TimeTable> add = gson.fromJson(jsonObject.optString("add"), new TypeToken<List<TimeTable>>() {
            }.getType());
            this.add(add);
            LogUtil.i(jsonObject.optString("add"));

            List<TimeTable> update = gson.fromJson(jsonObject.optString("update"), new TypeToken<List<TimeTable>>() {
            }.getType());
            this.update(update);
            LogUtil.i(jsonObject.optString("update"));

            List<TimeTable> delete = gson.fromJson(jsonObject.optString("delete"), new TypeToken<List<TimeTable>>() {
            }.getType());
            this.delete(delete);
            LogUtil.i(jsonObject.optString("delete"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    public static void stop(){
//        if(instance!=null){
//            instance.timer.cancel();
//        }
//    }

    private void add(List<TimeTable> timeTables){
        User user = DBhelper.getInstance().getFirstUser();
        for(TimeTable t:timeTables){
            t.setUser(user);
            DBhelper.getInstance().add(t);
            AlarmHelper.getInstance().add(t);
        }
        mainActivity.fragmentInitView();
    }

    private void update(List<TimeTable> timeTables){
        User user = DBhelper.getInstance().getFirstUser();
        for(TimeTable t:timeTables){
            t.setUser(user);
            DBhelper.getInstance().update(t);
            AlarmHelper.getInstance().update(t);
        }
        mainActivity.fragmentInitView();
    }

    private void delete(List<TimeTable> timeTables){
        for(TimeTable t:timeTables){
            DBhelper.getInstance().delete(t);
            AlarmHelper.getInstance().cancel(t);
        }
        mainActivity.fragmentInitView();
    }
}
