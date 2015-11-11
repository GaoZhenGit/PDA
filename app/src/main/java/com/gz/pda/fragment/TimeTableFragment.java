package com.gz.pda.fragment;

import android.app.Activity;
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
    public void initView() {
        getTimeTableFromDB();
        timeTableAdapter.notifyDataSetChanged();
    }

    @Override
    void setListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.DataKey.TIMETABLE, timeTables.get(position));
                Intent intent = new Intent(getActivity(), TimeTableActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, Constant.Code.MODIFY_TIMETABLE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case Constant.Code.MODIFY_TIMETABLE:
                //修改日程回调
                //获得回调timetable数据
                TimeTable modifiedTimetable = (TimeTable) data.getExtras()
                        .getSerializable(Constant.DataKey.TIMETABLE);
                //判断返回的id,修改本页面数据
                int size = timeTables.size();
                for (int i = 0; i < size; i++) {
                    if (timeTables.get(i).getId() == modifiedTimetable.getId()) {
                        timeTables.set(i, modifiedTimetable);
                    }
                }
                timeTableAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    private void getTimeTableFromDB(){
        User user = DBhelper.getInstance().getUserById(1);
        timeTables.clear();
        timeTables.addAll(user.getTimeTables());
    }
}
