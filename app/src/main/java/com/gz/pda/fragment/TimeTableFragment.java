package com.gz.pda.fragment;

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
import com.gz.pda.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * show timetable on list
 * Created by host on 2015/11/6.
 */
public class TimeTableFragment extends BaseFragment {
    private ListView listView;
    private TimeTableAdapter timeTableAdapter;
    private List<TimeTable> timeTables;

    @Override
    void setConvertView(LayoutInflater inflater, ViewGroup container) {
        convertView = inflater.inflate(R.layout.fragment_time_table, container, false);
    }

    @Override
    void fetchData() {
        listView = findView(R.id.listview);
        timeTables = new ArrayList<>();
        timeTableAdapter = new TimeTableAdapter(getActivity(), timeTables);
        listView.setAdapter(timeTableAdapter);
        listView.setDivider(null);
    }

    @Override
    void initView() {
        TimeTable table1 = new TimeTable();
        table1.setTitle("第一号");
        table1.setYear(2015);
        table1.setMonth(11);
        table1.setDay(11);
        table1.setHour(16);
        table1.setMinute(50);
        table1.setText("今天完成界面了吗今天完成界面了吗今天完成界面了吗今天完成界面了吗" +
                "今天完成界面了吗今天完成界面了吗今天完成界面了吗今天完成界面了吗" +
                "今天完成界面了吗今天完成界面了吗今天完成界面了吗今天完成界面了吗");

        TimeTable table2 = new TimeTable();
        table2.setTitle("第二号");
        table2.setYear(2015);
        table2.setMonth(11);
        table2.setDay(6);
        table2.setHour(8);
        table2.setMinute(8);
        table2.setText("今天完成界面了吗？？？");
        table2.setAlarm(true);
        timeTables.add(table1);
        timeTables.add(table2);
        timeTableAdapter.notifyDataSetChanged();
    }

    @Override
    void setListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.DataKey.TIMETABLE,timeTables.get(position));
                Intent intent = new Intent(getActivity(), TimeTableActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
