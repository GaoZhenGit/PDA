package com.gz.pda.activity;

import com.androidquery.AQuery;
import com.gz.pda.R;

/**
 * for register
 * Created by host on 2015/11/7.
 */
public class RegisterActivity extends BaseActivity {
    AQuery aQuery;
    @Override
    protected void fetchData() {

    }

    @Override
    protected void initLayoutDataView() {
        setContentView(R.layout.activity_register);
        aQuery =new AQuery(this);
    }

    @Override
    protected void initView() {
        aQuery.id(R.id.tv_title_middle).text("注册");
    }

    @Override
    protected void setListener() {
        aQuery.id(R.id.btn_title_left).clicked(this,"onBackPressed");
    }
}
