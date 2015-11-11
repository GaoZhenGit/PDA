package com.gz.pda.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gz.pda.R;
import com.gz.pda.datamodel.TimeTable;

import java.util.List;

/**
 * view of time table
 * Created by host on 2015/11/6.
 */
public class TimeTableAdapter extends BaseAdapter {
    private Context context;
    private List<TimeTable> timeTables;

    public TimeTableAdapter(Context context, List<TimeTable> timeTables) {
        this.context = context;
        this.timeTables = timeTables;
    }

    @Override
    public int getCount() {
        return timeTables == null ? 0 : timeTables.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_timetable,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.tv_table_title);
            viewHolder.time = (TextView) convertView.findViewById(R.id.tv_table_time);
            viewHolder.text = (TextView) convertView.findViewById(R.id.tv_table_text);
            viewHolder.alarm = (ImageView) convertView.findViewById(R.id.btn_img_alarm);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        TimeTable timeTable = timeTables.get(position);
        viewHolder.title.setText(timeTable.getTitle());
        viewHolder.time.setText(timeTable.getYear()
                +"-"+timeTable.getMonth()
                +"-"+timeTable.getDay()
                +"  "
                +timeTable.getWeekString()
                +"   "
                +timeTable.getHourString()
                +":"
                + timeTable.getMinuteString());
        viewHolder.text.setText(timeTable.getText());
        if (timeTable.isAlarm()){
            viewHolder.alarm.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    public class ViewHolder{
        public TextView title;
        public TextView time;
        public TextView text;
        public ImageView alarm;
    }
}
