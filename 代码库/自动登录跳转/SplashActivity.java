package com.miaotech.health.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;

import com.miaotech.health.R;
import com.miaotech.health.utils.ExitApplication;
import com.umeng.analytics.MobclickAgent;

public class SplashActivity extends Activity {

    // 延迟2秒
    private static final long SPLASH_DELAY_MILLIS = 2000;
    private static final int GO_HOME = 1000;
    private static final int GO_GUIDE = 1001;
    private static final int GO_LOGIN = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        ExitApplication.getInstance().addActivity(this);// 添加到应用界面队列用于统一退出
        isAutoLogin();
    }

    /**
     * Handler:跳转到不同界面
     */
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME:
                    goHome();//跳转到到主界面
                    break;
                case GO_GUIDE://跳转到完善资料界面
                    goGuide();
                    break;
                case GO_LOGIN://跳转到登录界面
                    goLogin();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 判断是否为第一次登录并作相应的逻辑处理
     * token为空跳转login
     * 完善资料标志为空跳转firstlogin
     */
    private void isAutoLogin() {

        SharedPreferences sp = getSharedPreferences("UserLogin", MODE_PRIVATE);
        String token = sp.getString("token", "");
        boolean info_complete = sp.getBoolean("info_complete", false);
        if ("".equals(token)) {
            mHandler.sendEmptyMessageDelayed(GO_LOGIN, SPLASH_DELAY_MILLIS);
        } else if (info_complete) {
            mHandler.sendEmptyMessageDelayed(GO_GUIDE, SPLASH_DELAY_MILLIS);
        } else {
            mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
        }

    }

    /**
     * 跳转到登录界面
     */

    private void goLogin() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        SplashActivity.this.startActivity(intent);
        SplashActivity.this.finish();
    }

    /**
     * 跳转到完善资料界面
     */

    private void goGuide() {
        Intent intent = new Intent(SplashActivity.this, FirstLogin1Activity.class);
        SplashActivity.this.startActivity(intent);
        SplashActivity.this.finish();
    }

    /**
     * 跳转到完善主界面
     */

    private void goHome() {
        Intent intent = new Intent(SplashActivity.this, FirstLogin1Activity.class);
        SplashActivity.this.startActivity(intent);
        SplashActivity.this.finish();
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
