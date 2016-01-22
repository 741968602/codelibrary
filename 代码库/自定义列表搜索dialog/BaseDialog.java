package com.eagle.parking.utils;

import com.eagle.parking.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

public class BaseDialog extends Dialog{
	
	private CustomProgress progress;

	public BaseDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
	
	
	
	public void showShortToast(String str){
		Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
	}

	public void showShortToast(int str){
		Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * 发送未知错误
	 */
	public void showUnKnowToast(){
		showShortToast("发生未知错误,请稍后再试");
	}
	
	/**
	 * 网络异常提示语
	 */
	public void showNetWorkToast(){
		showShortToast(getContext().getResources().getString(R.string.network_anomal));
	}
	
	/**
	 * 显示进程对话框
	 */
	public void showProgress(){
		if(progress== null){
			progress = CustomProgress.show(getContext(),null, true, null);
		}else{
			progress.show();
		}
		
	}
	
	
	/**
	 * 取消进程对话框的显示
	 */
	public void dismissProgress(){
		if(progress!=null && progress.isShowing()){
			progress.dismiss();
		}
	}

}
