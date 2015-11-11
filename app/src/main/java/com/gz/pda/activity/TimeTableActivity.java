package com.gz.pda.activity;

import android.text.Editable;
import android.widget.EditText;

import com.androidquery.AQuery;
import com.gz.pda.R;
import com.gz.pda.app.Constant;
import com.gz.pda.datamodel.TimeTable;

import java.util.Calendar;
import java.util.Date;

/**
 * edit or crete timetable
 * Created by host on 2015/11/10.
 */
public class TimeTableActivity extends BaseActivity {
    private AQuery aQuery;
    private TimeTable timeTable;
    private boolean createNew;

    private EditText title;
    private EditText text;

    @Override
    protected void fetchData() {
        if (mBundle == null) {
            timeTable = new TimeTable();
            createNew = true;
        } else {
            timeTable = (TimeTable) mBundle.getSerializable(Constant.DataKey.TIMETABLE);
            createNew = false;
        }
    }

    @Override
    protected void initLayoutDataView() {
        setContentView(R.layout.activity_timetable);
        aQuery = new AQuery(this);
        title = findView(R.id.timetable_title);
        text = findView(R.id.timetable_text);
    }

    @Override
    protected void initView() {
        aQuery.id(R.id.img_title_right).image(R.mipmap.ic_confirm);
        aQuery.id(R.id.img_title_right2).image(R.mipmap.ic_edit);
        aQuery.id(R.id.btn_title_right2).visible();
        aQuery.id(R.id.tv_title_middle).text("日程");
        if (createNew) {
            timeTable.setDate(new Date());
        } else {
            aQuery.id(R.id.timetable_title).text(timeTable.getTitle());
            aQuery.id(R.id.timetable_text).text(timeTable.getText());
        }
        aQuery.id(R.id.btn_table_date).text(
                timeTable.getYear() + "-"
                        + timeTable.getMonth() + "-"
                        + timeTable.getDay());
        aQuery.id(R.id.btn_table_time).text(timeTable.getHourString() + ":" + timeTable.getMinuteString());
        aQuery.id(R.id.cb_alarm).checked(timeTable.isAlarm());
    }

    @Override
    protected void setListener() {
        aQuery.id(R.id.btn_title_left).clicked(this, "onBackPressed");
        aQuery.id(R.id.btn_title_right).clicked(this, "confirm");
        aQuery.id(R.id.btn_title_right2).clicked(this, "enableEdit");
    }

    public void confirm() {

    }

    //允许编辑
    public void enableEdit() {
        title.setFocusableInTouchMode(true);
        title.setFocusable(true);
        title.requestFocus();
        text.setFocusableInTouchMode(true);
        text.setFocusable(true);
    }
}
