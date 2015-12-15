package com.gz.pda.activity;

import android.view.View;

import com.androidquery.AQuery;
import com.gz.pda.R;

/**
 * Created by host on 2015/12/15.
 */
public class FriendActivity extends BaseActivity{
    AQuery aQuery;
    @Override
    protected void fetchData() {

    }

    @Override
    protected void initLayoutDataView() {
        setContentView(R.layout.activity_friend);
        aQuery = new AQuery(this);
    }

    @Override
    protected void initView() {
        aQuery.id(R.id.tv_title_middle).text("好友");
        aQuery.id(R.id.btn_title_right2).visible();
        aQuery.id(R.id.img_title_right).image(R.mipmap.ic_add);
    }

    @Override
    protected void setListener() {
        aQuery.id(R.id.btn_title_left).clicked(this,"onBackPressed");
    }
}
