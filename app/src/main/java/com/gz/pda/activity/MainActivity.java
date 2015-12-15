package com.gz.pda.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.android.volley.toolbox.Volley;
import com.androidquery.AQuery;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gz.pda.Network.Net;
import com.gz.pda.R;
import com.gz.pda.adapter.TabPagerAdapter;
import com.gz.pda.alarm.AlarmHelper;
import com.gz.pda.app.Constant;
import com.gz.pda.datamodel.TimeTable;
import com.gz.pda.datamodel.User;
import com.gz.pda.db.DBhelper;
import com.gz.pda.fragment.BaseFragment;
import com.gz.pda.fragment.CalendarFragment;
import com.gz.pda.fragment.TimeTableFragment;
import com.gz.pda.fragment.UserFragment;
import com.gz.pda.listener.OnTabSelectedListener;
import com.gz.pda.sys.SysHelper;
import com.gz.pda.utils.LogUtil;
import com.gz.pda.view.PagerTabWidget;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends BaseActivity {
    private AQuery aQuery;

    protected PagerTabWidget mTabWidget;
    protected TabPagerAdapter mPagerAdapter;
    protected ViewPager mViewPager;
    private List<Fragment> fragments;
    private SysHelper sysHelper;

    @Override
    protected void fetchData() {
        fragments = new ArrayList<>();

        fragments.add(new TimeTableFragment());
        fragments.add(new CalendarFragment());
        fragments.add(new UserFragment());
        sysHelper = new SysHelper(this);
    }

    @Override
    protected void initLayoutDataView() {
        setContentView(R.layout.activity_main);
        aQuery = new AQuery(this);
    }

    @Override
    protected void initView() {
        //**************设置下方tab的view和事件***********************
        mTabWidget = findView(R.id.tabwidget);
        mViewPager = findView(R.id.viewpager);
        //设置主界面下方的图标
        View tab1 = LayoutInflater.from(this).inflate(R.layout.tab1, null);
        View tab2 = LayoutInflater.from(this).inflate(R.layout.tab2, null);
        View tab3 = LayoutInflater.from(this).inflate(R.layout.tab3, null);
        mTabWidget.addTab(tab1);
        mTabWidget.addTab(tab2);
        mTabWidget.addTab(tab3);
        mTabWidget.setDividerInvisible();

        mPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mPagerAdapter);
        mTabWidget.setmViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(2);

        mTabWidget.setmOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onSelected(List<View> tabViews, int position) {
                switch (position) {
                    case 0:
                        aQuery.id(R.id.img_tab_calendar).image(R.mipmap.ic_calendar_grey);
                        aQuery.id(R.id.img_tab_user).image(R.mipmap.ic_user_grey);
                        aQuery.id(R.id.img_tab_timetable).image(R.mipmap.ic_timetable);
                        break;
                    case 1:
                        aQuery.id(R.id.img_tab_calendar).image(R.mipmap.ic_calendar);
                        aQuery.id(R.id.img_tab_user).image(R.mipmap.ic_user_grey);
                        aQuery.id(R.id.img_tab_timetable).image(R.mipmap.ic_timetable_grey);
                        break;
                    case 2:
                        aQuery.id(R.id.img_tab_calendar).image(R.mipmap.ic_calendar_grey);
                        aQuery.id(R.id.img_tab_user).image(R.mipmap.ic_user);
                        aQuery.id(R.id.img_tab_timetable).image(R.mipmap.ic_timetable_grey);
                        break;
                }
            }
        });
        //********************************************************
        aQuery.id(R.id.tv_title_middle).text("个人助理");
        aQuery.id(R.id.img_title_right2).image(R.mipmap.ic_add);
        aQuery.id(R.id.btn_title_right2).visible();
    }

    @Override
    protected void setListener() {
        aQuery.id(R.id.btn_title_left).clicked(this, "onBackPressed");
        aQuery.id(R.id.btn_title_right2).clicked(this, "createTimetable");
        aQuery.id(R.id.btn_title_right).clicked(this, "search");
    }

    @Override
    public void onBackPressed() {
        exit();
    }

    public void exit() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确定退出？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.create().show();

    }

    public void createTimetable() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setNegativeButton("自己", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivityForResult(new Intent(MainActivity.this, TimeTableActivity.class), Constant.Code.CREATE_TIMETABLE);
                    }
                })
                .setPositiveButton("好友", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivityForResult(new Intent(MainActivity.this, FriendTimetableActivity.class), Constant.Code.CREATE_TIMETABLE);
                    }
                })
                .setTitle("选择添加日期的对象").create();
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case Constant.Code.CREATE_TIMETABLE:
                //创建日程回调
                TimeTable createTimetable = (TimeTable) data.getExtras()
                        .getSerializable(Constant.DataKey.TIMETABLE);
                createTimetable.setId(((int)System.currentTimeMillis()/1000));
                User user = DBhelper.getInstance().getFirstUser();
                createTimetable.setUser(user);//设置本用户使用
                DBhelper.getInstance().add(createTimetable);//添加到本地数据库
                AlarmHelper.getInstance().add(createTimetable);//添加到闹钟队列，自动判断是否响铃
                remoteAdd(createTimetable);
                fragmentInitView();
                break;
            case Constant.Code.SEARCH:
                //搜索页面返回后刷新界面
                fragmentInitView();
                break;
            default:
                break;
        }
    }

    private void remoteAdd(final TimeTable createTimetable) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String timeTableString = gson.toJson(createTimetable);
        LogUtil.i(timeTableString);
        Map<String, String> param = new HashMap<>();
        param.put("data", timeTableString);
        Net.post(Constant.URL.Add, param, new Net.NetworkListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optInt("state") == 0) {
                        toast("添加成功");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    remoteAdd(createTimetable);
                }
            }

            @Override
            public void onFail(String error) {
                remoteAdd(createTimetable);
            }
        });
    }

    public void fragmentInitView() {
        if (fragments.get(0) instanceof BaseFragment
                && fragments.get(1) instanceof BaseFragment) {
            ((BaseFragment) fragments.get(0)).initView();
            ((BaseFragment) fragments.get(1)).initView();
        }
    }

    public void search() {
        startActivityForResult(new Intent(this, SearchActivity.class), Constant.Code.SEARCH);
    }

    @Override
    protected void onDestroy() {
        sysHelper.stop();
        super.onDestroy();
    }
}
