package com.miaotech.health.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.miaotech.health.R;
import com.miaotech.health.utils.CheckApplication;
import com.miaotech.health.widget.MyToast;
import com.umeng.analytics.MobclickAgent;

public class OtherResetPassActivity extends Activity implements View.OnClickListener {

    private TextView tittle_next, tittle_back, tittle_tv;// 返回，注册按钮
    private EditText oldpass_edit, newpass_edit, renewpass_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otherresetpassactivity);
        initView();
    }

    /**
     * 初始化界面组件
     */
    private void initView() {
        tittle_tv = (TextView) findViewById(R.id.tittle_tv);
        tittle_next = (TextView) findViewById(R.id.tittle_next);//重置
        tittle_back = (TextView) findViewById(R.id.tittle_back);
        oldpass_edit = (EditText) findViewById(R.id.oldpass_edit);
        newpass_edit = (EditText) findViewById(R.id.newpass_edit);
        renewpass_edit = (EditText) findViewById(R.id.renewpass_edit);
        tittle_next.setOnClickListener(this);
        tittle_back.setOnClickListener(this);
        tittle_tv.setText("重置密码");
        tittle_next.setText("重置");
    }

    /**
     * 屏幕按钮点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tittle_back:
                finish();
                break;
            case R.id.tittle_next:
                if (check()) {
                    Toast.makeText(OtherResetPassActivity.this,"格式正确",Toast.LENGTH_SHORT).show();
                    // TODO 联网重置
                }
                break;
            default:
                break;

        }

    }


    private boolean check() {
        CheckApplication check = new CheckApplication();// 格式校验类
        String oldpass=oldpass_edit.getText().toString().trim();
        String newpass=newpass_edit.getText().toString().trim();
        String renewpass=renewpass_edit.getText().toString().trim();

        if (TextUtils.isEmpty(oldpass)) {
            // 提示不为空
            MyToast.showToastShort(getApplicationContext(), "旧密码不能为空");
            return false;
        } else if (!check.isPassword(oldpass)) {
            MyToast.showToastShort(getApplicationContext(), "旧密码格式不正确");
            return false;
        }
        if (TextUtils.isEmpty(newpass)) {
            // 提示不为空
            MyToast.showToastShort(getApplicationContext(),"新密码不能为空");
            return false;
        } else if (!check.isPassword(newpass)) {
            MyToast.showToastShort(getApplicationContext(), "新密码格式不正确");
            return false;
        }
        if (TextUtils.isEmpty(renewpass)) {
            MyToast.showToastShort(getApplicationContext(), "重复密码不能为空");
            return false;
        } else if (!renewpass.equals(newpass)) {
            MyToast.showToastShort(getApplicationContext(),"重复密码与新密码不相同");
            return false;
        }


        return true;
    }
    //友盟统计------------------
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
