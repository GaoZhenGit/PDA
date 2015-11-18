package com.gz.pda.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gz.pda.R;
import com.gz.pda.activity.EditActivity;
import com.gz.pda.activity.LoginActivity;
import com.gz.pda.alarm.AlarmHelper;
import com.gz.pda.app.Constant;
import com.gz.pda.datamodel.TimeTable;
import com.gz.pda.datamodel.User;
import com.gz.pda.db.DBhelper;
import com.gz.pda.utils.LogUtil;
import com.gz.pda.utils.SpUtils;
import com.j256.ormlite.table.TableUtils;

import java.io.File;

/**
 * user page
 * logoff
 * modify info
 * Created by host on 2015/11/5.
 */
public class UserFragment extends BaseFragment {
    private User user;
    private Gson gson;
    private AQuery aQuery;

    @Override
    void setConvertView(LayoutInflater inflater, ViewGroup container) {
        convertView = inflater.inflate(R.layout.fragment_user, container, false);
        gson = new GsonBuilder().disableHtmlEscaping().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Override
    void fetchData() {
        aQuery = new AQuery(convertView);
    }

    @Override
    public void initView() {
        user = DBhelper.getInstance().getFirstUser();
        aQuery.id(R.id.mdf_name).text(user.getUsername());
        aQuery.id(R.id.mdf_phone).text(user.getPhone());
        aQuery.id(R.id.mdf_detail).text(user.getDetail());
        LogUtil.i(gson.toJson(user));
    }

    @Override
    void setListener() {
        View nameModify = findView(R.id.btn_mdf_name);
        nameModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.DataKey.EDIT_TYPE, Constant.Code.NAME_EDIT);
                bundle.putString(Constant.DataKey.EDTI_DATA, user.getUsername());
                Intent intent = new Intent(getActivity(), EditActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, Constant.Code.NAME_EDIT);
            }
        });

        View detailModify = findView(R.id.btn_mdf_detail);
        detailModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.DataKey.EDIT_TYPE, Constant.Code.DETAIL_EDIT);
                bundle.putString(Constant.DataKey.EDTI_DATA, user.getDetail());
                Intent intent = new Intent(getActivity(), EditActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, Constant.Code.DETAIL_EDIT);
            }
        });

        View passwordModify = findView(R.id.btn_mdf_password);
        passwordModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.DataKey.EDIT_TYPE, Constant.Code.PASSWORD_EDIT);
                Intent intent = new Intent(getActivity(), EditActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, Constant.Code.PASSWORD_EDIT);
            }
        });

        View logoff = findView(R.id.btn_mdf_logoff);
        logoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清除数据库
                DBhelper.getInstance().drop();
                SpUtils spUtils = new SpUtils(getContext());
                spUtils.clear();
                getActivity().finish();
                AlarmHelper.getInstance().cancelAll();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        User user = DBhelper.getInstance().getFirstUser();
        switch (requestCode) {
            case Constant.Code.NAME_EDIT:
                user.setUsername(data.getStringExtra(Constant.DataKey.EDTI_DATA));
                DBhelper.getInstance().update(user);
                initView();
                break;
            case Constant.Code.DETAIL_EDIT:
                user.setDetail(data.getStringExtra(Constant.DataKey.EDTI_DATA));
                DBhelper.getInstance().update(user);
                initView();
                break;
            case Constant.Code.PASSWORD_EDIT:
                break;
        }
    }
}
