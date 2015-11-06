package com.gz.pda.activity;

import android.content.Intent;

import com.androidquery.AQuery;
import com.gz.pda.R;

/**
 * for login
 * Created by host on 2015/11/7.
 */
public class LoginActiviy extends BaseActivity {
    AQuery aQuery;
    @Override
    protected void fetchData() {

    }

    @Override
    protected void initLayoutDataView() {
        setContentView(R.layout.activity_login);
        aQuery =new AQuery(this);
    }

    @Override
    protected void initView() {
        aQuery.id(R.id.tv_title_middle).text("登录");
        aQuery.id(R.id.btn_title_right).invisible();
    }

    @Override
    protected void setListener() {
        aQuery.id(R.id.btn_title_left).clicked(this,"onBackPressed");
        aQuery.id(R.id.login_btn).clicked(this,"login");
    }

    public void login(){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
