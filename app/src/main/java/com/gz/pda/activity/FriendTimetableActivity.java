package com.gz.pda.activity;

import com.androidquery.AQuery;
import com.gz.pda.R;

/**
 * Created by host on 2015/12/15.
 */
public class FriendTimetableActivity extends TimeTableActivity {
    @Override
    protected void initLayoutDataView() {
        setContentView(R.layout.activity_friend_timetable);
        aQuery = new AQuery(this);
        title = findView(R.id.timetable_title);
        text = findView(R.id.timetable_text);
        if (createNew) {
            enableEdit();
        }
    }
}
