package com.gz.pda.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gz.pda.R;
import com.gz.pda.activity.TimeTableActivity;
import com.gz.pda.adapter.TimeTableAdapter;
import com.gz.pda.app.Constant;
import com.gz.pda.datamodel.TimeTable;
import com.gz.pda.datamodel.User;
import com.gz.pda.db.DBhelper;
import com.gz.pda.utils.LogUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * show timetable on list
 * Created by host on 2015/11/6.
 */
public class TimeTableFragment extends BaseTimetableFragment {

    @Override
    void setConvertView(LayoutInflater inflater, ViewGroup container) {
        convertView = inflater.inflate(R.layout.fragment_time_table, container, false);
    }

    @Override
    public void initView() {
        getTimeTableFromDB();
        timeTableAdapter.notifyDataSetChanged();
    }

    @Override
    protected void fetchLeftData() {

    }

    @Override
    protected void setLeftListener() {

    }

    private void getTimeTableFromDB(){
        User user = DBhelper.getInstance().getFirstUser();
        timeTables.clear();
        timeTables.addAll(user.getTimeTables());
        Collections.sort(timeTables);
    }
}
