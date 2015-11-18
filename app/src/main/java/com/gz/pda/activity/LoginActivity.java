package com.gz.pda.activity;

import android.content.Intent;
import android.widget.EditText;

import com.androidquery.AQuery;
import com.google.gson.Gson;
import com.gz.pda.Network.Net;
import com.gz.pda.R;
import com.gz.pda.alarm.AlarmHelper;
import com.gz.pda.datamodel.TimeTable;
import com.gz.pda.datamodel.User;
import com.gz.pda.db.DBhelper;
import com.gz.pda.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * for login
 * Created by host on 2015/11/7.
 */
public class LoginActivity extends BaseActivity {
    AQuery aQuery;
    Gson gson;
    private EditText phone;
    private EditText password;
//    private EditText username;

    @Override
    protected void fetchData() {
        //启动网络队列
        try {
            Net.getmQueue().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //检查是否登录
        User user = DBhelper.getInstance().getFirstUser();
        if (user != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void initLayoutDataView() {
        setContentView(R.layout.activity_login);
        aQuery = new AQuery(this);
        phone = findView(R.id.login_phone_et);
//        username = findView(R.id.login_name_et);
        password = findView(R.id.login_phone_psw_et);
    }

    @Override
    protected void initView() {
        aQuery.id(R.id.tv_title_middle).text("登录");
        aQuery.id(R.id.btn_title_right).visible();
        aQuery.id(R.id.img_title_right).image(R.mipmap.ic_user_white);
    }

    @Override
    protected void setListener() {
        aQuery.id(R.id.btn_title_left).clicked(this, "onBackPressed");
        aQuery.id(R.id.login_btn).clicked(this, "login");
        aQuery.id(R.id.btn_title_right).clicked(this, "register");
    }

    public void login() {
        String phoneString = phone.getText().toString();
        String passwordString = password.getText().toString();

        if (phoneString.length() == 0 || passwordString.length() == 0) {
            toast("请填写完整信息");
            return;
        }
        //填写登录信息
        User user = new User();
        user.setPhone(phoneString);
        user.setPassword(passwordString);
        //转换gson格式
        gson = new Gson();
        String data = gson.toJson(user);
        LogUtil.i(data);
        Map<String, String> param = new HashMap<>();
        param.put("data",data);
        //发起网络请求
        Net.login(param, new Net.NetworkListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //判断json中登录状态
                    if (jsonObject.optInt("state") == 1) {
                        //解读user
                        User user = gson.fromJson(jsonObject.optString("user"), User.class);
                        //将用户写入数据库
                        DBhelper.getInstance().add(user);
                        //将timetable全部关联用户写入数据库，闹铃队列
                        if (user.getTimeTables() != null) {
                            for (TimeTable timeTable : user.getTimeTables()) {
                                timeTable.setUser(user);
                                DBhelper.getInstance().add(timeTable);
                                AlarmHelper.getInstance().add(timeTable);//加入闹钟队列
                            }
                        }
                        toast("登录成功");
                        startActivity(new Intent(getBaseContext(), MainActivity.class));
                        finish();
                    } else {
                        toast("登录失败" + jsonObject.optString("reason"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    toast("登录失败");
                }
            }

            @Override
            public void onFail(String error) {
                toast("当前网络环境较差，请稍后再试");
            }
        });
    }

    public void login2() {
        User user = new User();
        user.setUsername("test");
        user.setPhone("13622847209");
        user.setDetail("test detail");
        DBhelper.getInstance().add(user);
        toast("登录成功");
        startActivity(new Intent(getBaseContext(), MainActivity.class));
        finish();
    }

    public void register() {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
