package com.gz.pda.fragment;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.gz.pda.R;
import com.gz.pda.datamodel.User;
import com.gz.pda.db.DBhelper;

import java.util.Collections;

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
