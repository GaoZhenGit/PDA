package com.gz.pda.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by host on 2015/11/5.
 */
public abstract class BaseFragment extends Fragment {
    protected View convertView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setConvertView(inflater, container);
        fetchData();
        initView();
        return convertView;
    }

    //设置总界面，用contextView =inflater.inflate(R.layout.xxx,container,false);
    abstract void setConvertView(LayoutInflater inflater, ViewGroup container);
    //获取数据
    abstract void fetchData();
    //页面细节初始化
    abstract void initView();

    //不用强制类型转换的findviewbyid
    protected <E> E findView(int id) {
        return (E) convertView.findViewById(id);
    }

    //直接输出toast
    protected void toast(String string){
        Toast.makeText(this.getContext(), string, Toast.LENGTH_SHORT).show();
    }
}
