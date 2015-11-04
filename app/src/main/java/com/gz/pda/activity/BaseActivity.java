package com.gz.pda.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

/**
 * Created by host on 2015/11/5.
 */
public abstract class BaseActivity extends Activity {
    protected Intent mIntent;
    protected Bundle mBundle;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIntent = getIntent();
        mBundle = mIntent.getExtras();
        fetchData();
        initLayoutDataView();
        initView();
        setListener();
    }
    //用于初始化获取数据
    protected abstract void fetchData();
    //初始化layout页面，以及设置页面后才能动的数据
    protected abstract void initLayoutDataView();
    //初始化页面细节
    protected abstract void initView();
    //设置各种监听器
    protected abstract void setListener();

    //直接输出toast
    protected void toast(String string){
        Toast.makeText(this,string,Toast.LENGTH_SHORT).show();
    }

    //不用强制类型转换的findviewbyid
    protected <E> E findView(int id){
        return (E)findViewById(id);
    }
}
