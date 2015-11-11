package com.gz.pda.app;

import android.app.Application;

import com.gz.pda.Network.Net;
import com.gz.pda.db.DBhelper;

/**
 * the hole application
 * Created by host on 2015/11/5.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Net.init(this);
        DBhelper.init(this);
    }
}
