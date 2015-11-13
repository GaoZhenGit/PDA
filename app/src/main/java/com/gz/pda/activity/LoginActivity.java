package com.gz.pda.activity;

import android.content.Intent;
import android.widget.EditText;

import com.androidquery.AQuery;
import com.gz.pda.R;
import com.gz.pda.datamodel.User;
import com.gz.pda.db.DBhelper;

/**
 * for login
 * Created by host on 2015/11/7.
 */
public class LoginActivity extends BaseActivity {
    AQuery aQuery;
    private EditText phone;
    private EditText password;
    private EditText username;

    @Override
    protected void fetchData() {
        //检查是否登录
        User user = DBhelper.getInstance().getFirstUser();
        if(user!=null){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void initLayoutDataView() {
        setContentView(R.layout.activity_login);
        aQuery = new AQuery(this);
        phone = findView(R.id.login_phone_et);
        username = findView(R.id.login_name_et);
        password = findView(R.id.login_phone_psw_et);
    }

    @Override
    protected void initView() {
        aQuery.id(R.id.tv_title_middle).text("登录");
        aQuery.id(R.id.btn_title_right).invisible();
    }

    @Override
    protected void setListener() {
        aQuery.id(R.id.btn_title_left).clicked(this, "onBackPressed");
        aQuery.id(R.id.login_btn).clicked(this, "login");
    }

    public void login() {
        String phoneString = phone.getText().toString();
        String usernameString = username.getText().toString();
        String passwordString = password.getText().toString();

        if(phoneString.length()==0||usernameString.length()==0||passwordString.length()==0){
            toast("请填写完整信息");
            return;
        }
        //TODO:登录后的回调
        User user = new User();
        user.setPhone(phoneString);
        user.setUsername(usernameString);
        user.setDetail("未填写");
        DBhelper.getInstance().add(user);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
