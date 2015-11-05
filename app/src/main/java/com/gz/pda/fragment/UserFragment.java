package com.gz.pda.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gz.pda.R;
import com.gz.pda.datamodel.User;
import com.gz.pda.utils.LogUtil;

/**
 * user page
 * logoff
 * modify info
 * Created by host on 2015/11/5.
 */
public class UserFragment extends BaseFragment {
    private Gson gson;
    @Override
    void setConvertView(LayoutInflater inflater, ViewGroup container) {
        convertView = inflater.inflate(R.layout.fragment_user, container, false);
        gson =new GsonBuilder().disableHtmlEscaping().create();
    }

    @Override
    void fetchData() {
        User user =new User();
        user.setUsername("xiaoming");
        LogUtil.i(gson.toJson(user));
    }

    @Override
    void initView() {

    }
}
