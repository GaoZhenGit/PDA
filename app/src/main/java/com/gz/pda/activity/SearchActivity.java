package com.gz.pda.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gz.pda.R;
import com.gz.pda.adapter.TimeTableAdapter;
import com.gz.pda.alarm.AlarmHelper;
import com.gz.pda.app.Constant;
import com.gz.pda.datamodel.TimeTable;
import com.gz.pda.db.DBhelper;
import com.gz.pda.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * search timetable from db
 * Created by host on 2015/11/14.
 */
public class SearchActivity extends BaseActivity {
    private ListView listView;
    private List<TimeTable> timeTableList;
    private EditText searchEditText;
    private ProgressBar progressBar;
    private TimeTableAdapter timeTableAdapter;

    private AQuery aQuery;

    @Override
    protected void fetchData() {

    }

    @Override
    protected void initLayoutDataView() {
        setContentView(R.layout.activity_search);
        aQuery = new AQuery(this);
    }

    @Override
    protected void initView() {
        listView = (ListView) findViewById(R.id.listview);
        searchEditText = (EditText) findViewById(R.id.et_search);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        timeTableList = new ArrayList<>();
        timeTableAdapter = new TimeTableAdapter(this,timeTableList);
        listView.setAdapter(timeTableAdapter);
        listView.setDivider(null);
    }

    @Override
    protected void setListener() {
        aQuery.id(R.id.btn_search_left).clicked(this, "onBackPressed");
        aQuery.id(R.id.btn_search).clicked(this, "aq_search");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.DataKey.TIMETABLE, timeTableList.get(position));
                Intent intent = new Intent(SearchActivity.this, TimeTableActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, Constant.Code.MODIFY_TIMETABLE);
            }
        });
    }

    public void aq_search(){
        String searchkey = searchEditText.getText().toString();
        if (searchkey.length() == 0) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        //从本地数据库提取笔记
        List<TimeTable> selected = new ArrayList<>();
        for(TimeTable t:DBhelper.getInstance().getFirstUser().getTimeTables()){
            if(t.getTitle().contains(searchkey)||t.getText().contains(searchkey)){
                selected.add(t);
            }
        }
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        LogUtil.i(gson.toJson(selected));
        refresh(selected);
        progressBar.setVisibility(View.GONE);
    }

    private void refresh(List<TimeTable> timeTables){
        timeTableList.clear();
        timeTableList.addAll(timeTables);
        timeTableAdapter.notifyDataSetChanged();
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
                int size = timeTableList.size();
                for (int i = 0; i < size; i++) {
                    if (timeTableList.get(i).getId() == modifiedTimetable.getId()) {
                        timeTableList.set(i, modifiedTimetable);
                    }
                }
                timeTableAdapter.notifyDataSetChanged();
                //更新本地数据库
                DBhelper.getInstance().update(modifiedTimetable);
                //更新闹钟队列
                AlarmHelper.getInstance().update(modifiedTimetable);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }
}
