package com.gz.pda.activity;


import android.util.Log;

import com.gz.pda.R;
import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends BaseActivity {
    private CalendarPickerView calendarPickerView;
    private Calendar minYear;
    private Calendar maxYear;

    @Override
    protected void fetchData() {
        minYear = Calendar.getInstance();
        minYear.add(Calendar.YEAR, -2);
        maxYear = Calendar.getInstance();
        maxYear.add(Calendar.YEAR, 2);
    }

    @Override
    protected void initLayoutDataView() {
        setContentView(R.layout.activity_main);
        calendarPickerView = findView(R.id.calendar_view);
    }

    @Override
    protected void initView() {
        calendarPickerView.init(minYear.getTime(),maxYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.SINGLE)
                .withSelectedDate(new Date());
    }

    @Override
    protected void setListener() {
        calendarPickerView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                toast(DateFormat.getDateInstance().format(date));
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }
}
