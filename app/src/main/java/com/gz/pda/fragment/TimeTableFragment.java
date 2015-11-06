package com.gz.pda.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gz.pda.R;
import com.gz.pda.adapter.TimeTableAdapter;
import com.gz.pda.datamodel.TimeTable;

import java.util.ArrayList;
import java.util.List;

/**
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
        table1.setYear("2015");
        table1.setMonth("11");
        table1.setDay("6");
        table1.setTime("16:50");
        table1.setText("今天完成界面了吗");

        TimeTable table2 = new TimeTable();
        table2.setTitle("第二号");
        table2.setYear("2015");
        table2.setMonth("11");
        table2.setDay("6");
        table2.setTime("17:00");
        table2.setText("今天完成界面了吗？？？");
        timeTables.add(table1);
        timeTables.add(table2);
        timeTableAdapter.notifyDataSetChanged();
    }

    @Override
    void setListener() {

    }
}
