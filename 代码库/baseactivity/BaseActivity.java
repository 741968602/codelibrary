package com.eagle.parking;

import com.eagle.parking.utils.CustomProgress;
import com.eagle.parking.utils.ExitApplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BaseActivity extends Activity implements View.OnClickListener{
	
	private CustomProgress progress;
	private Button backButton;
	private Button addButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	
	/**
	 * 初始化标题栏
	 * @param titleStr 标题
	 * @param isBackActivity 当true时，表示点击返回按钮，直接返回上一层；false时，表示单独设置返回键响应事件
	 * @param isAddShow 当为true时，初始化添加按键，并显示
	 */
	public void initTitleBar(String titleStr,boolean isBackActivity,boolean isAddShow){
		ExitApplication.getInstance().addActivity(BaseActivity.this);
		
		setTitle(titleStr);//设置标题栏显示
		
		//设置返回键响应
		setBackFinishActivity(isBackActivity);
		
		//设置添加按键是否显示
		if(isAddShow){
			isShowAddButton(isAddShow);
		}
		
	}
	
	//设置标题栏
	private void setTitle(String titleStr){
		TextView titleText = (TextView) findViewById(R.id.titleTv);
		titleText.setText(titleStr);
	}
	
	
	private void isShowAddButton(boolean isShow){
		
		if(addButton == null){
			addButton = (Button) findViewById(R.id.add);
		}
		
		if(isShow){
			addButton.setVisibility(View.VISIBLE);
		}
		
	}
	
	//初始化返回按钮
	private void setBackFinishActivity(boolean isBackActivity){
		
		if(backButton == null){
			backButton = (Button) findViewById(R.id.back);
		}
		
		if(isBackActivity){
			backButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
		
		
	}
	
	/**
	 * 获取返回按钮
	 * @return
	 */
	public Button getBackButton(){
		
		if(backButton == null){
			backButton = (Button) findViewById(R.id.back);
		}
		
		return backButton;
	}
	
	
	public void showShortToast(String str){
		Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
	}

	public void showShortToast(int str){
		Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
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
		showShortToast(getResources().getString(R.string.network_anomal));
	}
	
	/**
	 * 显示进程对话框
	 */
	public void showProgress(){
		if(progress== null){
			progress = CustomProgress.show(this,null, true, null);
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
	
	/**
	 * 获取用户userId
	 * @return
	 */
	public int getUserId(){
		return getSharedPreferences("User",Context.MODE_APPEND).getInt("userId", 0);
	}
	
	/**
	 * 获取用户nickName
	 * @return
	 */
	public String getNickName(){
		return getSharedPreferences("User",Context.MODE_APPEND).getString("nickName", "");
	}
	/**
	 * 获取用户手机号
	 * @return
	 */
	public String getPhone(){
		return getSharedPreferences("User",Context.MODE_APPEND).getString("phoneNo", "");
	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}


	public Button getAddButton() {
		return addButton;
	}


	public void setAddButton(Button addButton) {
		this.addButton = addButton;
	}


	public void setBackButton(Button backButton) {
		this.backButton = backButton;
	}


	
}
