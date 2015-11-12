package com.gz.pda.fragment;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.gz.pda.R;
import com.gz.pda.datamodel.TimeTable;
import com.gz.pda.datamodel.User;
import com.gz.pda.db.DBhelper;
import com.squareup.timessquare.CalendarPickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * show timetable in calendar
 * Created by host on 2015/11/5.
 */
public class CalendarFragment extends BaseTimetableFragment {
    CalendarPickerView calendarPickerView;
    Calendar minYear;
    Calendar maxYear;

    @Override
    void setConvertView(LayoutInflater inflater, ViewGroup container) {
        convertView = inflater.inflate(R.layout.fragment_calendar, container, false);
        calendarPickerView = findView(R.id.calendar_view);
    }

    @Override
    protected void fetchLeftData() {
        maxYear = Calendar.getInstance();
        maxYear.add(Calendar.YEAR, 0);
        maxYear.add(Calendar.MONTH, 2);
        minYear = Calendar.getInstance();
        minYear.add(Calendar.YEAR, 0);
        minYear.add(Calendar.MONTH, -2);
        calendarPickerView.init(minYear.getTime(), maxYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.SINGLE)
                .withSelectedDate(new Date());
    }

    @Override
    public void initView() {
        //显示选中日期的timetable
        getTimeTableFromDbByDate(calendarPickerView.getSelectedDate());
        timeTableAdapter.notifyDataSetChanged();
        //高亮有timetable的日期
        calendarPickerView.clearHighlightedDates();
        calendarPickerView.highlightDates(getTableDates());
    }


    @Override
    protected void setLeftListener() {
        calendarPickerView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                initView();
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }

    private void getTimeTableFromDbByDate(Date date) {
        User user = DBhelper.getInstance().getFirstUser();
        TimeTable selectTimeTable = new TimeTable();
        selectTimeTable.setDate(date);
        timeTables.clear();
        //筛选选中日期的timetable
        for (TimeTable t : user.getTimeTables()) {
            if (t.getDateString().equals(selectTimeTable.getDateString())) {
                timeTables.add(t);
            }
        }
    }

    //获得有timetable的日期集合
    private Collection<Date> getTableDates(){
        User user = DBhelper.getInstance().getFirstUser();
        List<Date> dates = new ArrayList<>();
        for(TimeTable t:user.getTimeTables()){
            Date date = new Date(t.getYear()-1900,t.getMonth()-1,t.getDay());
            dates.add(date);
        }
        return  dates;
    }
}
