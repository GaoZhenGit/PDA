package com.gz.pda.fragment;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.gz.pda.R;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;

/**
 * show timetable in calendar
 * Created by host on 2015/11/5.
 */
public class CalendarFragment extends BaseFragment {
    CalendarPickerView calendarPickerView;
    Calendar minYear;
    Calendar maxYear;

    @Override
    void setConvertView(LayoutInflater inflater, ViewGroup container) {
        convertView = inflater.inflate(R.layout.fragment_calendar, container, false);
        calendarPickerView = findView(R.id.calendar_view);
    }

    @Override
    void fetchData() {
        maxYear = Calendar.getInstance();
        maxYear.add(Calendar.YEAR, 2);
        minYear = Calendar.getInstance();
        minYear.add(Calendar.YEAR, -2);
        calendarPickerView.init(minYear.getTime(),maxYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.MULTIPLE)
                .withSelectedDate(new Date());
    }

    @Override
    void initView() {

    }

    @Override
    void setListener() {

    }
}
