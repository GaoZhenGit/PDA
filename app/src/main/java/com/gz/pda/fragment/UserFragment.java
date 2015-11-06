package com.gz.pda.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gz.pda.R;
import com.gz.pda.activity.EditActivity;
import com.gz.pda.app.Constant;
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
    private User user;
    @Override
    void setConvertView(LayoutInflater inflater, ViewGroup container) {
        convertView = inflater.inflate(R.layout.fragment_user, container, false);
        gson =new GsonBuilder().disableHtmlEscaping().create();
    }

    @Override
    void fetchData() {
        user =new User();
        user.setUsername("xiaoming");
        user.setPhone("12345678901");
        user.setDetail("宁静致远");
        LogUtil.i(gson.toJson(user));
    }

    @Override
    void initView() {

    }

    @Override
    void setListener() {
        View nameModify = findView(R.id.btn_mdf_name);
        nameModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle =new Bundle();
                bundle.putInt(Constant.DataKey.EDIT_TYPE, Constant.Code.NAME_EDIT);
                bundle.putString(Constant.DataKey.EDTI_DATA,user.getUsername());
                Intent intent =new Intent(getActivity(), EditActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, Constant.Code.NAME_EDIT);
            }
        });

        View detailModify = findView(R.id.btn_mdf_detail);
        detailModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle =new Bundle();
                bundle.putInt(Constant.DataKey.EDIT_TYPE, Constant.Code.DETAIL_EDIT);
                bundle.putString(Constant.DataKey.EDTI_DATA,user.getDetail());
                Intent intent =new Intent(getActivity(), EditActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, Constant.Code.DETAIL_EDIT);
            }
        });

        View passwordModify = findView(R.id.btn_mdf_password);
        passwordModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle =new Bundle();
                bundle.putInt(Constant.DataKey.EDIT_TYPE, Constant.Code.PASSWORD_EDIT);
                Intent intent =new Intent(getActivity(), EditActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, Constant.Code.PASSWORD_EDIT);
            }
        });
    }
}
