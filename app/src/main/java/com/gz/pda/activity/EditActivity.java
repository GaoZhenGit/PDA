package com.gz.pda.activity;

import android.text.InputType;
import android.widget.EditText;

import com.androidquery.AQuery;
import com.gz.pda.R;
import com.gz.pda.app.Constant;

/**
 * the acitvity that edit username detail password
 * Created by host on 2015/11/6.
 */
public class EditActivity extends BaseActivity {
    private int type;
    private AQuery aQuery;
    private String editData;
    private EditText modifyData;

    @Override
    protected void fetchData() {
        if (mBundle == null) {
            finish();
        }
        int getType = mBundle.getInt(Constant.DataKey.EDIT_TYPE);
        if (getType == Constant.Code.NAME_EDIT) {
            type = Constant.Code.NAME_EDIT;
        }
        if (getType == Constant.Code.DETAIL_EDIT) {
            type = Constant.Code.DETAIL_EDIT;
        }
        if (getType == Constant.Code.PASSWORD_EDIT) {
            type = Constant.Code.PASSWORD_EDIT;
        }
        editData = mBundle.getString(Constant.DataKey.EDTI_DATA);
    }

    @Override
    protected void initLayoutDataView() {
        setContentView(R.layout.activity_edit);
        aQuery = new AQuery(this);
    }

    @Override
    protected void initView() {
        modifyData = findView(R.id.et_modify);
        switch (type){
            case Constant.Code.NAME_EDIT:
                aQuery.id(R.id.tv_title_middle).text("名字");
                aQuery.id(R.id.et_modify).text(editData);
                break;
            case Constant.Code.DETAIL_EDIT:
                aQuery.id(R.id.tv_title_middle).text("个性签名");
                aQuery.id(R.id.et_modify).text(editData);
                break;
            case Constant.Code.PASSWORD_EDIT:
                aQuery.id(R.id.tv_title_middle).text("密码");
                modifyData.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                modifyData.setHint("请输入新密码");
                aQuery.id(R.id.et_old_password).visible();
                aQuery.id(R.id.et_password_confirm).visible();
                break;
        }
        aQuery.id(R.id.img_title_right).image(R.mipmap.ic_confirm);
    }

    @Override
    protected void setListener() {
        aQuery.id(R.id.btn_title_right).clicked(this,"confirm");
        aQuery.id(R.id.btn_title_left).clicked(this, "onBackPressed");
    }

    public void confirm(){
        //确定修改
        finish();
    }
}
