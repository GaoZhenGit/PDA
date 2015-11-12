package com.gz.pda.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gz.pda.R;
import com.gz.pda.activity.MainActivity;
import com.gz.pda.activity.TimeTableActivity;
import com.gz.pda.adapter.TimeTableAdapter;
import com.gz.pda.app.Constant;
import com.gz.pda.datamodel.TimeTable;
import com.gz.pda.db.DBhelper;

import java.util.ArrayList;
import java.util.List;

/**
 * the fragment that contain timetable listview
 * Created by host on 2015/11/12.
 */
public abstract class BaseTimetableFragment extends BaseFragment {
    protected ListView listView;
    protected TimeTableAdapter timeTableAdapter;
    protected List<TimeTable> timeTables;

    @Override
    void fetchData() {
        listView = findView(R.id.listview);
        timeTables = new ArrayList<>();
        timeTableAdapter = new TimeTableAdapter(getActivity(), timeTables);
        listView.setAdapter(timeTableAdapter);
        listView.setDivider(null);
        fetchLeftData();
    }

    protected abstract void fetchLeftData();

    @Override
    void setListener() {
        //设置点击进入
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
        //设置长按删除
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                String[] items = {"删除"};
                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DBhelper.getInstance().delete(timeTables.get(position));
                                Activity mainActivity = getActivity();
                                if(mainActivity instanceof MainActivity){
                                    ((MainActivity)mainActivity).fragmentInitView();
                                }
                            }
                        })
                        .create();
                alertDialog.show();
                return true;
            }
        });
        setLeftListener();
    }

    protected abstract void setLeftListener();

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
                //更新本地数据库
                DBhelper.getInstance().update(modifiedTimetable);
                //通知所有timetable list页面更新
                Activity mainActivity = getActivity();
                if(mainActivity instanceof MainActivity){
                    ((MainActivity)mainActivity).fragmentInitView();
                }
                break;
            default:
                break;
        }
    }
}
