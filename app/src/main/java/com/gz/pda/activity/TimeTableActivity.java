package com.gz.pda.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TimePicker;

import com.androidquery.AQuery;
import com.gz.pda.R;
import com.gz.pda.app.Constant;
import com.gz.pda.datamodel.TimeTable;
import com.squareup.timessquare.CalendarPickerView;

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
    private boolean isModify;

    private EditText title;
    private EditText text;

    @Override
    protected void fetchData() {
        if (mBundle == null) {
            timeTable = new TimeTable();
            createNew = true;
            isModify = true;
        } else {
            timeTable = (TimeTable) mBundle.getSerializable(Constant.DataKey.TIMETABLE);
            createNew = false;
            isModify = false;
        }
    }

    @Override
    protected void initLayoutDataView() {
        setContentView(R.layout.activity_timetable);
        aQuery = new AQuery(this);
        title = findView(R.id.timetable_title);
        text = findView(R.id.timetable_text);
        if (createNew) {
            enableEdit();
        }
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
        aQuery.id(R.id.btn_table_date).text(timeTable.getDateString());
        aQuery.id(R.id.btn_table_time).text(timeTable.getHourString() + ":" + timeTable.getMinuteString());
        aQuery.id(R.id.cb_alarm).checked(timeTable.isAlarm());
    }

    @Override
    protected void setListener() {
        aQuery.id(R.id.btn_title_left).clicked(this, "onBackPressed");
        aQuery.id(R.id.btn_title_right).clicked(this, "confirm");
        aQuery.id(R.id.btn_title_right2).clicked(this, "enableEdit");
        aQuery.id(R.id.cb_alarm).clicked(this, "editAlarm");
        aQuery.id(R.id.btn_table_date).clicked(this, "editDate");
        aQuery.id(R.id.btn_table_time).clicked(this, "editTime");
    }

    public void confirm() {
        if (!isModify) {
            finish();
            return;
        }
        String titleString = title.getText().toString();
        String textString = text.getText().toString();
        if (textString.length() == 0 || titleString.length() == 0) {
            return;
        }
        timeTable.setTitle(titleString);
        timeTable.setText(textString);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.DataKey.TIMETABLE, timeTable);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    //允许编辑
    public void enableEdit() {
        title.setFocusableInTouchMode(true);
        title.setFocusable(true);
        title.requestFocus();
        text.setFocusableInTouchMode(true);
        text.setFocusable(true);
        InputMethodManager inputManager =
                (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(title, 0);
        isModify = true;
    }

    //改变是否提醒
    public void editAlarm() {
        timeTable.setAlarm(aQuery.id(R.id.cb_alarm).isChecked());
        isModify = true;
    }

    //改变日期
    public void editDate() {
        final CalendarPickerView calendarPickerView
                = (CalendarPickerView) LayoutInflater.from(this)
                .inflate(R.layout.calendar_dialog, null, false);
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("选择日期")
                .setView(calendarPickerView)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int hour = timeTable.getHour();
                        int minute = timeTable.getMinute();
                        timeTable.setDate(calendarPickerView.getSelectedDate());
                        timeTable.setHour(hour);
                        timeTable.setMinute(minute);
                        aQuery.id(R.id.btn_table_date).text(timeTable.getDateString());
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        alertDialog.show();

        //初始化选择日历界面，从原来的日期起5个月的计划
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(new Date());
        calendarEnd.add(Calendar.MONTH, 5);
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(timeTable.getDate());
        calendarStart.add(Calendar.YEAR, -1);
        calendarPickerView.init(calendarStart.getTime(), calendarEnd.getTime())
                .inMode(CalendarPickerView.SelectionMode.SINGLE)
                .withSelectedDate(timeTable.getDate());
        isModify = true;
    }

    public void editTime() {
        final TimePicker timePicker = new TimePicker(this);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(timeTable.getHour());
        timePicker.setCurrentMinute(timeTable.getMinute());
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("选择时间")
                .setView(timePicker)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        timeTable.setHour(timePicker.getCurrentHour());
                        timeTable.setMinute(timePicker.getCurrentMinute());
                        aQuery.id(R.id.btn_table_time).text(timeTable.getHourString()
                                + ":" + timeTable.getMinuteString());
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        alertDialog.show();
        isModify = true;
    }
}
