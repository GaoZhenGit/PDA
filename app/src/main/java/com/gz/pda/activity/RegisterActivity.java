package com.gz.pda.activity;

import android.widget.EditText;

import com.androidquery.AQuery;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gz.pda.Network.Net;
import com.gz.pda.R;
import com.gz.pda.app.Constant;
import com.gz.pda.datamodel.User;
import com.gz.pda.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * for register
 * Created by host on 2015/11/7.
 */
public class RegisterActivity extends BaseActivity {
    AQuery aQuery;
    private EditText phone;
    private EditText username;
    private EditText password;

    @Override
    protected void fetchData() {

    }

    @Override
    protected void initLayoutDataView() {
        setContentView(R.layout.activity_register);
        aQuery = new AQuery(this);
    }

    @Override
    protected void initView() {
        aQuery.id(R.id.tv_title_middle).text("注册");
        aQuery.id(R.id.btn_title_right).invisible();
        phone = findView(R.id.register_phone_et);
        username = findView(R.id.register_name_et);
        password = findView(R.id.register_psw_et);
    }

    @Override
    protected void setListener() {
        aQuery.id(R.id.btn_title_left).clicked(this, "onBackPressed");
        aQuery.id(R.id.register_btn).clicked(this, "register");
    }

    public void register() {
        String phoneString = phone.getText().toString();
        String usernameString = username.getText().toString();
        String passwordString = password.getText().toString();

        if (phoneString.length() == 0
                || usernameString.length() == 0
                || passwordString.length() == 0) {
            toast("请填写完整信息");
            return;
        }

        User user = new User();
        user.setPhone(phoneString);
        user.setUsername(usernameString);
        user.setPassword(passwordString);

        Gson gson = new Gson();
        String data = gson.toJson(user);

        Map<String, String> param = new HashMap<>();
        param.put("data", data);
        Net.post(Constant.URL.Register, param, new Net.NetworkListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optInt("state") == 1) {
                        toast("注册成功");
                        finish();
                    } else {
                        toast("注册失败" + jsonObject.optString("reason"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    toast("注册失败");
                }
            }

            @Override
            public void onFail(String error) {
                toast("当前网络环境较差，请稍后再试");
            }
        });
    }
}
