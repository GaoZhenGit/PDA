package com.gz.pda.activity;



import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.androidquery.AQuery;
import com.gz.pda.R;
import com.gz.pda.adapter.TabPagerAdapter;
import com.gz.pda.fragment.CalendarFragment;
import com.gz.pda.listener.OnTabSelectedListener;
import com.gz.pda.view.PagerTabWidget;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {
    private AQuery aQuery;

    protected PagerTabWidget mTabWidget;
    protected TabPagerAdapter mPagerAdapter;
    protected ViewPager mViewPager;
    private List<Fragment> fragments;

    @Override
    protected void fetchData() {
        fragments =new ArrayList<>();

        fragments.add(new Fragment());
        fragments.add(new CalendarFragment());
        fragments.add(new Fragment());
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
        final View tab1 = LayoutInflater.from(this).inflate(R.layout.tab1,null);
        final View tab2 = LayoutInflater.from(this).inflate(R.layout.tab2,null);
        final View tab3 = LayoutInflater.from(this).inflate(R.layout.tab3,null);
        mTabWidget.addTab(tab1);
        mTabWidget.addTab(tab2);
        mTabWidget.addTab(tab3);
        mTabWidget.setDividerInvisible();

        mPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(),fragments);
        mViewPager.setAdapter(mPagerAdapter);
        mTabWidget.setmViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(2);

        mTabWidget.setmOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onSelected(List<View> tabViews, int position) {
                switch (position){
                    case 0:
                        aQuery.id(R.id.img_tab_calendar).image(R.mipmap.ic_calendar_grey);
                        break;
                    case 1:
                        aQuery.id(R.id.img_tab_calendar).image(R.mipmap.ic_calendar);
                        break;
                    case 2:
                        aQuery.id(R.id.img_tab_calendar).image(R.mipmap.ic_calendar_grey);
                        break;
                }
            }
        });
        //********************************************************
        aQuery.id(R.id.tv_title_middle).text("个人助理");

    }

    @Override
    protected void setListener() {
    }

}
