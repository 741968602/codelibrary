package com.stcyclub.zhimeng.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.stcyclub.zhimeng.R;
import com.stcyclub.zhimeng.chat.FaceConversionUtil;


public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		//开始加载表情文件
		new Thread(new Runnable() {
			@Override
			public void run() {
				FaceConversionUtil.getInstace().getFileText(getApplication());
			}
		}).start();
	}
	public void btnClick(View v){
		Intent intent=new Intent(MainActivity.this,ChatActivity.class);
		MainActivity.this.startActivity(intent);
	}
	
}

